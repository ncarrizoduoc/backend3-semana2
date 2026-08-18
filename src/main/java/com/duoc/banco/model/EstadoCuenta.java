package com.duoc.banco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoCuenta {
    private Long cuentaId;
    private Integer ingresos;
    private Integer salidas;
    private Integer diferencia;

}
