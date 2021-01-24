package com.calinux.chessdb.api.v1.facade;

import com.calinux.chessdb.api.v1.dto.ImportGameResponseDTO;
import com.calinux.chessdb.api.v1.dto.mapper.ImportGamesResponseDTOMapper;
import com.calinux.chessdb.service.ImportGamesService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ChessGameFacade {

    private final ImportGamesService importGamesService;

    public ImportGameResponseDTO importGames(MultipartFile pgnFile) {
        JobExecution jobExecution = importGamesService.importGames(pgnFile);

        return ImportGamesResponseDTOMapper.INSTANCE.fromJobExecution(jobExecution);
    }

    public ImportGameResponseDTO getImportStatus(Long jobId) {
        JobExecution jobExecution = importGamesService.getImportStatus(jobId);

        return ImportGamesResponseDTOMapper.INSTANCE.fromJobExecution(jobExecution);
    }
}
