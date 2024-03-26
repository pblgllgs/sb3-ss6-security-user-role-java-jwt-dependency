package com.pblgllgs.sb3ss6securityuserrolejjwt.service.impl;
/*
 *
 * @author pblgl
 * Created on 26-03-2024
 *
 */

import com.pblgllgs.sb3ss6securityuserrolejjwt.model.Role;
import com.pblgllgs.sb3ss6securityuserrolejjwt.model.User;
import com.pblgllgs.sb3ss6securityuserrolejjwt.repository.RoleRepository;
import com.pblgllgs.sb3ss6securityuserrolejjwt.repository.UserRepository;
import com.pblgllgs.sb3ss6securityuserrolejjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>());
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addToUser(String username, String roleName) {
        User user = userRepo.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("USER_NOT_FOUND_WITH_NAME: " + username)
                );
        Role role = roleRepo.findByName(roleName)
                .orElseThrow(
                        () -> new RuntimeException("ROLE_NOT_FOUND_WITH_NAME: " + roleName)
                );
        user.getRoles().add(role);
        userRepo.save(user);
    }
}
