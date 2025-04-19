package org.example.notificationservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private LocalDateTime remindAt;
    private String email;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getRemindAt() { return remindAt; }
    public void setRemindAt(LocalDateTime remindAt) { this.remindAt = remindAt; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
