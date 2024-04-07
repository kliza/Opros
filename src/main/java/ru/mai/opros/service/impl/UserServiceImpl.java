package ru.mai.opros.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mai.opros.entity.User;
import ru.mai.opros.entity.enums.UserRole;
import ru.mai.opros.repo.UserRepo;
import ru.mai.opros.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь %s не обнаружен".formatted(username)));
    }

    @Override
    public User createNewUser(UserDetails userDetails) {
        User user = (User) userDetails;
        user.setRole(UserRole.POLL_MANAGER)
                .setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user);
    }
}
