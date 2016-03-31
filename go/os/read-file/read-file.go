
package main

import (
    "fmt"
    "os"
)

func main() {

    fileName := "test.txt"

    //
    // os.Open() function returns a File pointer or an error
    //

    filePtr, err := os.Open(fileName)

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
    // os.File implements Reader.Read() so we use that to read bytes.
    // In order to read all bytes, we need to know how many bytes are
    // in the file.
    //

    fileInfoPtr, err := filePtr.Stat()

    if err != nil {

        //
        // exit on error
        //
        fmt.Println("error", err)
        return
    }

    fileSize := fileInfoPtr.Size()

    //
    // we make a slice large enough to accommodate all the bytes from the file
    //

    bs := make([]byte, fileSize)

    //
    // we do the reading
    //
    _, err = filePtr.Read(bs)

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