package ru.mai.opros.service;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;

@Profile("!local")
public interface AuthProvider extends AuthenticationProvider {
}
