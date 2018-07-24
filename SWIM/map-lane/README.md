# SWIM Map Lane

An example that allows experimenting with a map lane. 

It consists of:
 
* A server process that declares a plane and a service which exposes the map lane 
(ws://localhost:9000/service-example/<service-id>/map-lane-example)
* An interactive client that allows execution of multiple instances and supports linking
to a map lane, asynchronously receiving updates on the lane and sending updates to the 
lane.

# Build

````
./bin/build-and-deploy
````

It will deploy (then update) ~/tmp/map-lane

# Run


````
./bin/server
````

Multiple clients can be executing in parallel.

````
./bin/client
````

Create a downlink to the map lane (note that different services maintain different map lanes):

> open <service-id>

Put a key/value pair in the map


> put <key> <value>
>
````
