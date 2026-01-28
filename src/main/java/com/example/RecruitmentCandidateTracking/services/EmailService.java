package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.enums.EmailType;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.thymeleaf.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, EmailType type, String verificationLink) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(to);
            helper.setSubject(type.getSubject());

            Context context = new Context();
            context.setVariable("verificationLink", verificationLink);
            context.setVariable("appName", "Recruitment & Candidate Tracking System");

            String htmlContent = templateEngine.process(type.getTemplate(), context);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            log.info("Gửi email xác thực thành công đến: {}", to);

        } catch (Exception e) {

            log.error("Lỗi khi gửi email xác thực đến {}: ", to, e);

        }
    }

    @Async
    public void sendOfferEmail(
            String to,
            String candidateName,
            String jobTitle,
            String contractType,
            String startWorkDate,
            String basicSalary,
            String probationSalary,
            String approvedByName
    ) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(EmailType.OFFER_NOTIFICATION.getSubject());

            Context context = new Context();
            context.setVariable("candidateName", candidateName);
            context.setVariable("jobTitle", jobTitle);
            context.setVariable("contractType", contractType);
            context.setVariable("startWorkDate", startWorkDate);
            context.setVariable("basicSalary", basicSalary);
            context.setVariable("probationSalary", probationSalary);
            context.setVariable("approvedByName", approvedByName);
            context.setVariable("appName", "Recruitment & Candidate Tracking System");

            String htmlContent = templateEngine.process(
                    EmailType.OFFER_NOTIFICATION.getTemplate(),
                    context
            );

            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            log.info("Gửi email OFFER thành công đến: {}", to);

        } catch (Exception e) {
            log.error("Lỗi khi gửi email OFFER đến {}: ", to, e);
        }
    }

    public void sendPlainText(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }



}

