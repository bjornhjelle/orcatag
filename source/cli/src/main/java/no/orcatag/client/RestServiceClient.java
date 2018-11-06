package no.orcatag.client;

import no.orcatag.models.Folder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by bjorn on 26/10/2018.
 */
public class RestServiceClient {

    private HttpClient httpClient;

    public RestServiceClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public List<String> createFolder(Folder folder) {
        String url = httpClient.getUrl() + "/folders";

        RestTemplate restTemplate = httpClient.getRestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        System.out.println(folder.getFoldername());
        HttpEntity<?> httpEntity  =  new HttpEntity<Folder>(folder, httpClient.getHeaders());

        System.out.println("kaller getRestTemplate");
        ResponseEntity<List<String>> response = httpClient.getRestTemplate().exchange(
                url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<String>>(){});
        System.out.println("Called REST-service, status code:" + response.getStatusCode().toString());
        return response.getBody();
    }

}
