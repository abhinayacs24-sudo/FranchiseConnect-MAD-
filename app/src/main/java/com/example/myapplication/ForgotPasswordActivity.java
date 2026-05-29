package com.example.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private LinearLayout llVerifyStep, llResetStep;
    private EditText etFpEmail, etDob, etCity, etPinCode, etNewPassword, etConfirmPassword;
    private Spinner spGender, spState;
    private Button btnVerifyDetails, btnResetPassword;
    private TextView tvBackToLogin;
    private String verifiedUserId = null;

    private String[] indianStates = {
            "Select State", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat",
            "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra",
            "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan", "Sikkim",
            "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal",
            "Delhi", "Jammu & Kashmir", "Ladakh"
    };

    private String[] genders = {"Select Gender", "Male", "Female", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize Views
        llVerifyStep = findViewById(R.id.llVerifyStep);
        llResetStep = findViewById(R.id.llResetStep);
        etFpEmail = findViewById(R.id.etFpEmail);
        etDob = findViewById(R.id.etDob);
        etCity = findViewById(R.id.etCity);
        etPinCode = findViewById(R.id.etPinCode);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        spGender = findViewById(R.id.spGender);
        spState = findViewById(R.id.spState);
        btnVerifyDetails = findViewById(R.id.btnVerifyDetails);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);

        // Setup Spinners
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(genderAdapter);

        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, indianStates);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spState.setAdapter(stateAdapter);

        // Date Picker for DOB
        etDob.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(ForgotPasswordActivity.this,
                    (view, year1, month1, dayOfMonth) -> etDob.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                    year, month, day);
            datePickerDialog.show();
        });

        btnVerifyDetails.setOnClickListener(v -> {
            if (validateVerificationFields()) {
                verifyDetails();
            }
        });

        btnResetPassword.setOnClickListener(v -> {
            if (validateResetFields()) {
                resetPassword();
            }
        });

        tvBackToLogin.setOnClickListener(v -> finish());
    }

    private void verifyDetails() {
        ForgotPasswordRequest request = new ForgotPasswordRequest(
                etFpEmail.getText().toString().trim(),
                etDob.getText().toString().trim(),
                spState.getSelectedItem().toString(),
                etCity.getText().toString().trim(),
                etPinCode.getText().toString().trim(),
                spGender.getSelectedItem().toString()
        );

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.verifyDetails(request).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    verifiedUserId = response.body().getUser().getId();
                    llVerifyStep.setVisibility(View.GONE);
                    llResetStep.setVisibility(View.VISIBLE);
                    Toast.makeText(ForgotPasswordActivity.this, "Details Verified. Set New Password.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Verification Failed: Details do not match", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetPassword() {
        ForgotPasswordRequest request = new ForgotPasswordRequest(verifiedUserId, etNewPassword.getText().toString().trim());

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.resetPassword(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Password Reset Successfully!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Reset Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateVerificationFields() {
        if (etFpEmail.getText().toString().isEmpty() ||
            spGender.getSelectedItemPosition() == 0 ||
            etDob.getText().toString().isEmpty() ||
            spState.getSelectedItemPosition() == 0 ||
            etCity.getText().toString().isEmpty() ||
            etPinCode.getText().toString().isEmpty()) {
            
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateResetFields() {
        String newPass = etNewPassword.getText().toString();
        String confirmPass = etConfirmPassword.getText().toString();

        if (newPass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(this, "Both fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!newPass.equals(confirmPass)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (newPass.length() < 8) {
            Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
