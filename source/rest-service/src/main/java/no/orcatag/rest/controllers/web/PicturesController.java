package no.orcatag.rest.controllers;

import com.amazonaws.services.s3.AmazonS3Client;
import no.orcatag.rest.config.S3Properties;
import no.orcatag.rest.models.Greeting;
import no.orcatag.rest.models.StoredPicture;
import no.orcatag.rest.repositories.FolderRepository;
import no.orcatag.rest.repositories.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class PicturesController {

    // sjekk denne:
    // https://docs.aws.amazon.com/AmazonS3/latest/dev/ShareObjectPreSignedURLJavaSDK.html

    private S3Properties s3Properties;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    PicturesController(S3Properties s3Properties) {
        this.s3Properties = s3Properties;
    }

    @GetMapping("/folders/{folderId}/pictures")
    public String getAnswersByQuestionId(@PathVariable Long folderId, Model model) {
        model.addAttribute("bucket", s3Properties.getRootBucket());
        model.addAttribute("pictures", pictureRepository.findByFolderId(folderId));
/*        for (StoredPicture p : pictureRepository.findByFolderId(folderId)){
            System.out.println(p.getSignedUrl("123"));
        }*/
        return "pictures";
    }


}
