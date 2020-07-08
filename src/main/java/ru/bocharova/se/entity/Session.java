package ru.bocharova.se.entity;

import org.jetbrains.annotations.Nullable;

public class Session extends AbstractEntity{
    @Nullable
    String userId;

    @Nullable
    String userLogin;


    @Nullable
    public String getUserId() {
        return userId;
    }

    public void setUserId(@Nullable String userId) {
        this.userId = userId;
    }

    @Nullable
    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(@Nullable String userLogin) {
        this.userLogin = userLogin;
    }

    public Session(
            @Nullable final String userId,
            @Nullable final String userLogin) {
        this.userId = userId;
        this.userLogin = userLogin;
    }

    public Session() {
    }
}
