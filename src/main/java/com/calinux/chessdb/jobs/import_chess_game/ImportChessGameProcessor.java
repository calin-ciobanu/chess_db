package com.calinux.chessdb.jobs.import_chess_game;

import com.calinux.chessdb.entity.*;
import com.calinux.chessdb.entity.enums.GameResultType;
import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.game.GameResult;
import com.github.bhlangonijr.chesslib.game.Player;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;
import com.github.bhlangonijr.chesslib.util.LargeFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class ImportChessGameProcessor implements ItemProcessor<PgnGame, ChessGame> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    @Override
    public ChessGame process(PgnGame pgnGame) {
        log.info("processing: " + pgnGame.getId());

        Game game = loadGame(pgnGame);

        ChessGame chessGame = mapGenericFields(new ChessGame(), game);

        chessGame.setWhitePlayer(mapPlayer(game.getWhitePlayer()));
        chessGame.setBlackPlayer(mapPlayer(game.getBlackPlayer()));

        chessGame.setChessMoves(mapMoves(game));
        chessGame.setChessGameResult(mapResult(game.getResult()));
        chessGame.setChessTimeControl(mapTimeControl(game));

        chessGame.setPgnGame(pgnGame);

        return chessGame;
    }

    private Game loadGame(PgnGame pgnGame) {
        PgnHolder pgn = new PgnHolder(null);
        try {
            LargeFile largeFile= new LargeFile(new ByteArrayInputStream(pgnGame.getPgnText().getBytes()));
            pgn.loadPgn(largeFile);
        } catch (Exception e) {
            return null;
        }

        return pgn.getGames().get(0);
    }

    private User mapPlayer(Player player) {
        User user = new User();

        String[] name = player.getName().split(",");

        if (name.length > 1) {
            user.setFirstName(name[1].strip());
        } else {
            user.setFirstName("Unknown");
        }
        user.setLastName(name[0].strip());
        user.setUsername(String.format(
                "%s_%s",
                user.getFirstName().toLowerCase(),
                user.getLastName().toLowerCase()
        ));
        user.setEmail(String.format("%s@imported.com", user.getUsername()));

        UserDetail userDetail = new UserDetail();
        userDetail.setAbout("");
        userDetail.setRating(player.getElo());
        user.setUserDetail(userDetail);

        return user;
    }

    private List<ChessMove> mapMoves(Game game) {
        List<ChessMove> moves = new ArrayList<>();
        int i = 1;
        for (Move move : game.getHalfMoves()) {
            ChessMove chessMove = new ChessMove();
            chessMove.setDescription(move.getSan());
            chessMove.setIndexInGame(i / 2);
            moves.add(chessMove);
            i++;
        }
        return moves;
    }

    private ChessGameResult mapResult(GameResult result) {
        ChessGameResult chessGameResult = new ChessGameResult();
        switch (result) {
            case BLACK_WON:
                chessGameResult.setResultType(GameResultType.BLACK_WON);
                break;
            case WHITE_WON:
                chessGameResult.setResultType(GameResultType.WHITE_WON);
                break;
            case DRAW:
                chessGameResult.setResultType(GameResultType.DRAW);
                break;
            case ONGOING:
                chessGameResult.setResultType(GameResultType.ONGOING);
                break;
        }
        return chessGameResult;
    }

    private ChessTimeControl mapTimeControl(Game game) {
        ChessTimeControl chessTimeControl = new ChessTimeControl();

        if (game.getRound().getEvent().getTimeControl() != null) {
            chessTimeControl.setGameTimeInSeconds((int) game.getRound().getEvent().getTimeControl().getMilliseconds()/1000);
            chessTimeControl.setIncrementPerMoveInSeconds((int) game.getRound().getEvent().getTimeControl().getIncrement()/1000);
        } else {
            chessTimeControl.setGameTimeInSeconds(-1);
            chessTimeControl.setIncrementPerMoveInSeconds(-1);
        }
        return chessTimeControl;
    }

    private ChessGame mapGenericFields(ChessGame chessGame, Game game) {
        Date gameDate = Date.from(LocalDate.parse(game.getRound().getEvent().getStartDate().replaceAll("\\?\\?", "01"), formatter).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        chessGame.setDate(gameDate);

        chessGame.setNumberOfMoves(game.getHalfMoves().size());
        chessGame.setLocation(game.getRound().getEvent().getSite());

        return chessGame;
    }
}
