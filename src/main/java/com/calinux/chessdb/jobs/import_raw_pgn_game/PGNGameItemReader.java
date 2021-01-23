package com.calinux.chessdb.jobs.import_raw_pgn_game;

import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@StepScope
@Slf4j
public class PGNGameItemReader implements ItemReader<Game> {

    private String filename;
    private ItemReader<Game> delegate;

    @BeforeStep
    public void beforeStep(final StepExecution stepExecution) {
        JobParameters jobParameters = stepExecution.getJobParameters();
        log.info(String.format("Importing PGN file %s", filename));
        filename = jobParameters.getString("filename");
    }

    @AfterStep
    public void afterStep(final StepExecution stepExecution) throws Exception {
        JobParameters jobParameters = stepExecution.getJobParameters();
        filename = jobParameters.getString("filename");
        log.info(String.format("Deleting temporary PGN game file %s", filename));
        Files.deleteIfExists(Paths.get(filename));
    }

    @Override
    public Game read() throws Exception {
        if (delegate == null) {
            delegate = new IteratorItemReader<>(games());
        }
        return delegate.read();
    }

    private List<Game> games() throws Exception {
        PgnHolder pgn = new PgnHolder(filename);
        pgn.loadPgn();
        if (pgn.getGames().size() == 0) {
            log.warn(String.format("No games found in file %s", filename));
        }
        return pgn.getGames();
    }

}
