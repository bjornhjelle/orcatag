
# README

## Starting from command line

    $ java -jar build/libs/cli-1.0-SNAPSHOT.jar --area=a --longitude=45 --latitude=45 --username=bjorn


with LOG_LEVEL debug:

    $ env LOG_LEVEL=DEBUG java -jar build/libs/cli.jar --area=a --longitude=45 --latitude=45 --username=bjorn 20181124_vestfjorden/_

Or, with gradle:

    $ ./gradlew bootRun

Create a script in ~/bin:

    #!/bin/sh
    env LOG_LEVEL=ERROR java --add-opens java.base/java.lang=ALL-UNNAMED -jar \
      /Users/bjorn/Documents/prosjekter/andre/orca/orcatag/source/cli/build/libs/cli.jar $*

And set execute bit:

    $ chmod u+x ~/bin/orcatag

Can then start from any directory.

    $ orcatag --area=a --longitude=45 --latitude=45 --username=bjorn 20181124_vestfjorden/ 