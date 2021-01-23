package com.calinux.chessdb.api.v1.controller;

import com.calinux.chessdb.api.v1.dto.ImportGameResponseDTO;
import com.calinux.chessdb.service.ImportGamesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/games")
public class GameController {

    private ImportGamesServiceImpl importGamesService;

    @Autowired
    public GameController(ImportGamesServiceImpl importGamesService) {
        this.importGamesService = importGamesService;
    }

    @PostMapping("/upload")
    public ImportGameResponseDTO uploadPGNDB(@RequestPart MultipartFile file) {
        return importGamesService.importGames(file);
    }

    @GetMapping("/upload/{jobID}/status")
    public ImportGameResponseDTO getStatus(@PathVariable(value = "jobID") Long jobID) {
        return importGamesService.getImportStatus(jobID);
    }

}
