package org.example.notificationservice.service;

import org.example.notificationservice.dto.ReminderRequest;
import org.example.notificationservice.model.Reminder;
import org.example.notificationservice.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private TaskScheduler taskScheduler;

    public Reminder scheduleReminder(ReminderRequest request) {
        Reminder reminder = new Reminder();
        reminder.setMessage(request.getMessage());
        reminder.setRemindAt(request.getRemindAt());
        reminder.setEmail(request.getEmail());

        Reminder savedReminder = reminderRepository.save(reminder); // Сохраняем и получаем объект с ID

        taskScheduler.schedule(
                () -> sendReminder(savedReminder),
                Date.from(request.getRemindAt().atZone(ZoneId.systemDefault()).toInstant())
        );
        return savedReminder;
    }

    private void sendReminder(Reminder reminder) {
        System.out.println("🔔 Reminder: " + reminder.getMessage());
    }

    public Reminder getReminder(Long id) {
        return reminderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reminder not found"));
    }

    public void deleteReminder(Long id) {
        if (!reminderRepository.existsById(id)) {
            throw new RuntimeException("Reminder with ID " + id + " not found");
        }
        reminderRepository.deleteById(id);
    }

    public List<Reminder> getAllReminders() {
        return reminderRepository.findAll();
    }
}