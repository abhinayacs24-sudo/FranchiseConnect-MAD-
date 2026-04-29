package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etFirstName, etMiddleName, etLastName, etDob, etCity, etPincode, etAddress, etOccupation, etMobile, etEmail, etPassword, etConfirmPassword;
    private RadioGroup rgGender;
    private Spinner spState, spQualification;
    private Button btnRegister, btnClear;
    private TextView tvLoginLink;
    private ImageView ivProfilePreview;

    private final String[] indianStates = {
            "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat",
            "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra",
            "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan", "Sikkim",
            "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal",
            "Delhi", "Jammu & Kashmir", "Ladakh"
    };

    private final String[] qualifications = {"SSLC", "PUC", "Diploma", "B.Sc", "B.Com", "B.E/B.Tech", "BCA", "BBA", "M.Sc", "M.Com", "M.E/M.Tech", "MCA", "MBA", "PhD"};

    // Modern Photo Picker Launcher
    private final ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    ivProfilePreview.setImageURI(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize Views
        etFirstName = findViewById(R.id.etFirstName);
        etMiddleName = findViewById(R.id.etMiddleName);
        etLastName = findViewById(R.id.etLastName);
        etDob = findViewById(R.id.etDob);
        etCity = findViewById(R.id.etCity);
        etPincode = findViewById(R.id.etPincode);
        etAddress = findViewById(R.id.etAddress);
        etOccupation = findViewById(R.id.etOccupation);
        etMobile = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        rgGender = findViewById(R.id.rgGender);
        spState = findViewById(R.id.spState);
        spQualification = findViewById(R.id.spQualification);
        btnRegister = findViewById(R.id.btnRegister);
        btnClear = findViewById(R.id.btnClear);
        tvLoginLink = findViewById(R.id.tvLoginLink);
        ivProfilePreview = findViewById(R.id.ivProfilePreview);

        // Setup Spinners
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, indianStates);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spState.setAdapter(stateAdapter);

        ArrayAdapter<String> qualAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, qualifications);
        qualAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spQualification.setAdapter(qualAdapter);

        // Profile Photo Click - Using modern Photo Picker for integrated feel
        ivProfilePreview.setOnClickListener(v -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        // Date Picker
        etDob.setOnClickListener(v -> showDatePicker());

        // Buttons
        btnRegister.setOnClickListener(v -> handleRegistration());
        btnClear.setOnClickListener(v -> clearFields());
        tvLoginLink.setOnClickListener(v -> {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> etDob.setText(year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth),
                year, month, day);
        datePickerDialog.show();
    }

    private void handleRegistration() {
        if (validateFields()) {
            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateFields() {
        if (etFirstName.getText().toString().trim().isEmpty()) {
            etFirstName.setError("First Name is required");
            return false;
        }
        if (etLastName.getText().toString().trim().isEmpty()) {
            etLastName.setError("Last Name is required");
            return false;
        }
        if (rgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select Gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etEmail.getText().toString().trim().isEmpty()) {
            etEmail.setError("Email is required");
            return false;
        }
        if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            etConfirmPassword.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    private void clearFields() {
        etFirstName.setText("");
        etMiddleName.setText("");
        etLastName.setText("");
        etDob.setText("");
        etCity.setText("");
        etPincode.setText("");
        etAddress.setText("");
        etOccupation.setText("");
        etMobile.setText("");
        etEmail.setText("");
        etPassword.setText("");
        etConfirmPassword.setText("");
        ivProfilePreview.setImageResource(android.R.drawable.ic_menu_camera);
        rgGender.clearCheck();
        spState.setSelection(0);
        spQualification.setSelection(0);
    }
}
