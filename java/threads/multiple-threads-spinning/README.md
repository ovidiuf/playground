#Java Threads

A java program that maintains concurrent threads spinning.

See https://kb.novaordis.com/index.php/Java_Threads#Java_Threads_and_Linux_Processes

#Usage

````
    java -jar .../threads.jar --threads=100
````

where:

##--threads=

specifies the number of concurrent threads to keep running until Ctrl-C. If not specified
the default is 1 (plus the main thread that will block).


