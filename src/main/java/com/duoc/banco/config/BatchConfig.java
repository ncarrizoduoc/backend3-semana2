package com.duoc.banco.config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.ChunkOrientedStepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.support.SingleItemPeekableItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.duoc.banco.listener.BancoStepExecutionListener;
import com.duoc.banco.listener.JobCompletionListener;
import com.duoc.banco.model.EstadoCuenta;
import com.duoc.banco.model.Interes;
import com.duoc.banco.model.MovimientoCuenta;
import com.duoc.banco.model.Transaccion;
import com.duoc.banco.processor.EstadoCuentaItemProcessor;

@Configuration
public class BatchConfig {

    // Paso para procesamiento de transacciones
    @Bean
    public Step transaccionStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        ItemReader<Transaccion> transaccionItemReader,
        ItemProcessor<Transaccion, Transaccion> transaccionItemProcessor,
        ItemWriter<Transaccion> transaccionItemWriter,
        BancoStepExecutionListener stepListener
    ) {
        return new ChunkOrientedStepBuilder<Transaccion, Transaccion>(
            "transaccionStep",
            jobRepository,
            10
        )
        .reader(transaccionItemReader)
        .processor(transaccionItemProcessor)
        .writer(transaccionItemWriter)
        .listener(stepListener)
        .build();
    }

    // Job para procesamiento de transacciones
    @Bean
    public Job transaccionJob(
        JobRepository jobRepository,
        Step transaccionStep,
        JobCompletionListener jobCompletionListener
    ) {
        return new JobBuilder("transaccionJob", jobRepository)
        .start(transaccionStep)
        .listener(jobCompletionListener)
        .build();
    }

    // Step para procesamiento de intereses
    @Bean
    public Step interesStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        ItemReader<Interes> interesItemReader,
        ItemProcessor<Interes, Interes> interesItemProcessor,
        ItemWriter<Interes> interesItemWriter,
        BancoStepExecutionListener stepListener
    ) {
        return new ChunkOrientedStepBuilder<Interes, Interes>(
            "interesStep",
            jobRepository,
            10
        )
        .reader(interesItemReader)
        .processor(interesItemProcessor)
        .writer(interesItemWriter)
        .listener(stepListener)
        .build();
    }

    // Job para procesamiento de intereses
    @Bean
    public Job interesJob(
        JobRepository jobRepository,
        Step interesStep,
        JobCompletionListener jobCompletionListener
    ) {
        return new JobBuilder("interesJob", jobRepository)
        .start(interesStep)
        .listener(jobCompletionListener)
        .build();
    }

    // Paso 1: Leer movimientos de cuentas desde el CSV y guardarlos en 
    // la tabla temporal MOVIMIENTO_CUENTA en base de datos
    @Bean
    public Step leerYGuardarMovimientosDeCuentaStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        FlatFileItemReader<MovimientoCuenta> movimientoCuentaItemReader,
        ItemWriter<MovimientoCuenta> movimientoCuentaItemWriter,
        BancoStepExecutionListener bancoStepListener
    ){
        return new ChunkOrientedStepBuilder<MovimientoCuenta, MovimientoCuenta>(
            "leerYGuardarMovimientosDeCuentaStep",
            jobRepository,
            10)
            .reader(movimientoCuentaItemReader)
            .writer(movimientoCuentaItemWriter)
            .listener(bancoStepListener)
            .build();
    }

    // Paso 2: Leer movimientos de cuentas desde la tabla temporal MOVIMIENTO_CUENTA,
    // ordenarlos por cuenta_id y procesarlos para generar el estado de cuenta
    @Bean
    public Step generarEstadosDeCuentaStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        SingleItemPeekableItemReader<MovimientoCuenta> movimientoCuentaPeekableReader,
        EstadoCuentaItemProcessor estadoCuentaItemProcessor,
        ItemWriter<EstadoCuenta> estadoCuentaItemWriter,
        BancoStepExecutionListener bancoStepListener
    ){
        ItemReader<MovimientoCuenta> reader = () -> {
            MovimientoCuenta movActual = movimientoCuentaPeekableReader.read();
            MovimientoCuenta movSiguiente = movimientoCuentaPeekableReader.peek();

            if (movActual != null){
                boolean esUltimo = (movSiguiente == null || !movSiguiente.getCuentaId().equals(movActual.getCuentaId()));
                movActual.setUltimoDelGrupo(esUltimo);
            }
            return movActual;
        };

        return new ChunkOrientedStepBuilder<MovimientoCuenta, EstadoCuenta>(
            "generarEstadosDeCuentaStep",
            jobRepository,
            10)
            .reader(reader)
            .stream(movimientoCuentaPeekableReader)
            .processor(estadoCuentaItemProcessor)
            .writer(estadoCuentaItemWriter)
            .listener(bancoStepListener)
            .build();
    }

    @Bean
    public Job generarEstadosDeCuentaJob(
        JobRepository jobRepository,
        Step leerYGuardarMovimientosDeCuentaStep,
        Step generarEstadosDeCuentaStep,
        JobCompletionListener jobCompletionListener
    ){
        return new org.springframework.batch.core.job.builder.JobBuilder(
            "generarEstadosDeCuentaJob",
            jobRepository
        )
        .start(leerYGuardarMovimientosDeCuentaStep)
        .next(generarEstadosDeCuentaStep)
        .listener(jobCompletionListener)
        .build();
    }

}
