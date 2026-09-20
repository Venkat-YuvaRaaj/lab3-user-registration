package com.elkabani.userregistration;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public UserService(UserRepository userRepository,
                       NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public void registerUser(User user) {

        // Check whether the user is already registered
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            System.out.println("Registration failed: User with email "
                    + user.getEmail() + " is already registered.");
            return;
        }

        // Save the new user
        userRepository.save(user);

        System.out.println("User registered successfully: "
                + user.getEmail());

        // Send confirmation notification
        notificationService.send(
                "Registration successful! Welcome " + user.getName(),
                user.getEmail()
        );
    }
}