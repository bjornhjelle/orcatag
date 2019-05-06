package no.oracatag.test;

import no.orcatag.cli.beans.DirectoryProcessor;
import no.orcatag.cli.beans.FileUploader;
import no.orcatag.lib.models.Arguments;
import no.orcatag.lib.models.Folder;
import no.orcatag.lib.models.Picture;
import no.orcatag.cli.services.UploadService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by bjorn on 07/10/2018.
 */
public abstract class FileUploaderTest {

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
