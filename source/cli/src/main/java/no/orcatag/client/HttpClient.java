package no.orcatag.client;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * Created by bjorn on 26/10/2018.
 */
public class HttpClient {


    private String url;

    private HttpEntity<String> request;

    private RestTemplate restTemplate;

    public HttpHeaders getHeaders() {
        return headers;
    }

    private HttpHeaders headers;

    public String getUrl() {
        return url;
    }

    public HttpEntity<String> getRequest() {
        return request;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public HttpClient(String url, String username, String password) {
        this.url = url;
        String plainCreds = String.format("%s:%s", username, password);
        //System.out.println(plainCreds);
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        this.request = new HttpEntity<String>(headers);
        restTemplate = new RestTemplate();
        //request = new HttpEntity<String>(headers);
    }



}
