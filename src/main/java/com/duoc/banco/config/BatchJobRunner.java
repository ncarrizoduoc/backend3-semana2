package com.duoc.banco.config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BatchJobRunner implements CommandLineRunner {

    private final JobOperator jobLauncher;
    private final Job transaccionJob;
    private final Job interesJob;
    private final Job generarEstadosDeCuentaJob;

    public BatchJobRunner(JobOperator jobLauncher, Job transaccionJob, Job interesJob, Job generarEstadosDeCuentaJob) {
        this.jobLauncher = jobLauncher;
        this.transaccionJob = transaccionJob;
        this.interesJob = interesJob;
        this.generarEstadosDeCuentaJob = generarEstadosDeCuentaJob;
    }

    @Override
    public void run(String... args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(transaccionJob, jobParameters);
        jobLauncher.run(interesJob, jobParameters);
        jobLauncher.run(generarEstadosDeCuentaJob, jobParameters);
    }

}
