package com.calinux.chessdb.api.v1.dto.mapper;

import com.calinux.chessdb.api.v1.dto.ImportGameResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.batch.core.JobExecution;

@Mapper
public interface ImportGamesResponseDTOMapper {

    ImportGamesResponseDTOMapper INSTANCE = Mappers.getMapper(ImportGamesResponseDTOMapper.class);

    @Mapping(source = "id", target = "jobId")
    @Mapping(source = "createTime", target = "createdAt")
    @Mapping(source = "startTime", target = "startedAt")
    @Mapping(source = "endTime", target = "endedAt")
    ImportGameResponseDTO fromJobExecution(JobExecution jobExecution);
}
