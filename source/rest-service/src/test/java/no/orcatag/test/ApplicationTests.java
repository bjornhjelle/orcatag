package no.orcatag.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
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
        System.out.println(body);
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
