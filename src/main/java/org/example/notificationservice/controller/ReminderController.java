package org.example.notificationservice.controller;

import org.example.notificationservice.dto.ReminderRequest;
import org.example.notificationservice.model.Reminder;
import org.example.notificationservice.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String TODO_LIST_URL = "http://localhost:9090/tasks";

    @PostMapping
    public ResponseEntity<Reminder> createReminder(@RequestBody ReminderRequest request) {
        Reminder reminder = reminderService.scheduleReminder(request); // Возвращаем сохраненный объект
        return ResponseEntity.ok(reminder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reminder> getReminder(@PathVariable Long id) {
        try {
            Reminder reminder = reminderService.getReminder(id);
            return ResponseEntity.ok(reminder);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReminder(@PathVariable Long id) {
        try {
            reminderService.deleteReminder(id);
            return ResponseEntity.ok("Reminder deleted!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reminder not found");
        }
    }

    @GetMapping
    public ResponseEntity<List<Reminder>> getAllReminders() {
        List<Reminder> reminders = reminderService.getAllReminders();
        return ResponseEntity.ok(reminders);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<TaskModel> getTaskDetails(@PathVariable String taskId) {
        try {
            TaskModel task = restTemplate.getForObject(TODO_LIST_URL + "/" + taskId, TaskModel.class);
            return ResponseEntity.ok(task);
        } catch (ResourceAccessException e) {
            System.err.println("To-Do List unavailable: " + e.getMessage());
            TaskModel fallback = new TaskModel();
            fallback.setTitle("Task details unavailable");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(fallback);
        }
    }

    // Внутренний класс для получения данных о задаче из To-Do List
    private static class TaskModel {
        private String id;
        private String title;
        private String description;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}