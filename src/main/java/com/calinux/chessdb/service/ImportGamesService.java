package com.calinux.chessdb.service;

import com.calinux.chessdb.api.v1.dto.ImportGameResponseDTO;
import com.calinux.chessdb.api.v1.exception.GameImportException;
import com.calinux.chessdb.api.v1.exception.JobNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@Slf4j
public class ImportGamesService {

    @Value("${user.home}")
    private String tempDir;

    @Autowired
    ApplicationContext context;

    @Autowired
    JobExplorer jobExplorer;

    public ImportGameResponseDTO importGames(MultipartFile pgnFile) {

        // Save PGN file to temp location from where it is processed
        File tempFile = new File(tempDir, String.valueOf(System.currentTimeMillis()) + pgnFile.getOriginalFilename());
        try {
            pgnFile.transferTo(tempFile);
        } catch (Exception e) {
            throw new GameImportException("Could not write file to disk", e);
        }

        // Launch import job that will store the raw data in a processing table
        try {
            JobLauncher jobLauncher = context.getBean("asyncJobLauncher", JobLauncher.class);
            Job job = context.getBean("importCustomersJob", Job.class);
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("filename", tempFile.getAbsolutePath())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            JobExecution je = jobExplorer.getJobExecution(jobExecution.getId());

            return new ImportGameResponseDTO(
                    jobExecution.getId(),
                    jobExecution.getStatus(),
                    jobExecution.getCreateTime(),
                    jobExecution.getEndTime()
            );
        } catch (Exception e) {
            throw new GameImportException("Import job failed to start", e);
        }
    }

    public ImportGameResponseDTO getImportStatus(Long jobId) {
        JobExecution jobExecution = jobExplorer.getJobExecution(jobId);
        try {
            return new ImportGameResponseDTO(
                    jobExecution.getId(),
                    jobExecution.getStatus(),
                    jobExecution.getCreateTime(),
                    jobExecution.getEndTime()
            );
        } catch (NullPointerException e) {
            throw new JobNotFoundException(jobId, e);
        }
    }
}
