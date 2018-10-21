package no.orcatag.beans;

import no.orcatag.models.Folder;
import no.orcatag.models.Picture;
import no.orcatag.services.UploadService;

/**
 * Created by bjorn on 13/10/2018.
 */
public class FileUploader {

    private UploadService uploadService;

    public FileUploader(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    public void upload(Folder folder) {
        for (Picture picture : folder.getPictures()) {
            uploadService.upload(picture.getFullFilename());
        }
    }
}
