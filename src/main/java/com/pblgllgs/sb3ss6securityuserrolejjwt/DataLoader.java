package com.pblgllgs.sb3ss6securityuserrolejjwt;
/*
 *
 * @author pblgl
 * Created on 26-03-2024
 *
 */

import com.pblgllgs.sb3ss6securityuserrolejjwt.model.Role;
import com.pblgllgs.sb3ss6securityuserrolejjwt.model.User;
import com.pblgllgs.sb3ss6securityuserrolejjwt.repository.UserRepository;
import com.pblgllgs.sb3ss6securityuserrolejjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserService userService;
    private final UserRepository userRepo;

    @Override
    public void run(String... args) throws Exception {
        if ((long) userRepo.findAll().size() == 0) {

            userService.saveRole(Role.builder()
                    .name("ROLE_USER").description("user").build());
            userService.saveRole(Role.builder()
                    .name("ROLE_ADMIN").description("admin").build());
            userService.saveRole(Role.builder()
                    .name("ROLE_MANAGER").description("manager").build());

            userService.saveUser(new User("user", "user@gmail.com", "pass", "33333333", new HashSet<>()));
            userService.saveUser(new User("admin", "admin@gmail.com", "pass", "11111111", new HashSet<>()));
            userService.saveUser(new User("manager", "manager@gmail.com", "pass", "22222222", new HashSet<>()));

            userService.addToUser("user@gmail.com", "ROLE_USER");
            userService.addToUser("admin@gmail.com", "ROLE_ADMIN");
            userService.addToUser("manager@gmail.com", "ROLE_MANAGER");

        }
    }
}
