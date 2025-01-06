package com.madlen.madlen.controller;

import com.madlen.madlen.model.Metadata;
import com.madlen.madlen.service.MetadataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/metadata")
public class metadataController {

    private final MetadataService metadataService;

    public metadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @GetMapping
    public ResponseEntity<Metadata> getMetadata(){
        return ResponseEntity.ok(this.metadataService.getMetadata());
    }
}
