package com.duoc.banco.config.shutdown;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

@Component
public class InteresExecutorShutdown {

    private final ThreadPoolTaskExecutor taskExecutor;

    public InteresExecutorShutdown(@Qualifier("transaccionTaskExecutor") ThreadPoolTaskExecutor taskExecutor){
        this.taskExecutor = taskExecutor;
    }

    @PreDestroy
    public void shutdown(){
        taskExecutor.shutdown();
    }

}
