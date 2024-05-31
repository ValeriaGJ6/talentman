package com.talentman.sgthtalentman.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "nominas")
@Getter
@Setter
public class NominaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "idNomina")
    private Long id;

    private LocalDate fechaDePago;

    @Column(nullable = false)
    private double salarioBruto;

    private double auxilioTransporte;

    private double descuentoSalud;

    private double descuentoPension;

    private double salarioNeto;

    @Column(nullable = false)
    private int horasTrabajadas;

    private double adicionales;  

    @OneToOne()
    @JoinColumn(name = "idInformacionPersonal", referencedColumnName = "idInformacionPersonal", nullable = false)
    private InformacionPersonalModel informacionPersonal;
}
