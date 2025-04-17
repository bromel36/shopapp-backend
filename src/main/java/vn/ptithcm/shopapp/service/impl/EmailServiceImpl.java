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
import vn.ptithcm.shopapp.service.IEmailService;

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
    private String verifyApi;


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


    public void sendVerifyMail(String to, String subject, String name, String verifyToken, TokenTypeEnum type) {
        log.info("Verify email started");

        Email mailFrom = new Email(from);
        Email mailTo = new Email(to);


        String verificationLink = verifyApi + "?token=" + verifyToken +"&type="+ type.name();

        // Dinh nghia template
        Map<String, String> maps = new HashMap<>();

        maps.put("verification-link", verificationLink);
        maps.put("logo", logo);
        maps.put("name", name);
        maps.put("subject", subject);

        Personalization personalization = new Personalization();

        personalization.addTo(mailTo);

        maps.forEach(personalization::addDynamicTemplateData);

        Mail mail = new Mail();
        mail.setFrom(mailFrom);
//        mail.setSubject(subject);
        mail.addPersonalization(personalization);
        mail.setTemplateId("d-19e92076e6b24c9289e296a79c4653a7");

        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);

            if(response.getStatusCode() == 202) {
                log.info("Send email successfully");
            }
            else{
                log.info("Failed to send email " + response.getBody());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
