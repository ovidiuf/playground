# Consistent Hashing

# Overview

A distributed consistent hashing cluster simulated in the same address space. The client caches a view of the cluster, 
and reads and writes to the correct node. The client makes sure that it uses the correct view of the cluster for each
read and write operation by checking that its locally cached "cluster version" is consistent with the cluster version
reported by the cluster manager. If the cluster manager reports a newer version, the client updates its view before
doing anything. The update operation must be atomic, to insure that the cluster does not change again while the client
is updating its view.

Since we are interacting with the simulation on a single thread, we don't concern ourselves with concurrency safety. 
A real implementation totally should.

# Execution

```
r

> add node
> info
> write a b
> get a

```

# Command Line Help

In-line [HELP](./src/main/resources/HELP.txt).