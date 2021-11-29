package com.franciscorivera.patitas.poo;

public class cMascota
{
    private String nameMascota;
    private String typeMascota;
    private String dateMascota;
    private boolean isChip;
    private int isVacuna;
    private int isInscription;
    private String tamMascota;
    private double pesoMascota;
    private cDuenio oD;

    public cMascota(cDuenio oD) {
        this.oD = oD;
    }


    public String getNameMascota() {
        return nameMascota;
    }

    public void setNameMascota(String nameMascota) {
        this.nameMascota = nameMascota;
    }

    public String getTypeMascota() {
        return typeMascota;
    }

    public void setTypeMascota(String typeMascota) {
        this.typeMascota = typeMascota;
    }

    public String getDateMascota() {
        return dateMascota;
    }

    public void setDateMascota(String dateMascota) {
        this.dateMascota = dateMascota;
    }

    public boolean isChip() {
        return isChip;
    }

    public void setChip(boolean chip) {
        isChip = chip;
    }

    public int isVacuna() {
        return isVacuna;
    }

    public void setVacuna(int vacuna) {
        isVacuna = vacuna;
    }

    public int isInscription() {
        return isInscription;
    }

    public void setInscription(int inscription) {
        isInscription = inscription;
    }

    public String getTamMascota() {
        return tamMascota;
    }

    public void setTamMascota(String tamMascota) {
        this.tamMascota = tamMascota;
    }

    public double getPesoMascota() {
        return pesoMascota;
    }

    public void setPesoMascota(double pesoMascota) {
        this.pesoMascota = pesoMascota;
    }

    public cDuenio getoD() {
        return oD;
    }

    public void setoD(cDuenio oD) {
        this.oD = oD;
    }
}
