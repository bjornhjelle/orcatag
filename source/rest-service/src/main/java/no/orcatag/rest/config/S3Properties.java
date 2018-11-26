package no.orcatag.rest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by bjorn on 20/10/2018.
 */
@Data
@ConfigurationProperties(prefix="s3")
@Configuration
public class S3Properties {
    private String rootBucket;
    private String endpointUrl;
    private String accessKey;
    private String secretKey;
}
