# SWIM Map Lane

An example that allows experimenting with a map lane. 

It consists of:
 
* A server process that declares a plane and a service, which in turn exposes the map lane 
(ws://localhost:9000/service-example/<service-id>/map-lane-example). Note that multiple service
instances may be created, each of them holding onto its own map lane.

* An interactive client that allows execution of multiple instances, so we can interact with
the map lane concurrently. The client supports linking to the map lane, putting key/value 
pairs on the lane, asynchronously receiving updates when the lane is modified and closing the
downlink.

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
