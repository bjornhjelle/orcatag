package no.oracatag.test;

import no.orcatag.models.Picture;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PictureTest {

    @Test
    public void should_have_s3_path(){
        Picture picture = Picture.builder()
                .folderName("test")
                .filename("123.jpg")
                .build();
        assertThat(picture.getFullFilename()).isEqualTo("test/123.jpg");
        assertThat(picture.getS3Path()).isEqualTo("test/123/123.jpg");
    }
}
