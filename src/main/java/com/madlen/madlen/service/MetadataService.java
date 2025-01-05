package com.madlen.madlen.service;

import com.madlen.madlen.model.Metadata;
import com.madlen.madlen.repository.MetadataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetadataService {

    private final MetadataRepository metadataRepository;

    public MetadataService(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    public Metadata getMetadata() {
        return this.metadataRepository.findById(1).get();
    }

    public void updateMetadata(List<Integer> contentPages){
        Metadata metadata = getMetadata();

        metadata.setTotalQuestions(metadata.getTotalQuestions() + 1);
        metadata.getCoveragePages().addAll(contentPages);
        //TODO SET PRIMARY TOPICS
        metadataRepository.save(metadata);
    }

}
