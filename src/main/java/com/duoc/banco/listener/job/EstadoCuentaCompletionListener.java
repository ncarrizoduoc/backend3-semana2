package com.duoc.banco.listener.job;

import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class EstadoCuentaCompletionListener implements JobExecutionListener{

    private final ThreadPoolTaskExecutor taskExecutor;

    public EstadoCuentaCompletionListener(
        @Qualifier("movimientoCuentaTaskExecutor") ThreadPoolTaskExecutor taskExecutor
    ){
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // Lógica antes de la ejecución del Job
        System.out.println("Iniciando el job: " + jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // Lógica después de la ejecución del Job
        taskExecutor.shutdown();
        System.out.println("Fin del job: " + jobExecution.getJobInstance().getJobName());
    }

}
