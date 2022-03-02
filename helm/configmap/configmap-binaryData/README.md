# Overview

A simple example that deploys a ConfigMap with `binaryData` fields.

````bash

helm install test-configmap-binaryData ./configmap-binaryData
helm delete test-configmap-binaryData

````

The binary data is represented by a gzipped file available as `data/sample.txt.gz`, which is the gzipped version of the following text file:

```text
This is
a sample
text file.
```