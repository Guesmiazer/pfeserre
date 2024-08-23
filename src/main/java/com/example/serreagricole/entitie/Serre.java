package com.example.serreagricole.entitie;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Serre implements Serializable {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;

    private int serreId;

    private float temperature;

    private float humidite;

    private float humiditeSol;

    private float niveauEau;

    private String etat;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


    @JsonIgnore
    @ManyToOne
    private User user;




    @PrePersist
    private  void onCreate (){
       date= new Date();
    }
}
