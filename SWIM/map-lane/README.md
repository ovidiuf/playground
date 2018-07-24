# SWIM Map Lane

An example that allows experimenting with a map lane. 

It consists of:
 
* A server process that declares a plane and a service, which in turn exposes the map lane 
(ws://localhost:9000/service-example/<service-id>/map-lane-example). Note that multiple service
instances may be created, each of them holding onto its own map lane.

* An interactive client that allows concurrent execution of multiple instances, which may interact 
with the map lane of the same service instance. The client reports asynchronous events such as
"connect", "link", "sync", "disconnect", etc. that arrive on the link. The client also allows
putting putting key/value on the map lane.

# Build

````
./bin/build-and-deploy
````

It will deploy (then update) ~/tmp/map-lane

# Run

Start the server:

````
cd ~/tmp
./map-lane/bin/server
````

Multiple clients can be executing in parallel.

````
cd ~/tmp
./map-lane/bin/client
````

Create a downlink to the map lane (note that different services maintain different map lanes). 
When the downlink is created, various callbacks are registered:
* link status reporting callback 
* map state change reporting callback

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
