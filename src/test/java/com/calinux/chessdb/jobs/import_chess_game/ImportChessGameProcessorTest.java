package com.calinux.chessdb.jobs.import_chess_game;

import com.calinux.chessdb.entity.ChessGame;
import com.calinux.chessdb.entity.PgnGame;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ImportChessGameProcessorTest {

    private static String validPGN = "[Event \"Earl tourn\"]\n" +
            "[Site \"?\"]\n" +
            "[Date \"1906.??.??\"]\n" +
            "[Round \"?\"]\n" +
            "[White \"Savrov\"]\n" +
            "[Black \"Alekhine, Alexander\"]\n" +
            "[Result \"0-1\"]\n" +
            "[WhiteElo \"\"]\n" +
            "[BlackElo \"\"]\n" +
            "[ECO \"C30\"]\n" +
            "\n" +
            "1.e4 e5 2.f4 Bc5 3.Nf3 d6 4.c3 Bg4 5.Be2 Bxf3 6.Bxf3 Nc6 7.b4 Bb6 8.b5 Nce7\n" +
            "9.d4 exd4 10.cxd4 Nf6 11.O-O O-O 12.Bb2 d5 13.e5 Nd7 14.Nc3 c6 15.Qd3 Ng6\n" +
            "16.g3 f5 17.Kh1 Qe7 18.Nxd5 cxd5 19.Bxd5+ Kh8 20.Ba3 Qd8 21.Bxb7 Rb8 22.Bxf8 Ndxf8\n" +
            "23.Bc6 Qxd4 24.Qxf5 Ne7 25.Qc2 Rc8 26.Rad1 Qb4 27.Rb1 Qa5 28.Rf3 Nxc6 29.Rc3 Rd8\n" +
            "30.bxc6 Qd5+ 31.Qg2 Qd1+ 32.Qf1 Qd5+ 33.Qf3 Qxa2 34.Rd1 Rxd1+ 35.Qxd1 Ne6\n" +
            "36.Qf3 Qb1+ 37.Kg2 g6 38.Qd3 Qg1+ 39.Kh3 h5 40.Qc4 Qd1 41.Rc1 Qg4+ 42.Kg2 h4\n" +
            "43.Qf1 g5 44.Qd1 Nxf4+ 45.Kh1  0-1\n";

    private static String invalidPGN = "[Event \"Earl tourn\"]\n" +
            "[Site \"?\"]\n" +
            "[Date \"1906.??.??\"]\n" +
            "[Round \"?\"]\n" +
            "[White \"Savrov\"]\n" +
            "[Black \"Alekhine, Alexander\"]\n" +
            "[Result \"0-1\"]\n" +
            "[WhiteElo \"\"]\n" +
            "[BlackElo \"\"]\n" +
            "[ECO \"C30\"]\n" +
            "\n" +
            "1.e4 e5 2.f4 Bc5 3.Nf3 d6 4.c3 Bg4 5.Be2 Bxf3 6.Bxf3 Nc6 7.b4 Bb6 8.b5 Nce7\n" +
            "9.d4 exd4 10.cxd4 Nf6 11.O-O O-O 12.Bb2 d5 13.e5 Nd7 14.Nc3 c6 15.Qd3 Ng6\n" +
            "16.g3 f5 17.Kh1 Qe7 18.Nxd5 cxd5 19.Bxd5+ Kh8 20.Ba3 Qd8 21.Bxb7 Rb8 22.Bxf8 Ndxf8\n" +
            "23.Bc6 Qxd4 24.Qxf5 Ne7 25.Qc2 Rc8 26.Rad1 Qb4 27.Rb1 Qa5 28.Rf3 Nxc6 29.Rc3 Rd8\n" +
            "30.bxc6 Qd5+ 31.Qg2 Qd1+ 32.Qf1 Qd5+ 33.Qf3 Qxa2 34.Rd1 Rxd1+ 35.Qxd1 Ne6\n" +
            "36.Qf3 Qb1+ 37.Kg2 g6 38.Qd3 Qg1+ 39.Kh3 h5 40.Qc4 Qd1 41.Rc1 Qg4+ 42.Kg2 h4\n" +
            "43.Qf1 g5 44.Qd1 Nxf4+ 45.Kh1  0-1\n" +
            "\n" +
            "ite \"?\"]\n" +
            "[Date \"1906.??.??\"]\n" +
            "[Round \"?\"]\n" +
            "[White \"Savrov\"]\n" +
            "[Black \"Alekhine, Alexander\"]\n" +
            "[Result \"0-1\"]\n" +
            "[WhiteElo \"\"]\n" +
            "[BlackElo \"\"]";


    private static ImportChessGameProcessor importChessGameProcessor;
    private static PgnGame validGame;
    private static PgnGame invalidGame;

    @BeforeAll
    private static void setUp() {
        importChessGameProcessor = new ImportChessGameProcessor();
        validGame = new PgnGame();
        validGame.setLastName("savrov alekhine");
        validGame.setMoves("e4e5f4Bc5Nf3d6c3Bg4Be2Bxf3Bxf3Nc6b4Bb6b5Nce7d4exd4cxd4Nf6O-OO-OBb2d5e5Nd7Nc3c6Qd3Ng6g3f5Kh1Qe7Nxd5cxd5Bxd5+Kh8Ba3Qd8Bxb7Rb8Bxf8Ndxf8Bc6Qxd4Qxf5Ne7Qc2Rc8Rad1Qb4Rb1Qa5Rf3Nxc6Rc3Rd8bxc6Qd5+Qg2Qd1+Qf1Qd5+Qf3Qxa2Rd1Rxd1+Qxd1Ne6Qf3Qb1+Kg2g6Qd3Qg1+Kh3h5Qc4Qd1Rc1Qg4+Kg2h4Qf1g5Qd1Nxf4+Kh1");
        validGame.setPgnText(validPGN);
        validGame.setProcessed(false);


        invalidGame = new PgnGame();
        invalidGame.setLastName("savrov alekhine");
        invalidGame.setMoves("e4e5f4Bc5Nf3d6c3Bg4Be2Bxf3Bxf3Nc6b4Bb6b5Nce7d4exd4cxd4Nf6O-OO-OBb2d5e5Nd7Nc3c6Qd3Ng6g3f5Kh1Qe7Nxd5cxd5Bxd5+Kh8Ba3Qd8Bxb7Rb8Bxf8Ndxf8Bc6Qxd4Qxf5Ne7Qc2Rc8Rad1Qb4Rb1Qa5Rf3Nxc6Rc3Rd8bxc6Qd5+Qg2Qd1+Qf1Qd5+Qf3Qxa2Rd1Rxd1+Qxd1Ne6Qf3Qb1+Kg2g6Qd3Qg1+Kh3h5Qc4Qd1Rc1Qg4+Kg2h4Qf1g5Qd1Nxf4+Kh1");
        invalidGame.setPgnText(invalidPGN);
        invalidGame.setProcessed(false);
    }

    @Test
    public void GIVEN_a_valid_pgn_game_WHEN_processing_the_game_THEN_a_valid_chess_game_is_returned() {
        ChessGame chessGame = importChessGameProcessor.process(validGame);
        // TODO: check every property of object and nested objects
        assertThat(chessGame).isNotNull();
    }

    @Test
    public void GIVEN_an_invalid_pgn_game_WHEN_processing_the_game_THEN_something_happens() {
        ChessGame chessGame = importChessGameProcessor.process(invalidGame);
        assertThat(chessGame).isNull();
    }
}