package com.example.android_project.model;

public class Etudiant {
    private int id,NCIN;
    private String NCE, Nom, Prenom, Classe;

    public Etudiant(){}
    public Etudiant(int ncin,String nce,String nom,String prenom,String classe){
        this.Nom=nom;
        this.Prenom=prenom;
        this.NCIN=ncin;
        this.NCE=nce;
        this.Classe=classe;
    }

    public int getNCIN(){
        return this.NCIN;
    }
    public void setNCIN(int ncin){
        this.NCIN=ncin;
    }
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getNCE(){
        return this.NCE;
    }
    public void setNCE(String nce){
        this.NCE=nce;
    }
    public String getNom(){
        return this.Nom;
    }
    public void setNom(String nom){
        this.Nom=nom;
    }
    public String getPrenom(){
        return this.Prenom;
    }
    public void setPrenom(String prenom){
        this.Prenom=prenom;
    }
    public String getClasse(){
        return this.Classe;
    }
    public void setClasse(String classe){
        this.Classe=classe;
    }
}
