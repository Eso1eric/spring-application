package com.daniil.gloom.domain.enums;

public enum Permissions {

    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
