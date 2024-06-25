### Bonus Task: Implementing "Forgot Password" Feature with Security Questions

#### Objective:
Enhance the online shop application by adding a "Forgot Password" feature. Users will be able to reset their password by answering security questions and receiving a reset password email.

#### Requirements:
1. **Forgot Password**:
   - Users should be able to request a password reset by providing their email.
   - Users should answer security questions to verify their identity.
   - Upon successful verification, users should receive a reset password email with a link to reset their password.

2. **Security Questions**:
   - Users should set up security questions and answers during the signup process.
   - The application should store these security questions and answers securely.

### Steps to Implement the Bonus Task:

#### Step 1: Update the User Entity
- **Add Security Questions and Answers**:
  - Extend the `User` entity to include fields for security questions and answers.
  - Ensure that these fields are stored securely, similar to how passwords are stored.

#### Step 2: Update the Signup Process
- **Collect Security Questions and Answers**:
  - Update the signup form to include fields for security questions and answers.
  - Ensure that these fields are validated and stored securely in the database.

#### Step 3: Create the Forgot Password Flow
1. **Forgot Password Request**:
   - Create a form where users can enter their email to request a password reset.
   - Upon submission, generate a unique token and store it in the database along with the user's email and an expiration time.

2. **Verify Security Questions**:
   - Send an email to the user with a link to a page where they can answer their security questions.
   - The link should include the unique token generated in the previous step.
   - On the verification page, prompt the user to answer their security questions.
   - Validate the answers against the stored answers in the database.

3. **Send Reset Password Email**:
   - If the security questions are answered correctly, send a reset password email to the user.
   - The email should contain a link to a page where the user can reset their password.
   - The link should include the unique token to ensure that only the intended user can reset the password.

#### Step 4: Implement the Reset Password Functionality
1. **Reset Password Page**:
   - Create a form where users can enter their new password.
   - Validate the new password and ensure it meets security requirements.

2. **Update Password**:
   - Upon submission, validate the unique token to ensure it is still valid and has not expired.
   - Update the user's password in the database and invalidate the token to prevent reuse.

#### Step 5: Security Considerations
- **Token Expiration**:
  - Ensure that the unique token generated for password reset requests has a limited validity period (e.g., 1 hour).
  - Invalidate the token after it has been used to reset the password.

- **Secure Storage**:
  - Store security questions and answers securely, using encryption or hashing to protect sensitive information.
  - Ensure that the reset password link and token are transmitted securely over HTTPS.

- **Rate Limiting**:
  - Implement rate limiting to prevent abuse of the forgot password feature (e.g., multiple requests from the same IP address in a short period).

### Logic Explanation:

1. **User Entity Update**:
   - Extend the `User` entity to include fields for security questions and answers.
   - Ensure these fields are stored securely, similar to how passwords are stored.

2. **Signup Process Update**:
   - Update the signup form to collect security questions and answers.
   - Store the security questions and answers securely in the database.

3. **Forgot Password Request**:
   - Create a form for users to request a password reset by entering their email.
   - Generate a unique token and store it in the database with the user's email and an expiration time.
   - Send an email to the user with a link to a page where they can answer their security questions.

4. **Verify Security Questions**:
   - On the verification page, prompt the user to answer their security questions.
   - Validate the answers against the stored answers in the database.
   - If the answers are correct, send a reset password email to the user with a link to reset their password.

5. **Reset Password Functionality**:
   - Create a form for users to enter their new password.
   - Validate the new password and ensure it meets security requirements.
   - Update the user's password in the database and invalidate the token to prevent reuse.

6. **Security Considerations**:
   - Ensure the unique token has a limited validity period and is invalidated after use.
   - Store security questions and answers securely.
   - Implement rate limiting to prevent abuse of the forgot password feature.

By implementing this bonus task, students will gain experience with additional security features and enhance the overall security and usability of the online shop application.
