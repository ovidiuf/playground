
package main

import (
    "fmt"
    "os"
)

func main() {

    fileName := "test1.txt"

    //
    // os.Create() function returns a File pointer or an error
    //


    filePtr, err := os.Create(fileName)

    //
    // we make sure the file will be closed, no matter what happens in this
    // function. We defer immediately so we don't forget. Even if an error
    // occurs and filePtr is nil, the runtime will handle this situation
    // gracefully
    //

    defer filePtr.Close()

    if err != nil {

        //
        // exit on error
        //
        fmt.Println("error", err)
        return
    }

    //
    // os.File implements Writer.Write() so we use that to write bytes.
    //

    fileContent := `This is what will be
written into the file
`

    bs := []byte(fileContent)

    _, err = filePtr.Write(bs)

    if err != nil {

        //
        // exit on error
        //
        fmt.Println("error", err)
        return
    }

    fmt.Println(fileName, "written")
}