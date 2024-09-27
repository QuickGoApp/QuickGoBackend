package com.QuickGo.backend.service;

public interface MailService {
    void sendEmail(String recipient, String subject, String body);
}
