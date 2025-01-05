package com.madlen.madlen.service;

import com.madlen.madlen.model.Metadata;
import com.madlen.madlen.repository.MetadataRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class MetadataService {

    private final MetadataRepository metadataRepository;

    public MetadataService(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    public Metadata getMetadata() {
        return this.metadataRepository.findById(1).orElse(null);
    }

    public void updateMetadata(List<Integer> contentPages, boolean isQuestionDeleted){
        Metadata metadata = this.metadataRepository.findById(1).orElse(null);

        if (metadata == null) {
            metadata = new Metadata(null, 0, new HashSet<>(), new HashSet<>());
        }

        if(!isQuestionDeleted){
            metadata.setTotalQuestions(metadata.getTotalQuestions() + 1);
            HashSet<Integer> coveragePages = new HashSet<>(metadata.getCoveragePages());
            coveragePages.addAll(contentPages);
            metadata.setCoveragePages(coveragePages);
        } else {
            metadata.setTotalQuestions(metadata.getTotalQuestions() - 1);
            HashSet<Integer> coveragePages = new HashSet<>(metadata.getCoveragePages());
            contentPages.forEach(coveragePages::remove);
            metadata.setCoveragePages(coveragePages);
        }

        //TODO SET PRIMARY TOPICS
        metadataRepository.save(metadata);
    }
}
