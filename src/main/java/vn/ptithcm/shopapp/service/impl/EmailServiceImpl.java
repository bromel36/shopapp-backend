package vn.ptithcm.shopapp.service.impl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.enums.TokenTypeEnum;
import vn.ptithcm.shopapp.model.entity.Token;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.service.IEmailService;
import vn.ptithcm.shopapp.util.StringUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j(topic = "EMAIL-SENDER")
public class EmailServiceImpl implements IEmailService {

    private final SendGrid sendGrid;
    @Qualifier("from")
    private final String from;

    @Value("${spring.sendgrid.verify.logo}")
    private String logo;

    @Value("${spring.sendgrid.verify-api}")
    private String verifyWebLink;

    @Value("${spring.sendgrid.verify-deep-link}")
    private String verifyDeepLink;

    @Value("${spring.sendgrid.template-verify.id}")
    private String templateVerifyId;

    @Value("${spring.sendgrid.template-forgot-pwd.id}")
    private String templateForgotPwdId;

    private String templateId = "";



    public EmailServiceImpl(SendGrid sendGrid, String mailFromStr) {
        this.sendGrid = sendGrid;
        this.from = mailFromStr;
    }

    @Override
    public String send(String to, String subject, String body) {
        Email mailFrom = new Email(from);

        Email mailTo = new Email(to, "Minh Tiến");

        Content content = new Content("text/plain", body);

        Mail mail  = new Mail(mailFrom, subject, mailTo, content);

        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);

            if(response.getStatusCode() == 202) {
                return "Send email successfully";
            }
            else{
                return "Failed to send email " + response.getBody();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendVerifyMail(User user, Token token, String clientType) {
        log.info("Verify email started for user: {}", user.getEmail());

        String name = StringUtil.isValid(user.getFullName()) ? user.getFullName() : "";

        templateId = token.getType() == TokenTypeEnum.RESET_PASSWORD ? templateForgotPwdId : templateVerifyId;

        String verificationLink = (clientType.equalsIgnoreCase(StringUtil.WEB_HEADER)? verifyWebLink : verifyDeepLink)
                + "?token=" + token.getToken() + "&type=" + token.getType().name();

        sendVerificationEmail(user.getEmail(), StringUtil.EMAIL_SUBJECT, name, verificationLink);
    }


    private void sendVerificationEmail(String to, String subject, String name, String verificationLink) {
        Email mailFrom = new Email(from);
        Email mailTo = new Email(to);

        Map<String, String> templateData = new HashMap<>();
        templateData.put("verification-link", verificationLink);
        templateData.put("logo", logo);
        templateData.put("name", name);
        templateData.put("subject", subject);

        Personalization personalization = new Personalization();
        personalization.addTo(mailTo);
        templateData.forEach(personalization::addDynamicTemplateData);

        Mail mail = new Mail();
        mail.setFrom(mailFrom);
        mail.addPersonalization(personalization);
        mail.setTemplateId(templateId);

        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);

            if(response.getStatusCode() == 202) {
                log.info("Send email successfully to: {}", to);
            } else {
                log.info("Failed to send email: {}", response.getBody());
            }
        } catch (IOException e) {
            log.error("Error sending verification email", e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
