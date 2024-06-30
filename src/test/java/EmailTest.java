import com.example.onlineshop.OnlineShopApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;



@SpringBootTest(classes = OnlineShopApplication.class)
public class EmailTest {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void testEmailSending() {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo("chizuru.yamauchi@dci-student.org");
        email.setSubject("Test Email");
        email.setText("This is a test email.");
        javaMailSender.send(email);
        System.out.println("Email sent successfully");
    }
}
