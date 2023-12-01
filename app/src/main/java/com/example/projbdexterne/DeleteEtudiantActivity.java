package com.example.projbdexterne;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DeleteEtudiantActivity extends AppCompatActivity {

    Spinner spinnerNCIN;
    Button deleteButton;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supprimer_layout);

        spinnerNCIN = findViewById(R.id.spinner_ncin);
        deleteButton = findViewById(R.id.btn_supprimer);

        fillSpinnerWithNCIN();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEtudiant();
            }
        });
    }

    private void fillSpinnerWithNCIN() {
        new FetchNCINsTask().execute();
    }

    private class FetchNCINsTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DeleteEtudiantActivity.this);
            dialog.setMessage("Fetching NCINs...");
            dialog.show();
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            ArrayList<String> ncinList = new ArrayList<>();

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL("http://your_server_address/get_ncins.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }

                JSONArray jsonArray = new JSONArray(sb.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String ncin = jsonObject.getString("NCIN");
                    ncinList.add(ncin);
                }

            } catch (IOException | JSONException e) {
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

            return ncinList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> ncinList) {
            dialog.dismiss();

            ArrayAdapter<String> adapter = new ArrayAdapter<>(DeleteEtudiantActivity.this,
                    android.R.layout.simple_spinner_dropdown_item, ncinList);
            spinnerNCIN.setAdapter(adapter);
        }
    }

    private void deleteEtudiant() {
        String selectedNCIN = spinnerNCIN.getSelectedItem().toString();
        new DeleteEtudiantTask().execute(selectedNCIN);
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
                URL url = new URL("http://10.0.2.2/php/AndroidProject/api/add_etudiant.php?id=" + id);
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

            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

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
