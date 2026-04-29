package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private ImageView ivAdCarousel, btnNavFavorites, btnNavProfile;
    private TextView tvWelcomeDashboard;
    private LinearLayout layoutCategories;
    private RecyclerView rvBrands;
    private GridLayout gridSimilarSites;
    private Button btnDashUploadBrand;
    private View btnNavLogout;

    private int currentAdIndex = 0;
    // Using default android resources as placeholders for the images provided in React code
    private int[] adImages = {
        android.R.drawable.ic_menu_gallery, 
        android.R.drawable.ic_menu_camera, 
        android.R.drawable.ic_menu_slideshow,
        android.R.drawable.ic_menu_manage,
        android.R.drawable.ic_menu_info_details
    };
    private String[] adUrls = {
        "https://franchising.bk.com/franchise-fee",
        "https://www.vrlgroup.in/vrl_investors_desk.aspx",
        "https://franchise.tealogy.in/",
        "https://www.lakmesalon.in/",
        "https://www.franchiseindia.com/brands/t-r-autoworks.104371"
    };

    private String[][] similarSites = {
        {"Entrepreneur", "https://www.entrepreneur.com"},
        {"Indian Retailer", "https://www.indianretailer.com"},
        {"Restaurant India", "https://www.restaurantindia.in/"},
        {"Franchise Nepal", "https://www.franchisenepal.com/"},
        {"Franchise BD", "https://www.franchisebangladesh.com/"},
        {"Brand License", "https://www.indianretailer.com/brandlicense/"},
        {"BusinessEx", "https://www.businessex.com/"},
        {"Bradford", "https://www.bradfordlicenseindia.com/"},
        {"Francorp", "https://www.francorp.in/"},
        {"FranGlobal", "https://www.franglobal.com/"},
        {"Gaurav Marya", "https://www.gauravmarya.com/"},
        {"MSME", "https://www.msme.in/"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize Views
        ivAdCarousel = findViewById(R.id.ivAdCarousel);
        tvWelcomeDashboard = findViewById(R.id.tvWelcomeDashboard);
        layoutCategories = findViewById(R.id.layoutCategories);
        rvBrands = findViewById(R.id.rvBrands);
        gridSimilarSites = findViewById(R.id.gridSimilarSites);
        btnDashUploadBrand = findViewById(R.id.btnDashUploadBrand);
        btnNavFavorites = findViewById(R.id.btnNavFavorites);
        btnNavProfile = findViewById(R.id.btnNavProfile);
        btnNavLogout = findViewById(R.id.btnNavLogout);

        setupCategories();
        setupAdCarousel();
        setupSimilarSites();

        // Navigation
        btnNavProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        btnNavFavorites.setOnClickListener(v -> Toast.makeText(this, "Favorites clicked", Toast.LENGTH_SHORT).show());
        btnDashUploadBrand.setOnClickListener(v -> startActivity(new Intent(this, UploadBrandActivity.class)));
        
        btnNavLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.btnAdNext).setOnClickListener(v -> nextAd());
        findViewById(R.id.btnAdPrev).setOnClickListener(v -> prevAd());
        
        // Mocking user name
        tvWelcomeDashboard.setText("Welcome, User!");
    }

    private void setupCategories() {
        String[] categories = {
            "All Categories", "Food & Beverages", "Retail & Fashion", 
            "Health & Wellness", "Education", "Beauty & Salon", 
            "Automobiles", "Electronics", "Real Estate"
        };
        for (String cat : categories) {
            Button btn = new Button(this);
            btn.setText(cat);
            btn.setAllCaps(false);
            btn.setTextColor(getResources().getColor(android.R.color.white));
            btn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#1A60A5FA")));
            
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            btn.setLayoutParams(params);
            
            btn.setOnClickListener(v -> Toast.makeText(this, "Filtering by: " + cat, Toast.LENGTH_SHORT).show());
            layoutCategories.addView(btn);
        }
    }

    private void setupAdCarousel() {
        ivAdCarousel.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(adUrls[currentAdIndex]));
            startActivity(intent);
        });
        
        // Auto slide every 5 seconds as in React code
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                nextAd();
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);
    }

    private void nextAd() {
        currentAdIndex = (currentAdIndex + 1) % adImages.length;
        ivAdCarousel.setImageResource(adImages[currentAdIndex]);
    }

    private void prevAd() {
        currentAdIndex = (currentAdIndex - 1 + adImages.length) % adImages.length;
        ivAdCarousel.setImageResource(adImages[currentAdIndex]);
    }

    private void setupSimilarSites() {
        for (String[] site : similarSites) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(android.R.drawable.ic_menu_view); // Placeholder for sim1.png etc.
            iv.setPadding(16, 16, 16, 16);
            
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            iv.setLayoutParams(params);

            iv.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(site[1]));
                startActivity(intent);
            });
            gridSimilarSites.addView(iv);
        }
    }
}
