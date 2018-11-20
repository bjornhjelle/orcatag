package no.orcatag.controllers;

import no.orcatag.models.StoredPicture;
import no.orcatag.repositories.FolderRepository;
import no.orcatag.repositories.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PicturesController {


    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private FolderRepository folderRepository;

    @GetMapping("/folders/{folderId}/pictures")
    public List<StoredPicture> getAnswersByQuestionId(@PathVariable Long folderId) {
        return pictureRepository.findByFolderId(folderId);
    }


}
