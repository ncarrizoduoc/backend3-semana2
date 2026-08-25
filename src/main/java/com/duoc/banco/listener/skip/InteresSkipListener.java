package com.duoc.banco.listener.skip;

import org.springframework.batch.core.listener.SkipListener;
import org.springframework.stereotype.Component;

import com.duoc.banco.model.Interes;

@Component
public class InteresSkipListener implements SkipListener<Interes, Interes>{

    @Override
    public void onSkipInRead(Throwable t){
        System.out.println("Error al leer registro: " + t.getMessage());
    }

    @Override
    public void onSkipInProcess(Interes interes, Throwable t){
        System.out.println("Error al procesar interes: " + interes + ". Causa del error: " + t.getMessage());
    }

    @Override
    public void onSkipInWrite(Interes interes, Throwable t){
        System.out.println("Error al escribir interes: " + interes + ". Causa del error: " + t.getMessage());
    }

}
