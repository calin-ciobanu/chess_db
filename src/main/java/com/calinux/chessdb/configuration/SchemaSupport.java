package com.calinux.chessdb.configuration;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class SchemaSupport {

    @Bean
    public static BeanFactoryPostProcessor schemaFilesCleanupPostProcessor() {
        return bf -> {
            try {
                Files.deleteIfExists(Path.of("sql/drop_schema.sql"));
                Files.deleteIfExists(Path.of("sql/create_schema.sql"));
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        };
    }
}
