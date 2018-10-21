package no.orcatag.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}