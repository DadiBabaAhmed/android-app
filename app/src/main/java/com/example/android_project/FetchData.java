package com.example.android_project;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchData implements Runnable {
    private OnDataFetchedListener listener;

    public FetchData(OnDataFetchedListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        ArrayList<Etudiant> etudiants = new ArrayList<>();

        String url = "http://localhost/php/AndroidProject/getAllInfo.php"; // Replace with your PHP script URL

        try {
            URL apiUrl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) apiUrl.openConnection();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            String jsonData = stringBuilder.toString();
            etudiants = parseJSON(jsonData);

            inputStream.close();
            urlConnection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        if (listener != null) {
            listener.onDataFetched(etudiants);
        }
    }

    private ArrayList<Etudiant> parseJSON(String jsonData) throws JSONException {
        ArrayList<Etudiant> etudiants = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonData);

        for (int i = 0; i < jsonArray.length(); i++) {
            Etudiant etudiant = new Etudiant();
            // Parse JSON and populate Etudiant object here
            etudiants.add(etudiant);
        }

        return etudiants;
    }

    public interface OnDataFetchedListener {
        void onDataFetched(ArrayList<Etudiant> etudiants);
    }
}
