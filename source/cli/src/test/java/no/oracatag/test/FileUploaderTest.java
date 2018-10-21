package no.oracatag.test;

import no.orcatag.beans.DirectoryProcessor;
import no.orcatag.beans.FileUploader;
import no.orcatag.models.Arguments;
import no.orcatag.models.Folder;
import no.orcatag.models.Picture;
import no.orcatag.services.UploadService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by bjorn on 07/10/2018.
 */
public class FileUploaderTest {

    //private DirectoryProcessor directoryProcessor;

    private UploadService uploadServiceMock;
    private FileUploader fileUploader;

    @Before
    public void setUp() {
        uploadServiceMock = mock(UploadService.class);
        fileUploader = new FileUploader(uploadServiceMock);
        //directoryProcessor = new DirectoryProcessor();
    }


    @Test
    public void should_return_a_list_of_filenames_1()  throws IOException {
        Arguments arguments = Arguments.builder().directory("src/test/resources/files_test_1").build();
        DirectoryProcessor directoryProcessor = new DirectoryProcessor(arguments);
        Folder folder = directoryProcessor.getFolder();
        fileUploader.upload(folder);
        verify(uploadServiceMock, times(1)).upload("src/test/resources/files_test_1/DSCN1857.JPG");
    }

    @Test
    public void should_return_a_list_of_filenames_2()  throws IOException {
        Arguments arguments = Arguments.builder().directory("src/test/resources/files_test_2").build();
        DirectoryProcessor directoryProcessor = new DirectoryProcessor(arguments);
        Folder folder = directoryProcessor.getFolder();

        fileUploader.upload(folder);
        verify(uploadServiceMock, times(5)).upload(anyString());
        for (Picture picture : folder.getPictures()) {
            Mockito.verify(uploadServiceMock, Mockito.atLeastOnce()).upload(picture.getFullFilename());
        }

    }
}
