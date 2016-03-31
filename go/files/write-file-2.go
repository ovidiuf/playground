
package main

import (
    "fmt"
    "io/ioutil"
)

func main() {

    fileName := "test2.txt"

    content := "this will be\nwritten into the file\n"

    bs := []byte(content)

    //
    // the function reads the file and returns a byte slice and error status
    // there is nothing to close, it will be handled internally
    //
    err := ioutil.WriteFile(fileName, bs, 0644)

    if err != nil {

        //
        // exit on error
        //
        fmt.Println("error", err)
        return
    }

    fmt.Println(fileName, "written")
}