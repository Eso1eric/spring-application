package com.daniil.gloom.domain.enums;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Roles {

    USER(Sets.newHashSet()),
    MODERATOR(Sets.newHashSet(Permissions.USER_READ)),
    ADMIN(Sets.newHashSet(Permissions.USER_READ, Permissions.USER_WRITE));

    private final Set<Permissions> permissions;

    Roles(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> grantedPermissions = getPermissions()
                .stream().map(permissions -> new SimpleGrantedAuthority(
                        permissions.getPermission()
                )).collect(Collectors.toSet());

        grantedPermissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return grantedPermissions;
    }

}
