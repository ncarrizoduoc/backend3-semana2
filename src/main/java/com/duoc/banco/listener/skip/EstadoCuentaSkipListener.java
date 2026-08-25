package com.duoc.banco.listener.skip;

import org.springframework.batch.core.listener.SkipListener;
import org.springframework.stereotype.Component;

import com.duoc.banco.model.EstadoCuenta;
import com.duoc.banco.model.MovimientoCuenta;

@Component
public class EstadoCuentaSkipListener implements SkipListener<MovimientoCuenta, EstadoCuenta>{

    @Override
    public void onSkipInRead(Throwable t){
        System.out.println("Error al leer movimiento de cuenta desde base de datos: " + t.getMessage());
    }

    @Override
    public void onSkipInProcess(MovimientoCuenta mov, Throwable t){
        System.out.println("Error al procesar estado de cuenta: " + mov + ". Causa del error: " + t.getMessage());
    }

    @Override
    public void onSkipInWrite(EstadoCuenta estado, Throwable t){
        System.out.println("Error al escribir estado de cuenta: " + estado + ". Causa del error: " + t.getMessage());
    }

}
