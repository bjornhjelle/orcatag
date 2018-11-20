package no.orcatag.client;

import lombok.extern.slf4j.Slf4j;
import no.orcatag.models.Folder;
import no.orcatag.models.Picture;
import org.apache.commons.io.FileUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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

    public List<String> list() {
        String url = httpClient.getUrl() + "/list";
        log.info("kaller getRestTemplate");
        ResponseEntity<List<String>> response = httpClient.getRestTemplate().exchange(
                url, HttpMethod.GET, httpClient.getRequest(), new ParameterizedTypeReference<List<String>>(){});
        log.info("Called REST-service, status code:" + response.getStatusCode().toString());
        return response.getBody();
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

    public String uploadFile(Picture picture) throws IOException {
        String url = httpClient.getUrl() + "/uploadFile";

        RestTemplate restTemplate = httpClient.getRestTemplate();
        //restTemplate.getMessageConverters().add();

        HttpHeaders headers = httpClient.getHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        formHttpMessageConverter.addPartConverter(new MappingJackson2HttpMessageConverter());
        formHttpMessageConverter.addPartConverter(new ResourceHttpMessageConverter()); // This is hope driven programming


        restTemplate.setMessageConverters(Arrays.asList(
                formHttpMessageConverter
                ,new StringHttpMessageConverter()
                ,new MappingJackson2HttpMessageConverter())
        );

        LinkedMultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("Content-disposition", "form-data; name=file; filename=" + picture.getFullFilename());
        //headerMap.add("Content-type", "application/pdf");
        File file = new File(picture.getFullFilename());
        HttpEntity<byte[]> doc = new HttpEntity<byte[]>(FileUtils.readFileToByteArray(file), headerMap);

        LinkedMultiValueMap<String, Object> multipartReqMap = new LinkedMultiValueMap<>();
        multipartReqMap.add("file", doc);
        multipartReqMap.add("picture", picture);


        HttpEntity<LinkedMultiValueMap<String, Object>> reqEntity = new HttpEntity<>(multipartReqMap, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, reqEntity, String.class);


        return response.getStatusCode().toString();



/*        ResponseEntity<List<String>> response = httpClient.getRestTemplate().exchange(
                url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<String>>(){});
        System.out.println("Called REST-service, status code:" + response.getStatusCode().toString());
        return response.getBody();*/
    }

}
