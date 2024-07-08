package com.example.onlineshop.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@Service
public class CurrencyConverterService {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyConverterService.class);

    @Value("${exchangerate.api.key}")
    private String apikey;

    // A constant that holds the URL of the exchange rate API
    private static final String EXCHANGE_RATE_API_URL = "https://v6.exchangerate-api.com/v6/{apikey}/pair/{from}/{to}/{amount}";

    @Autowired
    private RestTemplate restTemplate; // An instance of RestTemplate class, a Spring class that simplifies making HTTP requests

    public CurrencyConverterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // A method that converts an amount of currency from one currency to another
    public BigDecimal convertCurrency(String from, String to, BigDecimal amount){
        if(apikey == null || apikey.isEmpty()){
            throw new IllegalArgumentException("API key is missing");
        }

        // Prepare the URL for the API request by replacing placeholders with actual values
        String url = UriComponentsBuilder.fromUriString(EXCHANGE_RATE_API_URL)
                .buildAndExpand(apikey, from, to, amount.toString())
                .toUriString();

        logger.debug("Calling exchange rate API with URL: {}", url);

        ResponseEntity<ExchangeRateResponse> response = restTemplate.getForEntity(url, ExchangeRateResponse.class);

        if(response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
            logger.debug("Conversion result: " + response.getBody().getConversionResult());
            return response.getBody().getConversionResult();
        }else {
            logger.error("Failed to retrieve exchange rate from API");
            throw new RuntimeException("Failed to retrieve exchange rate from API");
        }

    }

    // A static inner class that represents the response from the exchange rate API
    private static class ExchangeRateResponse{
        @JsonProperty("conversion_result")
        private BigDecimal conversionResult; // This field will be automatically populated with the data from the API response because of Spring's automatic conversion of the HTTP response body to a Java object

        // getter and setter
        public BigDecimal getConversionResult(){
            return conversionResult;
        }

        public void setConversionResult(BigDecimal conversionResult){
            this.conversionResult = conversionResult;
        }
    }

}
