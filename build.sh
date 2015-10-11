#!/bin/bash
mvn assembly:assembly -DdescriptorId=jar-with-dependencies package &&
    echo "================ Uploading ================" &&
    aws lambda update-function-code --function-name EchoTwitter --zip-file fileb://target/twitter-1.0-jar-with-dependencies.jar
