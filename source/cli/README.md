
# README

## Starting from command line

    $ java -jar build/libs/cli-1.0-SNAPSHOT.jar --area=a --longitude=45 --latitude=45 --username=bjorn

Or, with gradle:

    $ ./gradlew bootRun

Create a script in ~/bin:

    #!/bin/sh

    java -jar /Users/bjorn/Documents/prosjekter/andre/orca/orcatag/cli/build/libs/cli-1.0-SNAPSHOT.jar $*

And set execute bit:

    $ chmod u+x ~/bin/orcatag

Can then start from any directory.

    $ orcatag  --area=a --longitude=45 --latitude=45 --username=bjorn 2018_observations/