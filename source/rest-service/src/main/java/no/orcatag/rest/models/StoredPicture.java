package no.orcatag.rest.models;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.Region;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import no.orcatag.models.Picture;
import org.apache.catalina.Store;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.net.URL;

@Entity
@Table(name = "pictures")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StoredPicture extends AuditModel {
    @Id
    @GeneratedValue(generator = "picture_generator")
    @SequenceGenerator(
            name = "picture_generator",
            sequenceName = "picture_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(columnDefinition = "text")
    private String filename;

    @Column(columnDefinition = "text")
    private String key;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "folder_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private StoredFolder folder;

/*    @Column(columnDefinition = "text")
    private String s3Path;*/

    @JsonIgnore
    public static StoredPicture fromPicture(Picture picture, StoredFolder folder, String s3Path, String key) {
        return StoredPicture.builder()
                .filename(picture.getFilename())
                .folder(folder)
                //.s3Path(s3Path)
                .key(key)
                .build();
    }

    public String getSignedUrl(String bucket) {
        String clientRegion = Region.EU_Ireland.toString();
        String bucketName = bucket;
        String objectKey = this.key;

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            // Set the presigned URL to expire after one hour.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 60;
            expiration.setTime(expTimeMillis);

            // Generate the presigned URL.
            System.out.println("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectKey)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            //System.out.println("Pre-Signed URL: " + url.toString());
            return url.toString();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        return "";

    }

    // Getters and Setters (Omitted for brevity)
}