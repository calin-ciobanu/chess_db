package com.calinux.chessdb.jobs.import_chess_game;

import com.calinux.chessdb.entity.ChessGame;
import com.calinux.chessdb.entity.PgnGame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ImportChessGameBatchConfiguration {

    private static final String QUERY_FIND_NEW_GAMES =
            "SELECT * FROM pgn_game WHERE processed=FALSE ORDER BY id ASC;";

    private final JobLauncher jobLauncher;
    private final ApplicationContext context;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ImportChessGameProcessor importChessGameProcessor;
    private final ImportChessGameWriter importChessGameWriter;

    @Bean
    public ItemReader<PgnGame> pgnGameCursorItemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<PgnGame>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql(QUERY_FIND_NEW_GAMES)
                .rowMapper(new BeanPropertyRowMapper<>(PgnGame.class))
                .build();
    }

    @Bean
    public Step pgnGameCursorStep(ItemReader<PgnGame> pgnGameCursorItemReader) {
        return stepBuilderFactory.get("chess_game_import_1")
                .<PgnGame, ChessGame>chunk(10)
                .reader(pgnGameCursorItemReader)
                .processor(importChessGameProcessor)
                .writer(importChessGameWriter)
                .build();
    }

    @Bean
    public Job pgnGameCursorJob(Step pgnGameCursorStep) {
        return jobBuilderFactory.get("job_chess_game_import")
                .incrementer(new RunIdIncrementer())
                .flow(pgnGameCursorStep)
                .end()
                .build();
    }

    @Scheduled(fixedDelay = 10000, initialDelay = 2000)
    public void run() {
        try {
            Job job = context.getBean("pgnGameCursorJob", Job.class);
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.nanoTime())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

}
