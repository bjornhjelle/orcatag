package no.orcatag.rest.controllers;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import no.orcatag.rest.config.S3Properties;
import no.orcatag.models.Folder;
import no.orcatag.models.Picture;
import no.orcatag.rest.models.StoredFolder;
import no.orcatag.rest.models.StoredPicture;
import no.orcatag.rest.repositories.FolderRepository;
import no.orcatag.rest.repositories.PictureRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;

import static javax.imageio.ImageIO.*;

/**
 * Created by bjorn on 20/10/2018.
 */
@Slf4j
@RestController
@RequestMapping("/s3")
public class PicturesRestController {

    private AmazonS3Client amazonClient;

    private S3Properties s3Properties;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    PicturesRestController(AmazonS3Client amazonClient, S3Properties s3Properties) {
        this.amazonClient = amazonClient;
        this.s3Properties = s3Properties;
    }

    @RequestMapping(value = "/foldersOld", method = RequestMethod.POST)
    public List<String> greeting(@RequestBody Folder folder) {

        List<String> list = new ArrayList<>();

        // create rootBucket if it does not exist
        String rootBucketName = s3Properties.getRootBucket();
        String bucketName = String.format("%s/%s", s3Properties.getRootBucket(), folder.getFoldername());
        if (amazonClient.doesBucketExistV2(rootBucketName)) {
            log.info("Root bucket exists");
        } else {
            Bucket rootBucket = amazonClient.createBucket(rootBucketName);
            if (amazonClient.doesBucketExistV2(rootBucketName)) {
                log.info("Successfully created root bucket {}", rootBucketName);
            } else {
                log.error("Successfully created root bucket {}", rootBucketName);
            }
        }

        // check if bucket already exists
        if (amazonClient.doesObjectExist(rootBucketName, folder.getFoldername())) {
            log.info("StoredFolder {} exists in rootBucket {}", folder.getFoldername(), rootBucketName);
        } else {
            log.info("StoredFolder {} does not exist in rootBucket {}, will be created", folder.getFoldername(), rootBucketName);
        }

        for (Picture picture : folder.getPictures()) {
            log.info(picture.getFullFilename());
            PutObjectResult putObjectResult = amazonClient.putObject(rootBucketName, picture.getFullFilename(), "");
            log.info("Uploaded {} bytes.", putObjectResult.getMetadata().getContentLength());
        }
        return list;


/*        log.info("will create bucket in rootBucket: {}", folder.getFoldername());

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

        return list;*/
    }

    @RequestMapping("/folders/{folderName}")
    public ResponseEntity<List<String>> list(@PathVariable("folderName") String folderName) {
        log.info("will get contents of S3 folder: " + folderName);
        log.info(s3Properties.getRootBucket());
        List<Bucket> buckets = amazonClient.listBuckets();
        Iterator<Bucket> iterator = buckets.iterator();
        List<String> list = new ArrayList<>();
        while (iterator.hasNext()) {
            Bucket bucket = iterator.next();
            list.add(bucket.getName());
/*            ObjectListing listing = amazonClient.listObjects(bucket.getName());
            List<S3ObjectSummary> s3ObjectSummaryList = listing.getObjectSummaries();
            Iterator<S3ObjectSummary> objectIterator = s3ObjectSummaryList.iterator();
            while (objectIterator.hasNext()) {
                list.add(objectIterator.g);
            }*/
        }


        //return list;
        return ResponseEntity.ok(list);
    }


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, headers = "Content-Type=multipart/form-data")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestPart("picture") Picture picture) {
        log.info("received request, filename: {} ", file.getOriginalFilename());
        log.info("received request, picture fullfilename: {} ", picture.getFullFilename());

        // save folder in the database
        List<StoredFolder> storedFolderList = folderRepository.findByName(picture.getFolderName());
        StoredFolder storedFolder = null;
        if (storedFolderList.isEmpty()) {
            storedFolder = StoredFolder.builder()
                    .name(picture.getFolderName())
                    .build();
            folderRepository.save(storedFolder);
        } else {
            storedFolder = storedFolderList.get(0);
        }
        log.info("folder id: {}", storedFolder.getId());
        // ------------------------------------


        makeSureRootBucketExists();
        String s3Path = uploadTheFile(file, picture, storedFolder);

        return new ResponseEntity<String>(s3Path, HttpStatus.OK);
    }


    @RequestMapping(value = "/uploadFolder", method = RequestMethod.POST)
    public @ResponseBody
    Folder upload(@RequestBody Folder folder) {
        log.info(folder.getFoldername());
        return folder;
    }


    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

/*    private String generateFileName(MultipartFile multiPart) {
        return multiPart.getOriginalFilename().replace(" ", "_");
    }*/


    private void uploadReducedFileTos3bucket(String filename, File file, Picture picture, int width, int height, String label) throws IOException {
        String thumbnailFilename = String.format(
                "%s%s_%s.%s"
                , picture.getS3PathOnly()
                , FilenameUtils.getBaseName(filename)
                , label
                , FilenameUtils.getExtension(filename));
        System.out.println("Upload thumbnail: " + thumbnailFilename);

        BufferedImage bufferedImage = Thumbnails.of(file).size(width, height).keepAspectRatio(true).asBufferedImage();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, FilenameUtils.getExtension(filename), os);
        InputStream thumbnailInputStream = new ByteArrayInputStream(os.toByteArray());


        InputStream tmpInputStream = new ByteArrayInputStream(os.toByteArray());
        byte[] byteArray = IOUtils.toByteArray( tmpInputStream );
        Long contentLength = Long.valueOf(byteArray.length);
        tmpInputStream.close();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(contentLength);



        PutObjectResult putObjectResult = amazonClient.putObject(
                new PutObjectRequest(s3Properties.getRootBucket(), thumbnailFilename, thumbnailInputStream, objectMetadata)
                        //       .withCannedAcl(CannedAccessControlList.BucketOwnerRead));
                        .withCannedAcl(CannedAccessControlList.BucketOwnerRead));

    }


    private void uploadMetadataTos3bucket(String filename, Picture picture) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println(objectMapper.writeValueAsString(picture));
        String metadataFilename = String.format(
                "%s%s.json"
                , picture.getS3PathOnly()
                , FilenameUtils.getBaseName(filename));

        String fileContentString = objectMapper.writeValueAsString(picture);
        byte[] fileContentBytes = fileContentString.getBytes(StandardCharsets.UTF_8);
        InputStream fileInputStream = new ByteArrayInputStream(fileContentBytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(ContentType.APPLICATION_JSON.toString());
        metadata.setContentLength(fileContentBytes.length);
        PutObjectRequest putObjectRequest = new PutObjectRequest(
                s3Properties.getRootBucket(), metadataFilename, fileInputStream, metadata)
                .withCannedAcl(CannedAccessControlList.BucketOwnerRead);
        PutObjectResult putObjectResult = amazonClient.putObject(putObjectRequest);


    }

    private void uploadFileTos3bucket(String filename, File file, Picture picture) throws IOException {


        PutObjectResult putObjectResult = amazonClient.putObject(new PutObjectRequest(s3Properties.getRootBucket(), filename, file)
                //       .withCannedAcl(CannedAccessControlList.BucketOwnerRead));
                .withCannedAcl(CannedAccessControlList.BucketOwnerRead));
        System.out.println(putObjectResult.getContentMd5());
        System.out.println(picture.getMd5());

    }

    private String uploadTheFile(MultipartFile multipartFile, Picture picture, StoredFolder storedFolder) {
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            // String fileName = generateFileName(multipartFile);
            String fileName = picture.getS3Path().replace(" ", "_");
            fileUrl = s3Properties.getEndpointUrl() + "/" + s3Properties.getRootBucket() + "/" + fileName;
            uploadFileTos3bucket(fileName, file, picture);
            uploadMetadataTos3bucket(fileName, picture);
            uploadReducedFileTos3bucket(fileName, file, picture, 100, 100, "thumbnail");
            uploadReducedFileTos3bucket(fileName, file, picture, 500, 500, "small");
            uploadReducedFileTos3bucket(fileName, file, picture, 1000, 1000, "medium");

            StoredPicture storedPicture = pictureRepository.findByFolderIdAndFilename(
                    storedFolder.getId()
                    , picture.getFilename()
            );
            if (storedPicture == null) {
                storedPicture = StoredPicture.fromPicture(picture, storedFolder, fileUrl, fileName);
            } else {
                storedPicture = StoredPicture.fromPicture(picture, storedFolder, fileUrl, fileName);
            }
            pictureRepository.save(storedPicture);

            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    private void makeSureRootBucketExists() {
        String rootBucketName = s3Properties.getRootBucket();
        if (amazonClient.doesBucketExistV2(rootBucketName)) {
            log.info("Root bucket exists");
        } else {
            Bucket rootBucket = amazonClient.createBucket(rootBucketName);
            if (amazonClient.doesBucketExistV2(rootBucketName)) {
                log.info("Successfully created root bucket {}", rootBucketName);
            } else {
                log.error("Successfully created root bucket {}", rootBucketName);
            }
        }
    }


}
