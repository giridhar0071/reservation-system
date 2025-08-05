package com.giridhar.reservation.config;

import com.giridhar.reservation.service.AppointmentService;

import com.giridhar.reservation.service.WaitingListAllocatorService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;

import org.springframework.batch.core.launch.support.RunIdIncrementer;

import org.springframework.batch.core.repository.JobRepository;

import org.springframework.batch.repeat.RepeatStatus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {
    private final WaitingListAllocatorService waitAllocSvc;

    private final AppointmentService appointmentService;


    public BatchConfig(AppointmentService appointmentService, WaitingListAllocatorService waitAllocSvc) {
        this.appointmentService = appointmentService;
        this.waitAllocSvc = waitAllocSvc;
    }

    @Bean
    public Job appointmentBatchJob(JobRepository jobRepository, Step appointmentStep) {
        return new JobBuilder("appointmentBatchJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(appointmentStep)
                .build();
    }

    @Bean
    public Step appointmentStep(JobRepository jobRepository,
                                PlatformTransactionManager txMgr) {
        return new StepBuilder("appointmentStep", jobRepository)
                .tasklet((contribution, context) -> {
                    appointmentService.runRoundRobinBooking();
                    return RepeatStatus.FINISHED;
                }, txMgr)
                .build();
    }
}
