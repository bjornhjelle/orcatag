package no.orcatag.test;

import lombok.extern.slf4j.Slf4j;
import no.orcatag.rest.RestServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestServiceApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void getTest() {
        String body = this.restTemplate
                .withBasicAuth("orcatag", "test")
                .getForObject("/greeting", String.class);
        log.info(body);
        assertThat(body).contains("greeting");
    }

    @Test
    public void postTest() throws Exception {
        String body = this.restTemplate
                .withBasicAuth("orcatag", "test")
                .postForObject(new URI("/greeting"),"test", String.class);
        assertThat(body).contains("greeting");
    }

}
