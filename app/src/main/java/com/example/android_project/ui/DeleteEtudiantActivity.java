package com.example.android_project.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_project.R;
import com.example.android_project.model.Etudiant;
import com.example.android_project.network.ApiService;
import com.example.android_project.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteEtudiantActivity extends AppCompatActivity {

    Spinner spinnerNCIN;
    Button buttonSupprimer, buttonAnnuler;
    ApiService service;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supprimer_layout);

        // Initialize Retrofit service
        service = RetrofitClientInstance.getApiService();

        // Initialize views and buttons
        spinnerNCIN = findViewById(R.id.spinner_ncin);
        buttonSupprimer = findViewById(R.id.btn_supprimer);
        buttonAnnuler = findViewById(R.id.btn_annuler);

        // Call the method to populate the Spinner with NCINs
        populateNCINSpinner();

        // Set onClickListener for Supprimer button
        buttonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected NCIN from Spinner
                String selectedNCIN = spinnerNCIN.getSelectedItem().toString();

                // Call Retrofit method to delete etudiant based on the selected NCIN
                service.deleteEtudiant(Integer.parseInt(selectedNCIN)).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Handle successful deletion
                            Toast.makeText(DeleteEtudiantActivity.this, "Etudiant deleted successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle failure to delete etudiant
                            Toast.makeText(DeleteEtudiantActivity.this, "Failed to delete etudiant", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Handle failure: Network error or other issues
                        Toast.makeText(DeleteEtudiantActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Set onClickListener for Annuler button
        buttonAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity (home page in this case)
                finish();
            }
        });
    }

    private void populateNCINSpinner() {
        // Implement Retrofit call to fetch the list of NCINs from the database
        service.getEtudiants().enqueue(new Callback<List<Etudiant>>() {
            @Override
            public void onResponse(Call<List<Etudiant>> call, Response<List<Etudiant>> response) {
                if (response.isSuccessful()) {
                    List<Etudiant> etudiantsList = response.body();
                    // Populate the Spinner with the list of NCINs
                    ArrayAdapter<Etudiant> adapter = new ArrayAdapter<>(DeleteEtudiantActivity.this, android.R.layout.simple_spinner_item, etudiantsList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerNCIN.setAdapter(adapter);
                } else {
                    // Handle failure to fetch NCINs
                    Toast.makeText(DeleteEtudiantActivity.this, "Failed to fetch NCINs", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Etudiant>> call, Throwable t) {
                // Handle failure: Network error or other issues
                Toast.makeText(DeleteEtudiantActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

