package com.biocaching.orca.models;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Store;
import org.springframework.stereotype.Indexed;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Indexed
@Entity
public class Picture {

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


    //getters n setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public ZonedDateTime getCapturedAt() {
        return capturedAt;
    }

    public void setCapturedAt(ZonedDateTime capturedAt) {
        this.capturedAt = capturedAt;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}