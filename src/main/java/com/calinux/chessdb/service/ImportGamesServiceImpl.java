package com.calinux.chessdb.service;

import com.calinux.chessdb.api.v1.dto.ImportGameResponseDTO;
import com.calinux.chessdb.api.v1.exception.GameImportException;
import com.calinux.chessdb.api.v1.exception.JobNotFoundException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@Slf4j
@Setter
public class ImportGamesServiceImpl implements ImportGamesService {

    private String tempDir;
    private JobExplorer jobExplorer;
    private JobLauncher asyncJobLauncher;
    private Job importCustomersJob;

    @Autowired
    public ImportGamesServiceImpl(@Value("${user.home}") String tempDir, JobExplorer jobExplorer, JobLauncher asyncJobLauncher, Job importCustomersJob) {
        this.tempDir = tempDir;
        this.jobExplorer = jobExplorer;
        this.asyncJobLauncher = asyncJobLauncher;
        this.importCustomersJob = importCustomersJob;
    }

    @Override
    public JobExecution importGames(MultipartFile pgnFile) {

        // Save PGN file to temp location from where it is processed
        File tempFile = new File(tempDir, String.valueOf(System.currentTimeMillis()) + pgnFile.getOriginalFilename());
        try {
            pgnFile.transferTo(tempFile);
        } catch (Exception e) {
            throw new GameImportException("Could not write file to disk", e);
        }

        // Launch import job that will store the raw data in a processing table
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("filename", tempFile.getAbsolutePath())
                    .toJobParameters();

            JobExecution jobExecution = asyncJobLauncher.run(importCustomersJob, jobParameters);

            return jobExecution;
        } catch (Exception e) {
            throw new GameImportException("Import job failed to start", e);
        }
    }

    @Override
    public JobExecution getImportStatus(Long jobId) {
        JobExecution jobExecution = jobExplorer.getJobExecution(jobId);

        if (jobExecution == null) {
            throw new JobNotFoundException(jobId);
        }
        return jobExecution;
    }
}
