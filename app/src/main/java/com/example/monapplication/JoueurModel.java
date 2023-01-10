package com.example.monapplication;

public class JoueurModel {
    private String pseudo;
    private int vie;

    public JoueurModel(String pseudo, int vie) {
        this.pseudo = pseudo;
        this.vie = vie;
    }

    public String getPseudo() {
        return pseudo;
    }


    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    @Override
    public String toString() {
        return pseudo  +" , " + vie+" vies";
    }

}
