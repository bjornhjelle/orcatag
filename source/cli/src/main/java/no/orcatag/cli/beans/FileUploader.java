package no.orcatag.cli.beans;

import no.orcatag.lib.models.Folder;
import no.orcatag.lib.models.Picture;
import no.orcatag.cli.services.UploadService;

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
