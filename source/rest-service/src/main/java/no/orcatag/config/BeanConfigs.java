package no.orcatag.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Created by bjorn on 20/10/2018.
 */
@Configuration
public class BeanConfigs implements AWSCredentialsProvider {

    private S3Properties s3Properties;

    @Bean
    public  AmazonS3Client amazonS3Client(S3Properties s3Properties) {
        this.s3Properties = s3Properties;
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(Regions.EU_WEST_1)
//                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withCredentials(this)
                .build();
    }

    @Override
    public AWSCredentials getCredentials() {
        return new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return s3Properties.getAccessKey();
            }

            @Override
            public String getAWSSecretKey() {
                return s3Properties.getSecretKey();
            }
        };
    }

    @Override
    public void refresh() {

    }

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setMaxUploadSize(50000000);
        return commonsMultipartResolver;
    }
}