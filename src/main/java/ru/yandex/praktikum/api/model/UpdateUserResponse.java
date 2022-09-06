package ru.yandex.praktikum.api.model;

public class UpdateUserResponse {
    public Boolean ok;

    public UpdateUserResponse() {
    }
    private Boolean success;
    private User user;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
