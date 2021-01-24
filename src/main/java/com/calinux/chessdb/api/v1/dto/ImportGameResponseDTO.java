package com.calinux.chessdb.api.v1.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.core.BatchStatus;

import java.util.Date;

@Getter
@Setter
public class ImportGameResponseDTO {

    private Long jobId;
    private BatchStatus status;
    private Date createdAt;
    private Date startedAt;
    private Date endedAt;

    public ImportGameResponseDTO(Long jobId, BatchStatus status, Date createdAt, Date startedAt, Date endedAt) {
        this.jobId = jobId;
        this.status = status;
        this.createdAt = createdAt;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }
}
