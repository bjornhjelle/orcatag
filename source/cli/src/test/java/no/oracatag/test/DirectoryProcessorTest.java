package no.oracatag.test;

import no.orcatag.beans.DirectoryProcessor;
import no.orcatag.models.Arguments;
import no.orcatag.models.ExifObject;
import no.orcatag.models.Folder;
import no.orcatag.models.Picture;
import no.orcatag.services.UploadService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by bjorn on 07/10/2018.
 */
public class DirectoryProcessorTest {

    //private DirectoryProcessor directoryProcessor;


    @Before
    public void DirectoryProcessor() {

        //directoryProcessor = new DirectoryProcessor();
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_raise_exception_if_directory_does_not_exist() throws IOException {
        Arguments arguments = Arguments.builder().directory("tulledirectory").build();
        DirectoryProcessor directoryProcessor = new DirectoryProcessor(arguments);
        Folder folder = directoryProcessor.getFolder();
    }


    // a directory with no files
    @Test
    public void should_return_a_list_of_filenames_0()  throws IOException {
        Arguments arguments = Arguments.builder().directory("src/test/resources/files_test_0").build();
        DirectoryProcessor directoryProcessor = new DirectoryProcessor(arguments);
        Folder folder = directoryProcessor.getFolder();
        assertThat(folder.getPictures().size()).isEqualTo(0);
    }


    @Test
    public void should_return_a_list_of_filenames_1()  throws IOException {
        Arguments arguments = Arguments.builder().directory("src/test/resources/files_test_1").build();
        DirectoryProcessor directoryProcessor = new DirectoryProcessor(arguments);
        Folder folder = directoryProcessor.getFolder();
        assertThat(folder.getPictures().size()).isEqualTo(1);
        assertThat(folder.getPictures().get(0).getFilename()).isEqualTo("DSCN1857.JPG");
    }

    @Test
    public void should_return_a_list_of_filenames_2()  throws IOException {
        Arguments arguments = Arguments.builder().directory("src/test/resources/files_test_2").build();
        DirectoryProcessor directoryProcessor = new DirectoryProcessor(arguments);
        Folder folder = directoryProcessor.getFolder();
        assertThat(folder.getPictures().size()).isEqualTo(5);
        List<String> filenames = folder.getPictures().stream()
                .map(v -> v.getFilename()).collect(Collectors.toList());
        assertThat(filenames.contains("DSCN1898.JPG")).isTrue();
        assertThat(filenames.contains("DSCN1896.JPG")).isTrue();
        assertThat(filenames.contains("29-101-07.tif")).isTrue();
        assertThat(filenames.contains("_DSC4292_NKW-238.jpg")).isTrue();
        assertThat(filenames.contains("_DSC4465_NKW-263.jpg")).isTrue();
    }


    //
    @Test
    public void should_extract_exif()  throws IOException {
        Arguments arguments = Arguments.builder().directory("src/test/resources/files_test_1").build();
        DirectoryProcessor directoryProcessor = new DirectoryProcessor(arguments);
        Folder folder = directoryProcessor.getFolder();
        Picture picture = folder.getPictures().get(0);
        assertThat(picture.getExifObject().getGeo().getLat()).isEqualTo(10.268561);
        assertThat(picture.getExifObject().getGeo().getLng()).isEqualTo(-61.235239);
        assertThat(picture.getExifObject().getGeo().getLat()).isEqualTo(picture.getMetadata().getLatitude());
        assertThat(picture.getExifObject().getGeo().getLng()).isEqualTo(picture.getMetadata().getLongitude());
    }

    //
    @Test
    public void should_calculate_md5_hash()  throws IOException {
        Arguments arguments = Arguments.builder().directory("src/test/resources/files_test_4").build();
        DirectoryProcessor directoryProcessor = new DirectoryProcessor(arguments);
        Folder folder = directoryProcessor.getFolder();
        List<String> hashes = folder.getPictures().stream()
                .map(v -> v.getMd5()).collect(Collectors.toList());
        System.out.println("hashes: " + hashes.toString());
        assertThat(hashes.contains("10ecdf7840a03d611cb5b951e71b56ef")).isTrue();
        assertThat(hashes.contains("4734241333c32387f91dd7a7c211ae63")).isTrue();
        assertThat(hashes.contains("c9431f9ae37136b6cf7a20ee66064ac5")).isTrue();
//        assertThat(folder.getPictures().get(0).getMd5()).isEqualTo("10ecdf7840a03d611cb5b951e71b56ef");
    }

    //
    @Test
    public void should_use_coordinates_from_arguments()  throws IOException {
        Arguments arguments = Arguments.builder()
                .latitude(62.3)
                .longitude(11.5)
                .areaname("Storfjorden")
                .directory("src/test/resources/files_test_3").build();
        DirectoryProcessor directoryProcessor = new DirectoryProcessor(arguments);
        Folder folder = directoryProcessor.getFolder();
        Picture picture = folder.getPictures().get(0);
        System.out.println(picture.getExifObject());
        assertThat(picture.getMetadata().getLatitude()).isEqualTo(62.3);
        assertThat(picture.getMetadata().getLongitude()).isEqualTo(11.5);
        assertThat(picture.getMetadata().getArea()).isEqualTo("Storfjorden");
    }
}
