# Swagger with Vendor Extensions

# Overview

Code to explore swagger-parser and swagger-core.

# NOKB

[NOKB Swagger Concepts - Vendor Extensions](https://kb.novaordis.com/index.php/Swagger_Concepts#Vendor_Extensions).

# Build and Run

````
gradle clean build

./bin/run ./data/original.json

````
The runtime will produce a Swagger file (./with-vendor-extensions.json) that contains
Amazon API Gateway extensions.




