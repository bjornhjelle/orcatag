package no.orcatag.controllers;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import lombok.extern.slf4j.Slf4j;
import no.orcatag.config.S3Properties;
import no.orcatag.models.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by bjorn on 20/10/2018.
 */
@Slf4j
@RestController
@RequestMapping("/")
public class PicturesRestController {

    private AmazonS3Client amazonClient;

    private S3Properties s3Properties;

    @Autowired
    PicturesRestController(AmazonS3Client amazonClient, S3Properties s3Properties) {
        this.amazonClient = amazonClient;
        this.s3Properties = s3Properties;
    }




    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/list")
    public List<String> greeting(@RequestParam(value="folderName", defaultValue="") String folderName) {
        log.info("will get contents of S3 folder: " + folderName);
        log.info(s3Properties.getRootFolder());
        List<Bucket> buckets = amazonClient.listBuckets();
        Iterator<Bucket> iterator = buckets.iterator();
        List<String> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next().getName());
        }

        return list;
    }
}
