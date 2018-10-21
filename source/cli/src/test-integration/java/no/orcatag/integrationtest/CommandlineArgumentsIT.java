package no.orcatag.integrationtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by bjorn on 06/10/2018.
 */

@RunWith(SpringRunner.class)
public class CommandlineArgumentsIT {

    @Test
    public void context() {
        System.out.println("integrationtest");
    }


/*    @Test
    public void testRun() throws Exception {
        ApplicationArguments runner = ctx.getBean(ApplicationArguments.class);
        runner.run ( "-k", "arg1", "-i", "arg2");
    }*/

}
