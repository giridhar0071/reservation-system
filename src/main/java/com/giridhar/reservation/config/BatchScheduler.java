package com.giridhar.reservation.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling   // turn on processing of @Scheduled
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job appointmentBatchJob;

    @Autowired
    public BatchScheduler(JobLauncher jobLauncher, Job appointmentBatchJob) {
        this.jobLauncher        = jobLauncher;
        this.appointmentBatchJob = appointmentBatchJob;
    }

    /**
     * Runs every Friday at 10 PM
     */
    @Scheduled(cron = "0 0 22 ? * FRI", zone = "Asia/Kolkata")
    public void scheduleFridayBatch() throws Exception {
        jobLauncher.run(
                appointmentBatchJob,
                new JobParametersBuilder()
                        .addLong("ts", System.currentTimeMillis())
                        .toJobParameters()
        );
    }
}
