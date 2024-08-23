package com.example.serreagricole.entitie;

import java.util.Date;

public class SerreDTO {
    private int serreId;
    private float temperature;
    private float humidite;
    private float humiditeSol;
    private float niveauEau;
    private Date date;
    private String etat ;

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public SerreDTO(int serreId, float temperature, float humidite, float humiditeSol, float niveauEau, Date date) {
        this.serreId = serreId;
        this.temperature = temperature;
        this.humidite = humidite;
        this.humiditeSol = humiditeSol;
        this.niveauEau = niveauEau;
        this.date = date;
    }

    public SerreDTO(int serreId, float temperature, float humidite, float humiditeSol, float niveauEau, Date date, String etat) {
        this.serreId = serreId;
        this.temperature = temperature;
        this.humidite = humidite;
        this.humiditeSol = humiditeSol;
        this.niveauEau = niveauEau;
        this.date = date;
        this.etat=etat;
    }

    public int getSerreId() {
        return serreId;
    }

    public void setSerreId(int serreId) {
        this.serreId = serreId;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidite() {
        return humidite;
    }

    public void setHumidite(float humidite) {
        this.humidite = humidite;
    }

    public float getHumiditeSol() {
        return humiditeSol;
    }

    public void setHumiditeSol(float humiditeSol) {
        this.humiditeSol = humiditeSol;
    }

    public float getNiveauEau() {
        return niveauEau;
    }

    public void setNiveauEau(float niveauEau) {
        this.niveauEau = niveauEau;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
