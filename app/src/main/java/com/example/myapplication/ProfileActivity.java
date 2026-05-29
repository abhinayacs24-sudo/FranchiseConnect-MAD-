package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvProfileName, tvProfileEmail, tvDisplayMobile, tvDisplayGender, tvDisplayLocation, tvDisplayQualification;
    private Button btnEditProfile, btnProfileLogout, btnAddBrand;
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
        tvNoBrands = findViewById(R.id.tvNoBrands);

        btnAddBrand.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, UploadBrandActivity.class));
        });

        btnProfileLogout.setOnClickListener(v -> {
            getSharedPreferences("FranchiseConnect", MODE_PRIVATE).edit().clear().apply();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        fetchProfile();
    }

    private void fetchProfile() {
        String userId = getSharedPreferences("FranchiseConnect", MODE_PRIVATE).getString("userId", null);
        String token = getSharedPreferences("FranchiseConnect", MODE_PRIVATE).getString("token", null);

        if (userId == null || token == null) {
            Toast.makeText(this, "Please login again", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getProfile("Bearer " + token, userId).enqueue(new retrofit2.Callback<User>() {
            @Override
            public void onResponse(retrofit2.Call<User> call, retrofit2.Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    
                    String fullName = user.getFirstName();
                    if (user.getMiddleName() != null && !user.getMiddleName().isEmpty()) {
                        fullName += " " + user.getMiddleName();
                    }
                    if (user.getLastName() != null && !user.getLastName().isEmpty()) {
                        fullName += " " + user.getLastName();
                    }
                    
                    tvProfileName.setText(fullName);
                    tvProfileEmail.setText(user.getEmail());
                    tvDisplayMobile.setText(user.getMobile() != null && !user.getMobile().isEmpty() ? user.getMobile() : "Not provided");
                    tvDisplayGender.setText(user.getGender() != null && !user.getGender().isEmpty() ? user.getGender() : "Not provided");
                    
                    String location = "";
                    if (user.getState() != null && !user.getState().isEmpty()) {
                        location += user.getState();
                    }
                    if (user.getCity() != null && !user.getCity().isEmpty()) {
                        if (!location.isEmpty()) location += ", ";
                        location += user.getCity();
                    }
                    tvDisplayLocation.setText(location.isEmpty() ? "Not provided" : location);
                    tvDisplayQualification.setText(user.getQualification() != null && !user.getQualification().isEmpty() ? user.getQualification() : "Not provided");

                    // Mock brand list logic
                    tvNoBrands.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to load profile details", Toast.LENGTH_SHORT).show();
                    loadMockData(); // Fallback to mock data on server error
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                loadMockData(); // Fallback to mock data on network error
            }
        });
    }

    private void loadMockData() {
        tvProfileName.setText("Abbas Khan");
        tvProfileEmail.setText("abbaskhan.cs25@bmsce.ac.in");
        tvDisplayMobile.setText("9876543210");
        tvDisplayGender.setText("Male");
        tvDisplayLocation.setText("Karnataka, Bengaluru");
        tvDisplayQualification.setText("B.E/B.Tech");
        tvNoBrands.setVisibility(View.VISIBLE);
    }
}
