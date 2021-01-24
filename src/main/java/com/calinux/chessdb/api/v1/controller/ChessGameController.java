package com.calinux.chessdb.api.v1.controller;

import com.calinux.chessdb.api.v1.UrlHelper;
import com.calinux.chessdb.api.v1.dto.ImportGameResponseDTO;
import com.calinux.chessdb.api.v1.facade.ChessGameFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(UrlHelper.V1 + UrlHelper.GAMES)
@RequiredArgsConstructor
public class ChessGameController {

    private final ChessGameFacade chessGameFacade;

    @PostMapping("/upload")
    public ImportGameResponseDTO uploadPGNDB(@RequestPart MultipartFile file) {
        return chessGameFacade.importGames(file);
    }

    @GetMapping("/upload/{jobID}/status")
    public ImportGameResponseDTO getStatus(@PathVariable(value = "jobID") Long jobID) {
        return chessGameFacade.getImportStatus(jobID);
    }

}
