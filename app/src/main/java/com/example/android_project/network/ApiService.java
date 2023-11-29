package com.example.android_project.network;

import com.example.android_project.model.Etudiant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("fetch_etudiants.php")
    Call<List<Etudiant>> getEtudiants();

    @POST("add_etudiant.php")
    Call<Void> addEtudiant(@Body Etudiant etudiant);

    @POST("update_etudiant.php")
    Call<Void> updateEtudiant(@Body Etudiant etudiant);

    @DELETE("delete_etudiant.php")
    Call<Void> deleteEtudiant(@Query("id") int id);
}

