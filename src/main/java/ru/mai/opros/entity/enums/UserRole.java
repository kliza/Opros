package ru.mai.opros.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    POLL_MANAGER,
    POLL_ANALYST;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
