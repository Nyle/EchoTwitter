#!/bin/bash
mvn assembly:assembly -DdescriptorId=jar-with-dependencies package &&
    echo "================ Uploading ================" &&
    aws lambda update-function-code --function-name Remy --zip-file fileb://target/remy-1.0-jar-with-dependencies.jar
