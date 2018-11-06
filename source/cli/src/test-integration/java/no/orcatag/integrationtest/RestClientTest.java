package no.orcatag.integrationtest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by bjorn on 23/10/2018.
 */
@Slf4j
public class RestClientTest {

    public static void main(String args[]) {
        String plainCreds = "orcatag:test";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(headers);
        String url = "http://localhost:8080/list";
        ResponseEntity<List<String>> response = restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<List<String>>(){});
        System.out.println("Status code:" + response.getStatusCode().toString());


    }


}
