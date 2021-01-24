package com.calinux.chessdb.service;

import com.calinux.chessdb.api.v1.dto.ImportGameResponseDTO;
import org.springframework.batch.core.JobExecution;
import org.springframework.web.multipart.MultipartFile;

public interface ImportGamesService {
    JobExecution importGames(MultipartFile pgnFile);

    JobExecution getImportStatus(Long jobId);
}
