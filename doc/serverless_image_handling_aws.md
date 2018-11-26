# Serverless image processing with AWS Lambda
 
Follow steps here: https://aws.amazon.com/answers/web-applications/serverless-image-handler/

Log into AWS Management Console and launch the template with this URL: 
https://console.aws.amazon.com/cloudformation/home?region=us-east-1#/stacks/new?stackName=ServerlessImageHandler&templateURL=https:%2F%2Fs3.amazonaws.com%2Fsolutions-reference%2Fserverless-image-handler%2Flatest%2Fserverless-image-handler.template

(Remember to select the region - eu-west-1)


Set parameters: 

* Origin S3 bucket: orcatag-dev
* Origin S3 bucket region: eu-west-1

keep default for other parameters.