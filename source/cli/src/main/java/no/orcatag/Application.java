package no.orcatag;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.orcatag.beans.ArgumentProcessor;
import no.orcatag.beans.DirectoryProcessor;
import no.orcatag.client.HttpClient;
import no.orcatag.client.RestServiceClient;
import no.orcatag.config.OrcatagProperties;
import no.orcatag.models.Arguments;
import no.orcatag.models.Folder;
import no.orcatag.models.Picture;
import no.orcatag.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by bjorn on 06/10/2018.
 */

@Slf4j
@EnableConfigurationProperties
@SpringBootApplication
public class Application implements CommandLineRunner {

    private RestServiceClient restServiceClient;

    @Autowired
    private OrcatagProperties orcatagProperties;

    public static void main(String[] args) {
        try {
            log.info("Starting...");
            SpringApplication.run(Application.class, args);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void run(String... args) throws Exception {
        this.restServiceClient = new RestServiceClient(new HttpClient(orcatagProperties.getUrl(),
                orcatagProperties.getUsername(), orcatagProperties.getPassword()));
        log.debug("Number of arguments:" + args.length);
        UploadService uploadService = new UploadService();
        for (String arg : args) {
            log.debug(arg);
        }
        try {

            List<String> s3Buckets = this.restServiceClient.list();
            log.info("S3 buckets:");
            log.info(s3Buckets.toString());
            log.info("--------------------------------");

            Arguments arguments = ArgumentProcessor.process(new DefaultApplicationArguments(args));
            log.info("arguments: " + arguments);
            DirectoryProcessor directoryProcessor = new DirectoryProcessor(arguments);
            log.info("Read files from directory {}", arguments.getDirectory());
            Folder folder = directoryProcessor.getFolder();
            log.info("Found %d files", folder.getPictures().size());
            System.out.println(String.format("Will upload contents of %s to OrcaTag.", folder.getFoldername()));

            ObjectMapper objectMapper = new ObjectMapper();
            log.debug(objectMapper.writeValueAsString(folder));

            //List<String> results = this.restServiceClient.createFolder(folder);
            //results.stream().forEach(str -> log.info(str));

            for (Picture picture : folder.getPictures()) {

                System.out.println(String.format("Upload %s...", picture.getFilename()));
                String results = this.restServiceClient.uploadFile(picture);
                if (!results.equals("200")) {
                    log.error("Results: {}, failed to upload?", results);
                }
                //list.stream().forEach((s) -> System.out.println(s));
            }
            System.out.println("done!");

        } catch (IllegalArgumentException ex) {
            System.exit(-1);
        }


        // check that directory exists and is a directory
        // check that it is not empty
        // read the files and list out on
        System.exit(-1);

    }
}