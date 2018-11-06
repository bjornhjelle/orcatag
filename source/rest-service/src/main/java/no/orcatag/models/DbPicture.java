package no.orcatag.models;

import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Store;
import org.springframework.stereotype.Indexed;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

/**
 * Created by bjorn on 23/10/2018.
 */

@Data
@Indexed
@Entity
public class DbPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Field(store = Store.YES)
    private String filename;

    @Field
    private ZonedDateTime capturedAt;

    @Field
    private ZonedDateTime createdAt;

    @Field
    private ZonedDateTime updatedAt;

}