package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserSettingsController {

    @Autowired
    private UserRepository userRepository;

    public static class ProfileRequest {
        private String username;
        private String fullName;
        private String email;
        private String phone;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
    }

    public static class PasswordRequest {
        private String username;
        private String currentPassword;
        private String newPassword;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getCurrentPassword() { return currentPassword; }
        public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }

        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }

    public static class PreferencesRequest {
        private String username;
        private String language;
        private boolean emailNotifications;
        private boolean shiftReminders;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getLanguage() { return language; }
        public void setLanguage(String language) { this.language = language; }

        public boolean isEmailNotifications() { return emailNotifications; }
        public void setEmailNotifications(boolean emailNotifications) { this.emailNotifications = emailNotifications; }

        public boolean isShiftReminders() { return shiftReminders; }
        public void setShiftReminders(boolean shiftReminders) { this.shiftReminders = shiftReminders; }
    }

    @GetMapping("/profile")
    public User profile(@RequestParam String username) {
        return userRepository.findByUsername(username);
    }

    @PutMapping("/profile")
    public User updateProfile(@RequestBody ProfileRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        return userRepository.save(user);
    }

    @PutMapping("/password")
    public String changePassword(@RequestBody PasswordRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            return "User not found";
        }

        if (!user.getPassword().equals(request.getCurrentPassword())) {
            return "Current password is incorrect";
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return "Password changed successfully";
    }

    @PutMapping("/preferences")
    public User updatePreferences(@RequestBody PreferencesRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        user.setLanguage(request.getLanguage());
        user.setEmailNotifications(request.isEmailNotifications());
        user.setShiftReminders(request.isShiftReminders());

        return userRepository.save(user);
    }
}
