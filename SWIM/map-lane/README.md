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

Start the server:

````
./bin/server
````

Multiple clients can be executing in parallel.

````
./bin/client
````

Create a downlink to the map lane (note that different services maintain different map lanes):

````
> open <service-id>
````

Put a key/value pair in the map lane, assuming there is just one downlink opened:

````
> put <key> <value>
````

If there is more than one downlink opened, the service id must also be specified:

````
> put <service-id> <key> <value>
````

Close the downlink to the map lane maintained by the specified service.

````
> close <service-id>
````
