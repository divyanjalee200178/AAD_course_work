package org.example.back_end.service;

import org.example.back_end.entity.Email;

import java.util.List;

public interface EmailService {
    public void sendEmail(String to, String subject, String body);
    public void saveEmailRecord(String recipient, String subject, String body);
    public List<Email> getAllEmails();
    public boolean deleteEmail(int id);
}
