package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.controller.dto.certificate.CertificateDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserDto;
import com.github.evgenylizogubov.publicvoting.error.FileLoadingException;
import com.github.evgenylizogubov.publicvoting.error.MailSendingException;
import com.github.evgenylizogubov.publicvoting.error.NotFoundException;
import com.github.evgenylizogubov.publicvoting.mapper.certificate.CertificateDtoToCertificateMapper;
import com.github.evgenylizogubov.publicvoting.mapper.user.UserDtoToUserMapper;
import com.github.evgenylizogubov.publicvoting.model.Certificate;
import com.github.evgenylizogubov.publicvoting.model.User;
import com.github.evgenylizogubov.publicvoting.repository.CertificateRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final CertificateDtoToCertificateMapper certificateDtoToCertificateMapper;
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final UserDtoToUserMapper userDtoToUserMapper;
    
    public List<CertificateDto> getAllByOwner(UserDto owner) {
        List<Certificate> certificates = certificateRepository.findAllByOwner(userDtoToUserMapper.toEntity(owner));
        return certificateDtoToCertificateMapper.toDtoList(certificates);
    }
    
    public byte[] getCertificate(UserDto owner, int year) {
        User user = userDtoToUserMapper.toEntity(owner);
        
        if (certificateRepository.existsByOwnerAndYear(user, year)) {
            try {
                String pathToCertificate = "certificates/cert" + year + ".png";
                Resource file = new ClassPathResource(pathToCertificate);
                return StreamUtils.copyToByteArray(file.getInputStream());
            } catch (IOException e) {
                throw new FileLoadingException("Error loading certificate with year=" + year);
            }
        }
        
        return null;
    }
    
    public void sendByEmail(User winner, int year) {
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
