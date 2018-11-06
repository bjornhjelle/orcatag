package no.orcatag.services;

import java.io.File;

/**
 * Created by bjorn on 12/10/2018.
 */
public class  UploadService {

    public void upload(String filename) {
        // first check that the file exists and can be read
        File file = new File(filename);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("cannot open file: " + filename);
        }
    }
}
