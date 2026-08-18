package com.duoc.banco.listener;

import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // Lógica antes de la ejecución del job
        System.out.println("Iniciando el job: " + jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // Lógica después de la ejecución del job
        System.out.println("Fin del job: " + jobExecution.getJobInstance().getJobName());
    }

}
