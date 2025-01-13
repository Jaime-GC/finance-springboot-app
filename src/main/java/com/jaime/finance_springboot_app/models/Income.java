package com.jaime.finance_springboot_app.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "incomes")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private int amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    //Esto significa que un usuario puede tener muchos ingresos pero un ingreso solo puede pertenecer a un usuario
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    

    //Esto significa que una categoría puede tener muchos ingresos pero un ingreso solo puede pertenecer a una categoría
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;
}
