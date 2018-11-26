package no.orcatag.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

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
    private String folderName;

    public Picture(Folder folder){
        this.folderName = folder.getFoldername();
    }

    @JsonIgnore
    public String getFullFilename() {
        return this.folderName + "/" + this.filename;
    }

    public String getS3Path() {
        return this.folderName + "/" +  FilenameUtils.getBaseName(this.filename) + "/" + this.filename;
    }

    @JsonIgnore
    public String getS3PathOnly() {
        return this.folderName + "/" +  FilenameUtils.getBaseName(this.filename) + "/";
    }
}
