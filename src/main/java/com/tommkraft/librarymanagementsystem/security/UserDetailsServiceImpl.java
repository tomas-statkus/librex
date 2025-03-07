package com.tommkraft.librarymanagementsystem.security;

import com.tommkraft.librarymanagementsystem.entity.User;
import com.tommkraft.librarymanagementsystem.repository.jpa.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("🔍 Attempting to load user: " + username);

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            logger.warn("❌ User not found in database: " + username);
            throw new UsernameNotFoundException("User not found: " + username);
        }

        User user = userOptional.get();
        logger.info("✅ User found: " + user.getUsername());
        logger.info("🔐 Stored Password (Hashed in DB): " + user.getPassword());

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new RuntimeException("❌ Password is missing for user: " + username);
        }

        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
        builder.password(user.getPassword()); // ✅ Uses stored hashed password

        builder.roles(user.getRoles().stream()
                .map(role -> role.replace("ROLE_", "")) // Ensure roles don't start with "ROLE_"
                .toArray(String[]::new));

        return builder.build();
    }

}