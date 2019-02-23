# swagger-parser and swagger-core Example

# Overview

Code to explore swagger-parser and swagger-core.

# NOKB

NOKB: [NOKB Swagger API Tools](https://kb.novaordis.com/index.php/Swagger_API_Tools#Swagger_Parser_Playground_Example).

# Build and Run

````
gradle clean build

./bin/run ./data/original.json

````
The runtime will produce a Swagger file (./with-vendor-extensions.json) that contains
Amazon API Gateway extensions.




