package no.orcatag.beans;

import lombok.extern.slf4j.Slf4j;
import no.orcatag.models.Arguments;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by bjorn on 06/10/2018.
 */


@Slf4j
public class ArgumentProcessor
{

    public ArgumentProcessor()
    {

    }


    public static void usage() {
        System.out.println("Usage:");
        System.out.println("$ oractag --area=<area name> --latiude=<latitude> --longitude=<longitude> <directory>");
    }


    public static Arguments process(ApplicationArguments arguments) throws Exception
    {

        Arguments passedArguments = new Arguments();
        try {

            try {
                passedArguments.setDirectory(arguments.getNonOptionArgs().get(0));
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Argument directory name is missing");
            }

/*            final Result<Record1<String>> result =
                    dslContext.select(USERS.NAME)
                            .from(USERS)
                            .where(USERS.NAME.likeIgnoreCase(name))
                            .fetch();*/
            // Check if option argument --area is used.
            // In the following example: java -jar sample.jar --area=lofoten
            // format is the option argument with two values: xml, csv.
            if (arguments.containsOption("area")) {
                passedArguments.setAreaname(arguments.getOptionValues("area").get(0));
            } else {
                throw new IllegalArgumentException("argument area is missing");
            }

            if (arguments.containsOption("username")) {
                passedArguments.setUsername(arguments.getOptionValues("username").get(0));

            } else {
                throw new IllegalArgumentException("argument username is missing");
            }

            if (arguments.containsOption("longitude")) {
                passedArguments.setLongitude(Double.valueOf(arguments.getOptionValues("longitude").get(0)));
            } else {
                throw new IllegalArgumentException("argument longitude is missing");
            }

            if (arguments.containsOption("latitude")) {
                passedArguments.setLatitude(Double.valueOf(arguments.getOptionValues("latitude").get(0)));
            } else {
                throw new IllegalArgumentException("argument latitude is missing");
            }

            // at this point we should have both username, area name, latitude, longitude and a directory



        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            usage();
            throw e;

        }// do something

        return passedArguments;
    }
}