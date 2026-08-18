package com.duoc.banco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interes {
    private Long cuentaId;
    private String nombre;
    private Integer saldoInicial;
    private Integer saldoFinal;
    private Integer edad;
    private String tipo;
    private Double tasaInteres;

}
