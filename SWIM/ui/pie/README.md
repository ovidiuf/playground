# SWIM Pie Chart

# NOKB

https://kb.novaordis.com/index.php/SWIM_UI_Pie

# Build

````
./bin/build-and-deploy
````

This will deploy the server and client runtime in ~/tmp/swim-pie

# Run

Run the SWIM server:

````
cd tmp
./swim-pie/bin/server
````

Build and start the node that servers the SWIM TypeScript library:

````
cd .../swim/typescript
npm start
````

The web server that will serve the assets will become available at http://localhost:8080/.

Load the [pie.html](./pie.html) web page in a web browser.

Update the pie state in the client:

````
cd tmp
./swim-pie/bin/client
0 10 20 30
1 40
````