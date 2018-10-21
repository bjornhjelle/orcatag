package no.orcatag.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by bjorn on 12/10/2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Picture {
    private String md5;
    private String filename;
    private ExifObject exifObject;
    private Metadata metadata;
    private Folder folder;
    public Picture(Folder folder){
        this.folder = folder;
    }

    public String getFullFilename() {
        return folder.getFoldername() + "/" + this.filename;
    }
}
