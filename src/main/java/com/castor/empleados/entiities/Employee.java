package com.castor.empleados.entiities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "empleados")
//@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cedula")
    private Long numberId;

    @Column(name = "nombre")
    private String name;

    //Ruta de la foto
    @Column(name = "foto")
    @Lob
    private byte[] picture;

    @Column(name = "fecha_ingreso")
    private LocalDate entryDate;

    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    @Transient
    private Long idPosition;

    public Long getIdPosition() {
        return idPosition;
    }

    public void setIdPosition(Long idPosition) {
        this.idPosition = idPosition;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumberId() {
        return numberId;
    }

    public void setNumberId(Long numberId) {
        this.numberId = numberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", numberId=" + numberId +
                ", name='" + name + '\'' +
                ", entryDate=" + entryDate +
                ", position=" + position +
                '}';
    }
}
