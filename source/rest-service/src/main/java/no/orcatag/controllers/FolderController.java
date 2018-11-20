package no.orcatag.controllers;

import no.orcatag.models.Folder;
import no.orcatag.models.StoredFolder;
import no.orcatag.repositories.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


// https://www.callicoder.com/spring-boot-jpa-hibernate-postgresql-restful-crud-api-example/

@RestController
public class FolderController {

    @Autowired
    private FolderRepository folderRepository;

    @GetMapping("/folders")
    public Page<StoredFolder> getQuestions(Pageable pageable) {
        return folderRepository.findAll(pageable);
    }

}
