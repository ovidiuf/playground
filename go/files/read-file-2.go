
package main

import (
    "fmt"
    "io/ioutil"
)

func main() {

    fileName := "test.txt"

    //
    // the function reads the file and returns a byte slice and error status
    // there is nothing to close, it will be handled internally
    //
    bs, err := ioutil.ReadFile(fileName)

    if err != nil {

        //
        // exit on error
        //
        fmt.Println("error", err)
        return
    }

    //
    // we convert the bytes to a string
    //
    fileContent := string(bs)
    fmt.Print(fileContent)
}