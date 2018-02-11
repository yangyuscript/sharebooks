package com.lin.sharebooks.service;

public interface EmailService {
    public void sendHtmlEmail(String toEmail, String subject, String content);
}
