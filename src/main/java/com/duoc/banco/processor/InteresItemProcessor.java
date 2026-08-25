package com.duoc.banco.processor;

import java.util.Map;

import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.duoc.banco.exception.InteresNoValidoException;
import com.duoc.banco.model.Interes;

@Component
public class InteresItemProcessor implements ItemProcessor<Interes, Interes> {
    static final Map<String, Double> tasasInteres = Map.of(
        "AHORRO", 0.01, // Tasa del 1% para cuentas de ahorro
        "PRESTAMO", 0.05, // Tasa del 5% para cuentas de préstamo
        "HIPOTECA", 0.02 // Tasa del 2% para cuentas hipotecarias
    );

    @Override
    public Interes process(Interes interes){
        // Convertir nombre a mayúsculas
        interes.setNombre(interes.getNombre().toUpperCase());

        // Validar que el nombre del cliente no sea un texto en blanco (solo espacios) o null
        if (interes.getNombre().isBlank() || interes.getNombre().isEmpty()){
            throw new InteresNoValidoException(
                "Error en cuenta con ID " +
                interes.getCuentaId() +
                ": El nombre del cliente es un texto en blanco o null"
            );
        }

        // Validar que la edad del cliente sea mayor a 18
        if (interes.getEdad() < 18) {
            throw new InteresNoValidoException(
                "Error en cuenta con ID " +
                interes.getCuentaId() +
                ": La edad del cliente no es válida (menor a 18)");
        }

        //Validar que el saldo sea mayor o igual a 0
        if (interes.getSaldoInicial() < 0) {
            throw new InteresNoValidoException(
                "Error en cuenta con ID " +
                interes.getCuentaId() +
                ": El saldo no es válido (menor a cero)");
        }

        // Convertir tipo de interés a mayúsculas
        interes.setTipo(interes.getTipo().toUpperCase());

        // Calcular nuevo saldo segun tipo de transaccion e interes
        Double tasa = tasasInteres.get(interes.getTipo());
        if (tasa == null) {
            throw new InteresNoValidoException(
                "Error en cuenta con ID " +
                interes.getCuentaId() +
                ": Tipo de cuenta no válido");
        }
        Integer nuevoSaldo = (int) Math.round(interes.getSaldoInicial() * (1 + tasa));
        interes.setSaldoFinal(nuevoSaldo);
        interes.setTasaInteres(tasa);

        return interes;
    }

}
