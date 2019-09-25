# Configuration

Various elements that allow experimenting with configuration.

The "a" chart is the main chart, and "b" is a dependency.


```
helm delete --purge config-example

helm dependency update ./a

helm install --debug --name config-example ./a

./clean-dependencies.sh
```
