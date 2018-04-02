# OrcaTag
OrcaTag is a tool that can be used to analyze pictures of orcas, with the aim to recognize and track individual animals.

## Background
The Norwegian Orca Survey (NOS) has over many years collected photographs of orcas (killer whales) from several locations along the norwegian coast. To learn more about the animals it is important to identify individual animals. NOS has been doing this for a while and as of today more than 900 indiviual orcas has been identified and assigned a unique ID.
An indiviual orca can uniquely be identified by nicks in its dorsal fin, combined with scars and scratches on its saddle patch (https://www.norwegianorca-id.no/key).

NOS has a growing collection of photographs (approx 100 000 per august 2017) and wants to engage with a network of specialists to help go through the pictures and tag individual orcas so that they can be identified. 

OrcaTag will be a tool that can be used by the specialists to do this work.

For more information about the project see: 

* [Email-exchange with Dag Vongraven](doc/input_fra_dag_vongraven.txt) (in norwegian)
* [Functional design](doc/functional_design.md)

## Work packages
To manage the progress of the project, a set of work packages have been identified. Contributors should feel free to contribute to any of these or to suggest new work packages. Below are the work packages defined initially. For each work package a short description should be given and a link to a document with more details.  

## WP 1 - Technical architecture
OrcaTag will utilise a cloud-based architecture where managed infrastructure services are used where available to minimize operation efforts. Functionality will be developed as microservices with loose coupling. This should make it feasible to develop parts independent of other parts, possibly in different programming languages according to a contributor's preferences. For more details see [here](doc/wp_1_technical_architecture.md).

## WP 2 - Photo upload service
For more details see [here](doc/wp_2_photo_upload_service.md).

## WP 2 - Metadata database and search engine
For more details see [here](doc/wp_3_metadata_and_search.md).

## WP 4 - UX and graphical design
For more details see [here](doc/wp_4_ux_and_design).

## WP 5 - Autotagging of photos
For more details see [here](doc/wp_5_autotagging.md).

## WP 6 - To be defined
For more details see [here](doc/wp_6_tbd.md).

## WP 7 - To be defined
For more details see [here](doc/wp_7_tbd.md).

## WP 8 - To be defined
For more details see [here](doc/wp_8_tbd.md).

