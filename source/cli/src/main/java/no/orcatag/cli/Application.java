package no.orcatag.cli;

import lombok.extern.slf4j.Slf4j;
import no.orcatag.cli.client.HttpClient;
import no.orcatag.cli.client.RestServiceClient;
import no.orcatag.cli.config.OrcatagProperties;
import no.orcatag.lib.models.Arguments;
import no.orcatag.lib.models.Folder;
import no.orcatag.lib.models.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

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
        System.out.println("Number of arguments:" + args.length);
        //UploadService uploadService = new UploadService();
        for (String arg : args) {
            log.debug(arg);
        }
        try {

            List<String> s3Buckets = this.restServiceClient.list();
            log.info("S3 buckets:");
            log.info(s3Buckets.toString());
            log.info("--------------------------------");

           /* Arguments arguments = ArgumentProcessor.process(new DefaultApplicationArguments(args));
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
            }*/
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