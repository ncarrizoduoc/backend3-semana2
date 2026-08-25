package com.duoc.banco.listener.skip;

import org.springframework.batch.core.listener.SkipListener;
import org.springframework.stereotype.Component;

import com.duoc.banco.model.MovimientoCuenta;

@Component
public class MovimientoCuentaSkipListener implements SkipListener<MovimientoCuenta, MovimientoCuenta>{

    @Override
    public void onSkipInRead(Throwable t){
        System.out.println("Error al leer movimiento de cuenta: " + t.getMessage());
    }

    @Override
    public void onSkipInProcess(MovimientoCuenta mov, Throwable t){
        System.out.println("Error al procesar movimiento de cuenta: " + mov + ". Causa del error: " + t.getMessage());
    }

    @Override
    public void onSkipInWrite(MovimientoCuenta mov, Throwable t){
        System.out.println("Error al escribir movimiento de cuenta: " + mov + ". Causa del error: " + t.getMessage());
    }

}
