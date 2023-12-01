package com.example.projbdexterne;

import android.app.ProgressDialog;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteEtudiantActivity extends AppCompatActivity {

    EditText idToDelete;
    Button deleteButton, annulerButoon;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supprimer_layout);

        idToDelete = findViewById(R.id.ncin_input);
        deleteButton = findViewById(R.id.buttonDelete);
        annulerButoon = findViewById(R.id.buttonAnnuler);

        annulerButoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DeleteEtudiant", "Delete button clicked"); // Check if this log message appears in Logcat
                deleteEtudiant();
            }
        });
    }

    private void deleteEtudiant() {
        String id = idToDelete.getText().toString();
        new DeleteEtudiantTask().execute(id);
    }

    private class DeleteEtudiantTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DeleteEtudiantActivity.this);
            dialog.setMessage("Deleting student...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String response = null;

            try {
                String id = params[0];
                URL url = new URL("http://10.0.2.2/php/AndroidProject/api/delete_etudiant.php?id=" + id);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }

                response = sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            dialog.dismiss();

            Log.d("DeleteEtudiant", "Server Response: " + response); // Log the server response

            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    Log.d("DeleteEtudiant", "Success: " + success); // Log the success value
                    Log.d("DeleteEtudiant", "Message: " + message); // Log the message value

                    if (success == 1) {
                        Toast.makeText(DeleteEtudiantActivity.this, "Student deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DeleteEtudiantActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(DeleteEtudiantActivity.this, "Error deleting student", Toast.LENGTH_SHORT).show();
            }
        }
    }
}