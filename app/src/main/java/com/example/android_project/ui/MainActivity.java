package com.example.android_project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {
    Button buttonAjouter, buttonSupprimer, buttonModifier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        buttonAjouter = findViewById(R.id.ajouter);
        buttonSupprimer = findViewById(R.id.Supprimer);
        buttonModifier = findViewById(R.id.Modifier);

        // Set onClickListener for Ajouter button
        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AjouterEtudiantActivity
                Intent intent = new Intent(MainActivity.this, AddEtudiantActivity.class);
                startActivity(intent);
            }
        });

        // Set onClickListener for Supprimer button
        buttonSupprimer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to DeleteEtudiantActivity
                Intent intent = new Intent(MainActivity.this, DeleteEtudiantActivity.class);
                startActivity(intent);
            }
        });
        // Your implementation to fetch data, add, update, or delete etudiants
        ApiService service = RetrofitClientInstance.getApiService();

        // Example: Fetching data
        Call<List<Etudiant>> call = service.getEtudiants();
        call.enqueue(new Callback<List<Etudiant>>() {
            @Override
            public void onResponse(Call<List<Etudiant>> call, Response<List<Etudiant>> response) {
                if (response.isSuccessful()) {
                    List<Etudiant> etudiantsList = response.body();
                    // Process the etudiantsList here (populate UI, etc.)
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Etudiant>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}