package com.example.android_project.ui;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

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