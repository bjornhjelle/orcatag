package no.oracatag.test;

import no.orcatag.cli.beans.ArgumentProcessor;
import no.orcatag.lib.models.Arguments;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.DefaultApplicationArguments;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * Created by bjorn on 06/10/2018.
 */

public class ArgumentProcessorTest
{
    @Before
    public void setUp(){
    }

    //
    @Test
    public void should_not_throw_exception_if_sufficient_parameters() throws Exception  {
        String[] parameters = {"--area=a", "--latitude=0.3", "--longitude=4", "dir", "--username=bjorn.hjelle@gmail.com"};
        Arguments arguments = ArgumentProcessor.process(new DefaultApplicationArguments(parameters));

        assertThat(arguments.getAreaname()).isEqualTo("a");
        assertThat(arguments.getUsername()).isEqualTo("bjorn.hjelle@gmail.com");
        assertThat(arguments.getLatitude()).isEqualTo(0.3);
        assertThat(arguments.getLongitude()).isEqualTo(4);
        assertThat(arguments.getDirectory()).isEqualTo("dir");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_if_latitude_is_missing() throws Exception  {
        String[] parameters = {"--area=a", "--longitude=4", "dir"};
        Arguments arguments = ArgumentProcessor.process(new DefaultApplicationArguments(parameters));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_if_username_is_missing() throws Exception  {
        String[] parameters = {"--area=a", "--longitude=4", "--latitude=4", "dir"};
        Arguments arguments = ArgumentProcessor.process(new DefaultApplicationArguments(parameters));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_if_longitude_is_missing() throws Exception  {
        String[] parameters = {"--area=a", "--latitude=3", "dir"};
        Arguments arguments = ArgumentProcessor.process(new DefaultApplicationArguments(parameters));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_if_area_is_missing() throws Exception  {
        String[] parameters = {"--longitude=56","--latitude=4", "dir"};
        Arguments arguments = ArgumentProcessor.process(new DefaultApplicationArguments(parameters));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_if_directory_is_missing() throws Exception  {
        String[] parameters = {"--area=a", "--longitude=b","--latitude=b"};
        Arguments arguments = ArgumentProcessor.process(new DefaultApplicationArguments(parameters));
    }

}