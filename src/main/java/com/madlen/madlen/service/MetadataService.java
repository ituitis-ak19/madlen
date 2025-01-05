package com.madlen.madlen.service;

import com.madlen.madlen.model.Metadata;
import com.madlen.madlen.repository.MetadataRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;

@Service
public class MetadataService {

    private final MetadataRepository metadataRepository;
    private static final Logger logger = LoggerFactory.getLogger(MetadataService.class);

    public MetadataService(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    public Metadata getMetadata() {
        logger.info("Fetching metadata with ID 1.");
        return this.metadataRepository.findById(1).orElseGet(() -> {
            logger.warn("No metadata found with ID 1.");
            return null;
        });
    }

    public void updateMetadata(List<Integer> contentPages, boolean isQuestionDeleted) {
        logger.info("Starting update metadata. isQuestionDeleted: {}", isQuestionDeleted);
        Metadata metadata = this.metadataRepository.findById(1).orElse(null);

        if (metadata == null) {
            logger.warn("Metadata not found, creating a new one.");
            metadata = new Metadata(null, 0, new HashSet<>(), new HashSet<>());
        }

        if (!isQuestionDeleted) {
            logger.info("Adding a new question.");
            metadata.setTotalQuestions(metadata.getTotalQuestions() + 1);
            HashSet<Integer> coveragePages = new HashSet<>(metadata.getCoveragePages());
            if (contentPages != null) {
                coveragePages.addAll(contentPages);
                logger.info("Coverage pages updated. Total pages: {}", coveragePages.size());
            } else {
                logger.warn("Content pages is null, skipping adding to coverage pages.");
            }
            metadata.setCoveragePages(coveragePages);
        } else {
            logger.info("Removing a question.");
            metadata.setTotalQuestions(metadata.getTotalQuestions() - 1);
            HashSet<Integer> coveragePages = new HashSet<>(metadata.getCoveragePages());
            if (contentPages != null) {
                contentPages.forEach(page -> {
                    if (coveragePages.contains(page)) {
                        coveragePages.remove(page);
                        logger.info("Removed page {} from coverage pages.", page);
                    } else {
                        logger.warn("Page {} not found in coverage pages, skipping.", page);
                    }
                });
            } else {
                logger.warn("Content pages is null, skipping removal.");
            }
            metadata.setCoveragePages(coveragePages);
        }

        //TODO SET PRIMARY TOPICS
        logger.info("Saving updated metadata.");
        try {
            metadataRepository.save(metadata);
            logger.info("Metadata successfully saved.");
        } catch (Exception e) {
            logger.error("Error occurred while saving metadata.", e);
        }
    }
}
