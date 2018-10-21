# Technical architecture
To minimise the operational cost of a running implementation, we will utilize a cloud-based architecture. 
(This version of the technical architecture is based on my knowledge of Amazon AWS, but other cloud service providers could be used, such as Google cloud).

Applications and services will be developed as micro services and deployed as [AWS Elastic Beanstalk services](https://aws.amazon.com/elasticbeanstalk/). Such services must make functionality available over HTTPS so that they can be called by other services without knowledge of the internal implementation.
[AWS S3](https://aws.amazon.com/s3/) will be used to store uploaded photographs. S3 provides cheap and safe storage of data files. 
Storage of structured data, such as metadata for the photos, will be stored in [AWS RDS](https://aws.amazon.com/rds/) with an open source database engine such as PostgreSQL, MariaDB, MySQL or similar.
Storage of documents for efficient search should be stored in [AWS Elasticsearch Service](https://aws.amazon.com/elasticsearch-service/).

TODO: make an illustration (see ppt in doc-directory)
