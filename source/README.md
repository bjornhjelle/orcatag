Tips: 

https://medium.com/oril/uploading-files-to-aws-s3-bucket-using-spring-boot-483fcb6f8646

https://www.callicoder.com/spring-boot-jpa-hibernate-postgresql-restful-crud-api-example/

https://stackoverflow.com/questions/38578937/spring-boot-amazon-aws-s3-bucket-file-download-access-denied



## Access to S3 

To allow the service access to S3, create an IAM account with proper access and set in the environment: 

export AWS_ACCESS_KEY_ID="<AWS_ACCESS_KEY_ID>"
export AWS_SECRET_ACCESS_KEY="<AWS_SECRET_ACCESS_KEY>"

## PostgreSQL in docker-compose


    $ docker-compose -f rest-service/docker-compose/postgres.yml up 

### Log in to psql

    $ docker exec -it a271dab0eae0 /bin/bash
    $ su - postgres
    $ psql
    psql (11.1 (Debian 11.1-1.pgdg90+1))
    Type "help" for help.
          
    postgres=# 


asdasd   

