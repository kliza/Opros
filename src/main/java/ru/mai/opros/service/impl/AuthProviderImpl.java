package ru.mai.opros.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mai.opros.service.AuthProvider;
import ru.mai.opros.service.UserService;

@Service
@RequiredArgsConstructor
@Profile("!local")
public class AuthProviderImpl implements AuthProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = userService.loadUserByUsername(authentication.getName());
        String credentials = (String) authentication.getCredentials();
        authentication.setAuthenticated(passwordEncoder.matches(credentials, userDetails.getPassword()));

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
