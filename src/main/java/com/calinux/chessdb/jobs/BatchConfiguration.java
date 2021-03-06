package com.calinux.chessdb.jobs;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableScheduling
@EnableBatchProcessing
public class BatchConfiguration {

    private JobRepository jobRepository;
    private Integer maxPoolSize;
    private Integer corePoolSize;
    private Integer queueCapacity;

    @Autowired
    public BatchConfiguration(
            JobRepository jobRepository,
            @Value("${job.max-pool-size}") Integer maxPoolSize,
            @Value("${job.core-pool-size}") Integer corePoolSize,
            @Value("${job.queue-capacity}") Integer queueCapacity
    ) {
        this.jobRepository = jobRepository;
        this.maxPoolSize = maxPoolSize;
        this.corePoolSize = corePoolSize;
        this.queueCapacity = queueCapacity;
    }

    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);

        return executor;
    }

    @Bean
    public TaskExecutor syncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(1);
        executor.setCorePoolSize(1);
        executor.setQueueCapacity(0);

        return executor;
    }

    @Bean
    public JobLauncher asyncJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();

        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(threadPoolTaskExecutor());
        return jobLauncher;
    }
}
