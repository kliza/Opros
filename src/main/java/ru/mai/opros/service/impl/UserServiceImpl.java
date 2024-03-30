package ru.mai.opros.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mai.opros.entity.User;
import ru.mai.opros.repo.UserRepo;
import ru.mai.opros.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь %s не обнаружен".formatted(username)));
    }

    @Override
    public User createNewUser(UserDetails userDetails) {
        return userRepo.save((User) userDetails);
    }
}
