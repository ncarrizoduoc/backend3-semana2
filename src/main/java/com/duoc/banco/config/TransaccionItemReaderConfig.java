package com.duoc.banco.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.infrastructure.item.file.mapping.FieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.duoc.banco.model.Transaccion;

@Configuration
public class TransaccionItemReaderConfig {

    @Bean
    @StepScope
    public FlatFileItemReader<Transaccion> transaccionItemReader(
        @Value("${app.input-transacciones}") Resource inputFile
    ) {
        return new FlatFileItemReaderBuilder<Transaccion>()
            .name("transaccionItemReader")
            .resource(inputFile)
            .encoding("UTF-8")
            .linesToSkip(1)
            .delimited()
            .delimiter(",")
            .names("id", "fecha", "monto", "tipo")
            .fieldSetMapper(transaccionFieldSetMapper())
            .build();
    }

    private FieldSetMapper<Transaccion> transaccionFieldSetMapper() {
        return fieldSet -> {
            Transaccion transaccion = new Transaccion();
            transaccion.setId(fieldSet.readLong("id"));
            transaccion.setFecha(toFecha(fieldSet.readString("fecha")));
            transaccion.setMonto(fieldSet.readInt("monto"));
            transaccion.setTipo(fieldSet.readString("tipo"));
            return transaccion;
        };
    }

    // Convertir String a fecha
    private LocalDate toFecha(String fechaStr) {
        if (fechaStr == null || fechaStr.isEmpty()) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fechaStr, formatter);
    }

}
