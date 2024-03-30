package ru.mai.opros.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.mai.opros.entity.User;

public interface UserService extends UserDetailsService {

    User createNewUser(UserDetails userDetails);
}
