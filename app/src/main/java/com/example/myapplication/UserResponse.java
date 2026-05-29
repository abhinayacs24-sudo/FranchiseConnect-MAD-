package com.example.myapplication;

public class UserResponse {
    private String message;
    private String token;
    private UserData user;

    public static class UserData {
        private String id;
        private String email;
        private String name;

        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
    }

    public String getMessage() { return message; }
    public String getToken() { return token; }
    public UserData getUser() { return user; }
}
