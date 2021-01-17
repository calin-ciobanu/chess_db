package com.calinux.chessdb.jobs.import_raw_pgn_game;

import com.calinux.chessdb.entity.PgnGame;
import com.github.bhlangonijr.chesslib.game.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
public class PGNGameItemProcessor implements ItemProcessor<Game, PgnGame> {

    @Override
    public PgnGame process(Game game) {
        PgnGame pgnGame = new PgnGame();

        log.trace(String.format("Importing PGN game %s", pgnGame.toString()));

        // Parse games from PGN file and dump PGN representation in processing table
        pgnGame.setPgnText(game.toPgn(false, false));

        // Set player names and compact moves string to identify duplicates (uniqueness NOT IMPLEMENTED)
        pgnGame.setLastName(
                game.getWhitePlayer().getName().split(",")[0].toLowerCase().trim() +
                        " " +
                        game.getBlackPlayer().getName().split(",")[0].toLowerCase().trim()
        );

        List<String> movesSan = game.getHalfMoves().stream()
                .map(s -> s.getSan())
                .collect(Collectors.toList());

        pgnGame.setMoves(String.join("", movesSan));

        // Mark the game for processing to be imported in full db structure
        pgnGame.setProcessed(false);

        return pgnGame;
    }
}
