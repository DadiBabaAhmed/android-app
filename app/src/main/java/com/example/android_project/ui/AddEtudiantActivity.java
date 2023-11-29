package com.example.android_project.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_project.R;
import com.example.android_project.model.Etudiant;
import com.example.android_project.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

;

public class AddEtudiantActivity extends AppCompatActivity {

    EditText editTextNom, editTextPrenom, editTextNCIN, editTextNCE;
    Button buttonAjouter, buttonAnnuler;
    ApiService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_layout);

        //service = RetrofitClientInstance.getApiService();

        // Initialize views
        editTextNom = findViewById(R.id.nom);
        editTextPrenom = findViewById(R.id.prenom);
        editTextNCIN = findViewById(R.id.ncin);
        editTextNCE = findViewById(R.id.nce);

        buttonAjouter = findViewById(R.id.btn_ajouter);
        buttonAnnuler = findViewById(R.id.btn_annuler);

        // Set onClickListener for Ajouter button
        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from EditText fields
                String nom = editTextNom.getText().toString();
                String prenom = editTextPrenom.getText().toString();
                String ncin = editTextNCIN.getText().toString();
                String nce = editTextNCE.getText().toString();

                // Create an Etudiant object with the entered data
                Etudiant newEtudiant = new Etudiant();
                newEtudiant.setNom(nom);
                newEtudiant.setPrenom(prenom);
                newEtudiant.setNCIN(Integer.parseInt(ncin));
                newEtudiant.setNCE(nce);

                // Call the Retrofit method to add etudiant
                Call<Void> call = service.addEtudiant(newEtudiant);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Handle success: Record added successfully
                            Toast.makeText(AddEtudiantActivity.this, "Record added successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle failure: Error adding record
                            Toast.makeText(AddEtudiantActivity.this, "Failed to add record", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Handle failure: Network error or other issues
                        Toast.makeText(AddEtudiantActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Set onClickListener for Annuler button
        buttonAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle cancel action (e.g., go back to previous activity)
                finish();
            }
        });
    }
}
