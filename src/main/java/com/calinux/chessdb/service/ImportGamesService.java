package com.calinux.chessdb.service;

import com.calinux.chessdb.api.v1.dto.ImportGameResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ImportGamesService {
    ImportGameResponseDTO importGames(MultipartFile pgnFile);

    ImportGameResponseDTO getImportStatus(Long jobId);
}
