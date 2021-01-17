package com.calinux.chessdb.jobs.import_raw_pgn_game;

import com.calinux.chessdb.entity.PgnGame;
import com.github.bhlangonijr.chesslib.game.Game;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class PGNGameBatchConfiguration {


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PGNGameItemReader PGNGameItemReader;

    @Autowired
    private PGNGameItemProcessor PGNGameItemProcessor;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public JpaItemWriter chessGameItemWriter() {
        JpaItemWriter writer = new JpaItemWriter();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public Step importPgnGameStep(JpaItemWriter chessGameItemWriter) {
        return stepBuilderFactory.get("pgn_import_1")
                .<Game, PgnGame>chunk(10)
                .reader(PGNGameItemReader)
                .processor(PGNGameItemProcessor)
                .writer(chessGameItemWriter)
                .build();
    }

    @Bean
    public Job importCustomersJob(Step importPgnGameStep) {
        return jobBuilderFactory.get("job_pgn_import")
                .flow(importPgnGameStep)
                .end()
                .build();
    }

}
