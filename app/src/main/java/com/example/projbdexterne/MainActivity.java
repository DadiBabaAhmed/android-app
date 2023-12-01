package com.example.projbdexterne;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText nom, prenom, NCIN, NCE, classe;
    Button ajouter, annuler, read;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_ajouter);

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        NCIN = findViewById(R.id.ncin);
        NCE = findViewById(R.id.nce);
        classe = findViewById(R.id.classe);
        ajouter = findViewById(R.id.btn_ajouter);
        annuler = findViewById(R.id.btn_annuler);
        read = findViewById(R.id.read_btn);

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEtudiant();
                Intent i = new Intent( MainActivity.this, read_activity.class);
                startActivity(i);
            }
        });
        read.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent( MainActivity.this, read_activity.class);
                        startActivity(i);
                    }
                }
        );
    }  // ...Other parts of your code
    private void addEtudiant() {
        String nomValue = nom.getText().toString();
        String prenomValue = prenom.getText().toString();
        int ncinValue = Integer.parseInt(NCIN.getText().toString());
        String nceValue = NCE.getText().toString();
        String classeValue = classe.getText().toString();

        Etudiant newEtudiant = new Etudiant(0, ncinValue, nomValue, prenomValue, nceValue, classeValue);
        new AddEtudiantTask().execute(newEtudiant);
    }

    private class AddEtudiantTask extends AsyncTask<Etudiant, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Adding student...");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Etudiant... etudiants) {
            try {
                Etudiant newEtudiant = etudiants[0];
                URL url = new URL("http://10.0.2.2/php/AndroidProject/api/add_etudiant.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                JSONObject etudiantJson = new JSONObject();
                etudiantJson.put("NCIN", newEtudiant.getNcin());
                etudiantJson.put("NCE", newEtudiant.getNce());
                etudiantJson.put("Nom", newEtudiant.getNom());
                etudiantJson.put("Prenom", newEtudiant.getPrenom());
                etudiantJson.put("classe", newEtudiant.getClasse());

                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(etudiantJson.toString().getBytes());
                outputStream.flush();
                outputStream.close();

                int responseCode = connection.getResponseCode();
                return responseCode == HttpURLConnection.HTTP_OK;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            dialog.dismiss();
            if (success) {
                Toast.makeText(MainActivity.this, "Student added successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Error adding student", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // ...Other parts of your code
}

