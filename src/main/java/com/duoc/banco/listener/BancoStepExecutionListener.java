package com.duoc.banco.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.stereotype.Component;

@Component
public class BancoStepExecutionListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        // Lógica antes de la ejecución del paso
        System.out.println("Iniciando el paso: " + stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        // Lógica después de la ejecución del paso
        System.out.println("Fin del paso: " + stepExecution.getStepName());
        return stepExecution.getExitStatus();
    }

}
