package com.duoc.banco.config.shutdown;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

@Component
public class MovimientoCuentaExecutorShutdown {
    
    private final ThreadPoolTaskExecutor taskExecutor;

    public MovimientoCuentaExecutorShutdown(@Qualifier("movimientoCuentaTaskExecutor") ThreadPoolTaskExecutor taskExecutor){
        this.taskExecutor = taskExecutor;
    }

    @PreDestroy
    public void shutdown(){
        taskExecutor.shutdown();
    }

}
