package com.pblgllgs.sb3ss6securityuserrolejjwt.service;

import com.pblgllgs.sb3ss6securityuserrolejjwt.model.Role;
import com.pblgllgs.sb3ss6securityuserrolejjwt.model.User;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addToUser(String username, String roleName);
}
