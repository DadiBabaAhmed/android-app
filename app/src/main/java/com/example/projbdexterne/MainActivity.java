package com.example.projbdexterne;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
     Button buttonAjouter, buttonSupprimer,buttonAfficher;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.home_layout);
            buttonAjouter = findViewById(R.id.ajouter);
            buttonSupprimer = findViewById(R.id.supprimer);
            buttonAfficher = findViewById(R.id.afficher);
            buttonAfficher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, read_activity.class);
                    startActivity(intent);
                }
            });

            buttonAjouter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, AddEtudiantActivity.class);
                    startActivity(intent);
                }
            });


            buttonSupprimer.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, DeleteEtudiantActivity.class);
                    startActivity(intent);
                }
            });
        }
    }



