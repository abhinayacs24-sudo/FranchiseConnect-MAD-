package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvProfileName, tvProfileEmail, tvDisplayMobile, tvDisplayGender, tvDisplayLocation, tvDisplayQualification;
    private Button btnEditProfile, btnProfileLogout, btnAddBrand;
    private LinearLayout layoutBrandsList;
    private TextView tvNoBrands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Views
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvDisplayMobile = findViewById(R.id.tvDisplayMobile);
        tvDisplayGender = findViewById(R.id.tvDisplayGender);
        tvDisplayLocation = findViewById(R.id.tvDisplayLocation);
        tvDisplayQualification = findViewById(R.id.tvDisplayQualification);
        
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnProfileLogout = findViewById(R.id.btnProfileLogout);
        btnAddBrand = findViewById(R.id.btnAddBrand);
        layoutBrandsList = findViewById(R.id.layoutBrandsList);
        tvNoBrands = findViewById(R.id.tvNoBrands);

        // Setup Listeners
        btnEditProfile.setOnClickListener(v -> {
            // For now, just show a message. 
            // In a full app, you would toggle to an EditActivity or change visibility of an edit form.
            Toast.makeText(this, "Edit Profile Clicked", Toast.LENGTH_SHORT).show();
        });

        btnAddBrand.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, UploadBrandActivity.class));
        });

        btnProfileLogout.setOnClickListener(v -> {
            // Clear local storage logic would go here
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Mock loading data
        loadMockData();
    }

    private void loadMockData() {
        tvProfileName.setText("Abbas Khan");
        tvProfileEmail.setText("abbaskhan.cs25@bmsce.ac.in");
        tvDisplayMobile.setText("9876543210");
        tvDisplayGender.setText("Male");
        tvDisplayLocation.setText("Karnataka, Bengaluru");
        tvDisplayQualification.setText("B.E/B.Tech");

        // Mock brand list logic
        tvNoBrands.setVisibility(View.VISIBLE); // Show "No Brands" by default
    }
}
