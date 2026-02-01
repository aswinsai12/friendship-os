package com.friendshipos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.friendshipos.model.User;
import com.friendshipos.repo.UserRepo;

import com.friendshipos.repo.UserRepo;
import com.friendshipos.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class FriendshipOsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendshipOsApplication.class, args);
    }

    // 🔥 THIS RUNS WHEN APP STARTS
  @Bean
CommandLineRunner createUsers(UserRepo repo, PasswordEncoder encoder) {
    return args -> {

        if (repo.findByUsername("admin").isEmpty()) {
            repo.save(User.builder()
                    .username("admin")
                    .password(encoder.encode("admin123"))
                    .role("ADMIN")
                    .build());
        }

        if (repo.findByUsername("Jyothendra").isEmpty()) {
            repo.save(User.builder()
                    .username("Jyothendra")
                    .password(encoder.encode("happyBirthday"))
                    .role("USER") // birthday boy
                    .build());
        }
    };
}



}
