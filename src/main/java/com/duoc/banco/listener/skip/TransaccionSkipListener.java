package com.duoc.banco.listener.skip;

import org.springframework.batch.core.listener.SkipListener;
import org.springframework.stereotype.Component;

import com.duoc.banco.model.Transaccion;

@Component
public class TransaccionSkipListener implements SkipListener<Transaccion, Transaccion>{

    @Override
    public void onSkipInRead(Throwable t){
        System.out.println("Error al leer la transaccion: " + t.getMessage());
    }

    @Override
    public void onSkipInProcess(Transaccion transaccion, Throwable t){
        System.out.println("Error al procesar la transaccion: " + transaccion + ". Causa del error: " + t.getMessage());
    }

    @Override
    public void onSkipInWrite(Transaccion transaccion, Throwable t){
        System.out.println("Error al escribir la transaccion: " + transaccion + ". Causa del error: " + t.getMessage());
    }

}
