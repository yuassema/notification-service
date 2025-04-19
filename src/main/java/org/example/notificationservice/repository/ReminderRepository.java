package org.example.notificationservice.repository;

import org.example.notificationservice.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
}
