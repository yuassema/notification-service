package org.example.notificationservice.dto;

import java.time.LocalDateTime;

public class ReminderRequest {
    private String message;
    private LocalDateTime remindAt;
    private String email;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getRemindAt() { return remindAt; }
    public void setRemindAt(LocalDateTime remindAt) { this.remindAt = remindAt; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
