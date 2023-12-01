package com.example.projbdexterne;

public class Etudiant {
    private int id, ncin;
    private String nom, prenom, nce, classe;

    public Etudiant(){}
    public Etudiant(int id, int ncin, String nom, String prenom, String nce, String classe) {
        this.id = id;
        this.ncin = ncin;
        this.nom = nom;
        this.prenom = prenom;
        this.nce = nce;
        this.classe = classe;
    }

    public int getId() {
        return id;
    }

    public int getNcin() {
        return ncin;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNce() {
        return nce;
    }

    public String getClasse() {
        return classe;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNcin(int ncin) {
        this.ncin = ncin;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNce(String nce) {
        this.nce = nce;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }


}
