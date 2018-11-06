package no.orcatag.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by bjorn on 20/10/2018.
 */
@Data
@ConfigurationProperties(prefix="orcatag")
@Configuration
public class OrcatagProperties {
    private String url;
    private String username;
    private String password;
}
