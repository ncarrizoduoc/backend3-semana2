package com.duoc.banco.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.duoc.banco.model.Transaccion;

@Component
public class TransaccionItemProcessor implements ItemProcessor<Transaccion, Transaccion> {

    public Transaccion process(Transaccion transaccion) {
        List<String> observaciones = new ArrayList<>();
        // Validar el monto
        if (transaccion.getMonto() < 0) {
            observaciones.add("Monto no válido (menor a cero)");
        } else if (transaccion.getMonto() == 0){
            observaciones.add("Monto no válido (igual a cero)");
        }

        if (transaccion.getMonto() == null){
            observaciones.add("La transaccion no tiene un monto asociado");
        }

        // Validar que la fecha no sea posterior a la fecha actual
        if (transaccion.getFecha().isAfter(java.time.LocalDate.now())) {
            observaciones.add("Fecha no válida (mayor a la fecha actual)");
        }

        if (transaccion.getFecha().getYear() < 1900){
            observaciones.add("Fecha no válida: Anterior al año 1900");
        }

        // Validar que el tipo de transacción sea "DEBITO" o "CREDITO"
        transaccion.setTipo(transaccion.getTipo().toUpperCase());
        if (!transaccion.getTipo().equals("DEBITO") && !transaccion.getTipo().equals("CREDITO")) {
            observaciones.add("Tipo de transacción no válido (debe ser DEBITO o CREDITO)");
        }

        // Guardar observaciones como String
        if (!observaciones.isEmpty()) {
            transaccion.setObservaciones(String.join("; ", observaciones));
            transaccion.setObservaciones(transaccion.getObservaciones().toUpperCase());
        } else {
            transaccion.setObservaciones("SIN OBSERVACIONES");
        }

        return transaccion;
    }

}
