package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.error.MailSendingException;
import com.github.evgenylizogubov.publicvoting.error.NotFoundException;
import com.github.evgenylizogubov.publicvoting.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    
    public void sendAwardingMessage(User winner, int year) {
        try {
            Context context = new Context();
            context.setVariable("name", winner.getFirstName());
            
            String process = templateEngine.process("awarding.html", context);
            
            MimeMessage message = javaMailSender.createMimeMessage();
            
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("public.voting.awarding@gmail.com");
            helper.setSubject("Awarding for voting");
            helper.setTo(winner.getEmail());
            helper.setText(process, true);
            
            String pathToAttachment = "certificates/cert" + year + ".png";
            Resource file = new ClassPathResource(pathToAttachment);
            if (!file.exists()) {
                throw new NotFoundException("Certificate for awarding not found");
            }
            
            helper.addInline("certificate.png", file);
            
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new MailSendingException("Error sending email with award");
        }
    }
}
