# OrcaTag
OrcaTag is a tool that can be used to analyze pictures of orcas, with the aim to recognize and track individual animals.

## Background

The Norwegian Orca Survey (NOS) has over many years collected photographs of orcas (killer whales) from several locations along the norwegian coast. To learn more about the animals it is important to identify individual animals. NOS has been doing this for a while and as of today more than 900 indiviual orcas has been identified and assigned a unique ID.
An indiviual orca can uniquely be identified by nicks in its dorsal fin, combined with scars and scratches on its saddle patch (https://www.norwegianorca-id.no/key).

NOS has a growing collection of photographs (approx 100 000 per august 2017) and wants to engage with a network of specialists to help go through the pictures and tag individual orcas so that they can be identified. 

OrcaTag will be a tool that can be used by the specialists to do this work.

References: 

* The Norwegian Orca Survey, https://www.norwegianorcasurvey.no
* The Norwegian Orca-ID Catalogue, https://www.norwegianorca-id.no

## OrcaTag modules

OrcaTag will consist of several parts: 

* a picture upload facility to batch-import pictures
* a database where metadata would be stored
* a search engine to help match new pictures with already identified animals
* an image recognition service that can be used to automate the processing of pictures
* a web-application / dashboard to use when processing a picture, tagging the orcas in the pictures, etc
* an export facility that can be used to export data in a format suitable for import into other tools, such as Excel

### Loading pictures into OrcaTag

In the first version of OrcaTag, a command line utility will be provided to load pictures into the database. An observer would first copy pictures from her camera memory card into a folder on her computer and then run the ultility with a set of arguments to import the pictures. The load facility would perform several steps for each picture: 

1. relevant metadata items from the picture EXIF data (https://en.wikipedia.org/wiki/Exif) will be extracted and stored in the database
1. if the picture is not geotagged and the user has supplied geographical coordinates on the command line, the coordinates (in decimal latitude/longitude) will be added to the database
1. based on the original picture, lower resolution versions will be generated: e.g. thumbnail, medium, large
1. the pictures (in all resolutions) will be stored in a long term storage facility, e g AWS S3.

Given a subfolder named "20170829_\andenes" the ultility would be used in a manner similiar to this: 

    % orcatag_load -u bjorn@biocaching.com -area "andenes" -lat 69.36 -long 16.04 20170829_\andenes
    Password: ******
    will process 134 pictures in folder 20170829_\andenes...
    Done!

### Database
The database will contain metadata extracted from the pictures, and also metadata added by the user analysing the pictures. Such metadata will include: 

* date and time of the picture
* geographical coordinates (latitude/longitude)
* tags added by the user
* animal identification added by the user
* usernames and passwords for users that will have access to OrcaTag

### Search engine
The search engine will contain a document per picture and will be kept in sync with the metadata in the database. The purpose of the search engine is to provide an efficient means to browse earlier observations of orcas when trying to match the current picture with already identified animals.
With data indexed in a search engine, it may be possible to use other available tools such as Kibana to visualise and analyse the data.

### Image recognition service
We see two potential uses of machine learning/image recognition in Orcatag: 
1) given a picture, use image recognition to count the number of nicks in the dorsal fin, if the fin is damaged, the gender, etc. This service will add metadata to the picture. 
2) given a picture, find the orcas in the catalogue who looks the most like the animal in the picture. This service will return a list of the identified orcas that looks most like the one in the picture.

### Web-application
The web-application is the main tool for users who will analyse pictures. They will be able to tag individual animals with characteristics, such as damages and nicks to the dorsal fin. If an individual is recognized as one observed before, its identity can be added. The web-application will have a 'main-page' that will serve as a desktop for doing the tagging/identification. From the same page it should be possible to browse through pictures (earlier observations), and search through the database for pictures with animals with similar characteristics.

This function will have to be specified and developed based on input from the Norwegian Orca Survey staff members.

### Export facility
To be defined.


