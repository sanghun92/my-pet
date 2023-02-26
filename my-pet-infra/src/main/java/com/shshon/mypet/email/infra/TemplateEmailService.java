package com.shshon.mypet.email.infra;

import com.shshon.mypet.email.application.EmailService;
import com.shshon.mypet.email.dto.MailMessageDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class TemplateEmailService implements EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void send(MailMessageDto mailMessage) {
        mailSender.send(toMailMessage(mailMessage));
    }

    private String getMailContents(MailMessageDto mailMessage) {
        Context context = new Context();
        mailMessage.attribute().forEach(context::setVariable);
        return templateEngine.process(mailMessage.template(), context);
    }

    private MimeMessage toMailMessage(MailMessageDto mailMessage) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.addRecipients(MimeMessage.RecipientType.TO, String.join(",", mailMessage.to()));
            message.setSubject(mailMessage.subject());
            message.setText(getMailContents(mailMessage), "utf-8", "html");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return message;
    }
}
