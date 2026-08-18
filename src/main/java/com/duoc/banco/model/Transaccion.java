package com.duoc.banco.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaccion {

    private Long id;
    private LocalDate fecha;
    private Integer monto;
    private String tipo;
    private String observaciones;

}
