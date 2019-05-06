package no.orcatag.cli.beans;

import lombok.extern.slf4j.Slf4j;
import no.orcatag.lib.models.*;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by bjorn on 07/10/2018.
 */
@Slf4j
public class DirectoryProcessor {

    private Arguments arguments;


    public DirectoryProcessor(Arguments arguments) {
        this.arguments = arguments;
    }

    public Folder getFolder() throws IllegalArgumentException, IOException, FileNotFoundException {

        List<Picture> list = new ArrayList<>();
        String foldername = arguments.getDirectory();
        File folderFile = new File(foldername);
        if (!folderFile.exists() || !folderFile.isDirectory()) {
            throw new IllegalArgumentException(arguments.getDirectory() + " is not a directory");
        }
        log.info(arguments.getDirectory());
        try (Stream<Path> paths = Files.walk(Paths.get(arguments.getDirectory()))) {
            paths
                    // .forEach(f -> System.out.println(f.getFileName()));
                    .filter(Files::isRegularFile)
                    .forEach(file -> addToListIfFile(list, file, arguments));
        }
        Folder folder = Folder.builder().pictures(list).foldername(foldername).build();
        Iterator<Picture> iterator = list.iterator();
        while(iterator.hasNext()) {
            iterator.next().setFolderName(folder.getFoldername());
        }
        return folder;
    }

    private void addToListIfFile(List<Picture> list, Path file, Arguments arguments){

        String mimetype = new MimetypesFileTypeMap().getContentType(file.toFile());
        String type = mimetype.split("/")[0];
        log.info("Is image: {}", type.equals("image"));
        if (type.equals("image")) {
           //ExifObject exifObject = null;
            try {
                FileInputStream fileInputStream = new FileInputStream(file.toFile());

                // create the Exif object
                ExifObject exifObject = new ExifObject(fileInputStream);

                log.info(file.getFileName().toString() + "-> " + exifObject);

                // create the Metadata object
                Metadata metadata = new Metadata();
                metadata.setLatitude(exifObject.getGeo().getLat() == null ?
                        arguments.getLatitude() : exifObject.getGeo().getLat());
                metadata.setLongitude(exifObject.getGeo().getLng() == null ?
                        arguments.getLongitude() : exifObject.getGeo().getLng());
                metadata.setArea(arguments.getAreaname());


                // get the md5 hash
                fileInputStream = new FileInputStream(file.toFile());
                String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fileInputStream);
                log.info(file.getFileName().toString() + ":" + md5);
                log.info("exif: {}", exifObject);

                fileInputStream.close();

                // create the picture and add to list
                list.add(Picture.builder()
                        .filename(file.getFileName().toString())
                        .exifObject(exifObject)
                        .metadata(metadata)
                        .md5(md5)
                        .build());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.info(file.getFileName().toString() + " is not an image file");
        }
    }

}
