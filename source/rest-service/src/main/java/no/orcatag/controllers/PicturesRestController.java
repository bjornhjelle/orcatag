package no.orcatag.controllers;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ListBucketsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import lombok.extern.slf4j.Slf4j;
import no.orcatag.config.OrcatagProperties;
import no.orcatag.config.S3Properties;
import no.orcatag.models.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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

    @RequestMapping(value = "/folders", method = RequestMethod.POST)
    public List<String> greeting(@RequestBody Folder folder) {
        log.info("will create S3 folder: {}/{}", s3Properties.getRootFolder(), folder.getFoldername());
        String bucketName = String.format("{}/{}", s3Properties.getRootFolder(), folder.getFoldername());
        System.out.println(bucketName);
        List<String> list = new ArrayList<>();
        try {
            System.out.println("exists: " + amazonClient.doesBucketExistV2(bucketName));
            if (!amazonClient.doesBucketExistV2(bucketName)) {
                Bucket bucket = amazonClient.createBucket(bucketName);

                list = Arrays.asList(
                        bucket.getName()
                );
            }
        } catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it and returned an error response.
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }

        return list;
    }

    @RequestMapping("/list")
    public List<String> list(@RequestParam(value="folderName", defaultValue="") String folderName) {
        log.info("will get contents of S3 folder: " + folderName);
        log.info(s3Properties.getRootFolder());
        List<Bucket> buckets = amazonClient.listBuckets();
        Iterator<Bucket> iterator = buckets.iterator();
        List<String> list = new ArrayList<>();
        while (iterator.hasNext()) {
            Bucket bucket = iterator.next();
            list.add(iterator.next().getName());
            ObjectListing listing = amazonClient.listObjects(bucket.getName());
            list.add(listing.getBucketName());
        }


        return list;
    }

    @RequestMapping(value="/uploadFolder", method=RequestMethod.POST)
    public @ResponseBody Folder upload(@RequestBody Folder folder){
        log.info(folder.getFoldername());
        return folder;
    }


}
