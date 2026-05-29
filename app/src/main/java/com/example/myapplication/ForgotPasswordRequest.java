package com.example.myapplication;

public class ForgotPasswordRequest {
    private String userId;
    private String email;
    private String dob;
    private String state;
    private String city;
    private String pincode;
    private String gender;
    private String newPassword;

    // Constructor for details verification
    public ForgotPasswordRequest(String email, String dob, String state, String city, String pincode, String gender) {
        this.email = email;
        this.dob = dob;
        this.state = state;
        this.city = city;
        this.pincode = pincode;
        this.gender = gender;
    }

    // Constructor for password reset using userId
    public ForgotPasswordRequest(String userId, String newPassword) {
        this.userId = userId;
        this.newPassword = newPassword;
    }
}
