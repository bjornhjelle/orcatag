package no.orcatag.rest.controllers.web;

import no.orcatag.models.Folder;
import no.orcatag.rest.models.Greeting;
import no.orcatag.rest.models.StoredFolder;
import no.orcatag.rest.repositories.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


// https://www.callicoder.com/spring-boot-jpa-hibernate-postgresql-restful-crud-api-example/

@Controller
public class FolderController {

    @Autowired
    private FolderRepository folderRepository;

    @GetMapping("/folders")
    public String getQuestions(Pageable pageable, Model model) {
//    public Page<StoredFolder> getQuestions(Pageable pageable, Model model) {

        //model.addAttribute("folders", folderRepository.findAll(pageable));
        Page<StoredFolder> folders = folderRepository.findAll(pageable);
        //folders.getTotalElements()
        model.addAttribute("folders", folders);
        return "folders";
    }

}
