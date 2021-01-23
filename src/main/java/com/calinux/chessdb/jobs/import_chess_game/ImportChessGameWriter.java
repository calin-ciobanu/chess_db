package com.calinux.chessdb.jobs.import_chess_game;

import com.calinux.chessdb.entity.ChessGame;
import com.calinux.chessdb.entity.ChessTimeControl;
import com.calinux.chessdb.entity.PgnGame;
import com.calinux.chessdb.entity.User;
import com.calinux.chessdb.repository.ChessGameRepository;
import com.calinux.chessdb.repository.ChessTimeControlRepository;
import com.calinux.chessdb.repository.PgnGameRepository;
import com.calinux.chessdb.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Component
@Slf4j
public class ImportChessGameWriter extends JpaItemWriter<ChessGame> {

    private ChessGameRepository chessGameRepository;
    private UserRepository userRepository;
    private PgnGameRepository pgnGameRepository;
    private ChessTimeControlRepository chessTimeControlRepository;

    @Autowired
    public ImportChessGameWriter(
            EntityManagerFactory entityManagerFactory,
            ChessGameRepository chessGameRepository,
            UserRepository userRepository,
            PgnGameRepository pgnGameRepository,
            ChessTimeControlRepository chessTimeControlRepository
    ) {
        super();
        setEntityManagerFactory(entityManagerFactory);
        this.chessGameRepository = chessGameRepository;
        this.userRepository = userRepository;
        this.pgnGameRepository = pgnGameRepository;
        this.chessTimeControlRepository = chessTimeControlRepository;
    }

    @Override
    public void write(List<? extends ChessGame> items) {
        for (ChessGame chessGame : items) {
            // Check if game was already indexed
            if (chessGameRepository.findByPGNGame(chessGame.getPgnGame()) == null) {
                chessGame.setChessTimeControl(getTimeControl(chessGame.getChessTimeControl()));
                chessGame.setBlackPlayer(getUser(chessGame.getBlackPlayer()));
                chessGame.setWhitePlayer(getUser(chessGame.getWhitePlayer()));

                userRepository.save(chessGame.getBlackPlayer());
                userRepository.save(chessGame.getWhitePlayer());

                chessGameRepository.save(chessGame);
            } else {
                log.warn(String.format("PGN Game with id %d already indexed", chessGame.getPgnGame().getId()));
            }
            PgnGame pgnGame = chessGame.getPgnGame();
            pgnGame.setProcessed(Boolean.TRUE);
            pgnGameRepository.save(pgnGame);
        }
    }

    private ChessTimeControl getTimeControl(ChessTimeControl timeControl) {
        ChessTimeControl ctc = chessTimeControlRepository.findByGameTypeAndGameTimeInSecondsAndIncrementPerMoveInSeconds(
                timeControl.getGameType(),
                timeControl.getGameTimeInSeconds(),
                timeControl.getIncrementPerMoveInSeconds()
        );
        if (ctc != null) {
            return ctc;
        }
        return timeControl;
    }

    private User getUser(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser != null) {
            return foundUser;
        }
        return user;
    }

}
