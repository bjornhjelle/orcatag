package no.orcatag.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.Store;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "pictures")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "folder_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private StoredFolder folder;


    @Column(columnDefinition = "text")
    private String s3Path;

    @JsonIgnore
    public static StoredPicture fromPicture(Picture picture, StoredFolder folder, String s3Path) {
        return StoredPicture.builder()
                .filename(picture.getFilename())
                .folder(folder)
                .s3Path(s3Path)
                .build();
    }

    // Getters and Setters (Omitted for brevity)
}