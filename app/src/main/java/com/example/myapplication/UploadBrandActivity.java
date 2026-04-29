package com.example.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class UploadBrandActivity extends AppCompatActivity {

    private EditText etBrandName, etSubCategory, etAboutBrand, etMinInvestment, etMaxInvestment;
    private EditText etBrandFee, etAnticipatedReturn, etPaybackPeriod, etAreaRequired;
    private EditText etFranchiseOutlets, etEstablishedYear, etOperationCommenced, etFranchiseCommenced;
    private Spinner spCategory;
    private CheckBox cbIsRenewable;
    private Button btnSubmitBrand, btnCancel;

    private String[] categories = {
            "Food & Beverages", "Retail & Fashion", "Health & Wellness",
            "Education", "Beauty & Salon", "Automobiles",
            "Electronics", "Real Estate"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_brand);

        // Initialize Views
        etBrandName = findViewById(R.id.etBrandName);
        spCategory = findViewById(R.id.spCategory);
        etSubCategory = findViewById(R.id.etSubCategory);
        etAboutBrand = findViewById(R.id.etAboutBrand);
        etMinInvestment = findViewById(R.id.etMinInvestment);
        etMaxInvestment = findViewById(R.id.etMaxInvestment);
        etBrandFee = findViewById(R.id.etBrandFee);
        etAnticipatedReturn = findViewById(R.id.etAnticipatedReturn);
        etPaybackPeriod = findViewById(R.id.etPaybackPeriod);
        etAreaRequired = findViewById(R.id.etAreaRequired);
        etFranchiseOutlets = findViewById(R.id.etFranchiseOutlets);
        etEstablishedYear = findViewById(R.id.etEstablishedYear);
        etOperationCommenced = findViewById(R.id.etOperationCommenced);
        etFranchiseCommenced = findViewById(R.id.etFranchiseCommenced);
        cbIsRenewable = findViewById(R.id.cbIsRenewable);
        btnSubmitBrand = findViewById(R.id.btnSubmitBrand);
        btnCancel = findViewById(R.id.btnCancel);

        // Setup Category Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        // Date Pickers
        etOperationCommenced.setOnClickListener(v -> showDatePicker(etOperationCommenced));
        etFranchiseCommenced.setOnClickListener(v -> showDatePicker(etFranchiseCommenced));

        btnSubmitBrand.setOnClickListener(v -> {
            if (validateForm()) {
                Toast.makeText(this, "Brand details submitted successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnCancel.setOnClickListener(v -> finish());
    }

    private void showDatePicker(final EditText dateField) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> dateField.setText(year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth),
                year, month, day);
        datePickerDialog.show();
    }

    private boolean validateForm() {
        if (etBrandName.getText().toString().isEmpty()) {
            etBrandName.setError("Required");
            return false;
        }
        // Add more validations as needed
        return true;
    }
}
