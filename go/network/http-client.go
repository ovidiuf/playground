
package main

import (
    "net/http"
    "io/ioutil"
    "fmt"
)

func main() {

    res, err := http.Get("http://cnn.com")

    if err != nil {
        panic(err)
    }

    defer res.Body.Close()

    bs, err := ioutil.ReadAll(res.Body)

    if err != nil {
        panic(err)
    }

    fmt.Println(string(bs))
}