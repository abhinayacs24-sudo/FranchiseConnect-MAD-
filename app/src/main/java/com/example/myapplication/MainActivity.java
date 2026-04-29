package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView tvBrandCount, tvUserCount, tvEmail1;
    private Button btnGetStarted, btnNavRegister;
    private TextView btnNavLogin;
    // Note: 10.0.2.2 is the special alias for localhost when running in the Android Emulator
    private final String apiBaseUrl = "http://10.0.2.2:5000"; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        tvBrandCount = findViewById(R.id.tvBrandCount);
        tvUserCount = findViewById(R.id.tvUserCount);
        btnGetStarted = findViewById(R.id.btnGetStarted);
        tvEmail1 = findViewById(R.id.tvEmail1);
        btnNavLogin = findViewById(R.id.btnNavLogin);
        btnNavRegister = findViewById(R.id.btnNavRegister);

        // Set Click Listeners
        btnNavLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnNavRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        tvEmail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:abbaskhan.cs25@bmsce.ac.in"));
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        // Fetch Stats from API
        fetchStats();
    }

    private void fetchStats() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            String brandCount = fetchData(apiBaseUrl + "/api/brands/count");
            String userCount = fetchData(apiBaseUrl + "/api/users/count");

            handler.post(() -> {
                if (brandCount != null) {
                    tvBrandCount.setText(brandCount);
                } else {
                    tvBrandCount.setText("0");
                }
                
                if (userCount != null) {
                    tvUserCount.setText(userCount);
                } else {
                    tvUserCount.setText("0");
                }
            });
        });
    }

    private String fetchData(String urlString) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                JSONObject jsonObject = new JSONObject(response.toString());
                return String.valueOf(jsonObject.optInt("count", 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
        }
        return null;
    }
}
