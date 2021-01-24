package com.calinux.chessdb.service;

import com.calinux.chessdb.api.v1.dto.ImportGameResponseDTO;
import com.calinux.chessdb.api.v1.exception.GameImportException;
import com.calinux.chessdb.api.v1.exception.JobNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ImportGamesServiceTest {

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
            "43.Qf1 g5 44.Qd1 Nxf4+ 45.Kh1  0-1";

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private JobExplorer jobExplorer;

    @Mock
    private JobExecution jobExecution;

    private static String tempDir = "src/test/temp";

    private ImportGamesServiceImpl importGamesService;// = new ImportGamesServiceImpl(tempDir, context, jobExplorer);

    @BeforeEach
    public void setUp() {
        importGamesService = new ImportGamesServiceImpl(tempDir, jobExplorer, jobLauncher, null);
    }

    @AfterAll
    public static void tearDown() throws IOException {
        FileUtils.cleanDirectory(new File(tempDir));
    }

    // importGames
    @Test
    void GIVEN_a_valid_pgn_file_WHEN_file_save_fails_THEN_game_import_exception_is_thrown() throws IOException {
        MultipartFile mockPGN = Mockito.mock(MockMultipartFile.class);
        when(mockPGN.getOriginalFilename()).thenReturn("valid.pgn");
        willThrow(new IOException()).given(mockPGN).transferTo(any(File.class));

        assertThatThrownBy(
                () -> importGamesService.importGames(mockPGN)
        )
                .isInstanceOf(GameImportException.class)
                .hasMessage("Could not write file to disk");
    }

    @Test
    void GIVEN_a_valid_pgn_file_WHEN_job_start_fails_THEN_game_import_exception_is_thrown() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        MockMultipartFile mockPGN = new MockMultipartFile("valid.pgn", validPGN.getBytes(StandardCharsets.UTF_8));

        when(jobLauncher.run(any(),any())).thenThrow(new JobParametersInvalidException("error"));

        assertThatThrownBy(
                () -> importGamesService.importGames(mockPGN)
        )
                .isInstanceOf(GameImportException.class)
                .hasMessage("Import job failed to start");
    }

    @Test
    void GIVEN_a_valid_pgn_file_WHEN_job_start_succeeds_THEN_success_response_is_returned() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        MockMultipartFile mockPGN = new MockMultipartFile("valid.pgn", validPGN.getBytes(StandardCharsets.UTF_8));

        when(jobLauncher.run(any(), any())).thenReturn(jobExecution);

        JobExecution jobExecution = importGamesService.importGames(mockPGN);

        assertThat(jobExecution).isNotNull();
    }

    // getImportStatus
    @Test
    void GIVEN_a_valid_job_id_WHEN_get_import_status_is_called_THEN_information_about_the_job_is_returned() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Long jobId = 1l;

        when(jobExplorer.getJobExecution(jobId)).thenReturn(jobExecution);

        JobExecution jobExecution = importGamesService.getImportStatus(jobId);

        assertThat(jobExecution).isNotNull();
    }

    @Test
    void GIVEN_non_existing_job_id_WHEN_get_import_status_is_called_THEN_job_not_found_exception_is_thrown() {
        assertThatThrownBy(
                () -> importGamesService.getImportStatus(1l)
        )
                .isInstanceOf(JobNotFoundException.class)
                .hasMessage("Could not find job with id 1");
    }

}