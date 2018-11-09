package no.orcatag.client;

import lombok.extern.slf4j.Slf4j;
import no.orcatag.models.Folder;
import no.orcatag.models.Picture;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjorn on 26/10/2018.
 */
@Slf4j
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

    public List<String> uploadFile(Picture picture) {
        String url = httpClient.getUrl() + "/uploadFile";

        RestTemplate restTemplate = httpClient.getRestTemplate();
        //restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

/*
        System.out.println(folder.getFoldername());
        HttpEntity<?> httpEntity  =  new HttpEntity<Folder>(folder, httpClient.getHeaders());
*/

        System.out.println("kaller getRestTemplate....");

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();

        File file = new File(picture.getFullFilename());
        log.info("File {} exists: ", picture.getFullFilename(), file.exists());
        body.add("file", file);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);


        ResponseEntity<String> response = restTemplate
                .postForEntity(url, requestEntity, String.class);


        return new ArrayList<>();



/*        ResponseEntity<List<String>> response = httpClient.getRestTemplate().exchange(
                url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<String>>(){});
        System.out.println("Called REST-service, status code:" + response.getStatusCode().toString());
        return response.getBody();*/
    }

}
