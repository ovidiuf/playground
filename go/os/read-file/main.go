
package main

import (
    "fmt"
    "os"
)

func main() {

    fileName := "test.txt"

    //
    // we use the os Open function that returns a File pointer or an error
    //

    fileptr, err := os.Open(fileName)

    //
    // we make sure the file is closed, no matter of what happens in this
    // function. We defer immediately so we don't forget. Even if an error
    // occurs and fileptr is nil, the runtime will handle this situation
    // gracefully
    //

    defer fileptr.Close()

    if err != nil {
        fmt.Println("error", err)
        return
    }

    //
    // File implements Reader's Read() so we use that to read bytes
    //

    fileInfoPtr, err := fileptr.Stat()

    if err != nil {
        fmt.Println("error", err)
        return
    }

    fileSize := fileInfoPtr.Size()

    bs := make([]byte, fileSize)
    _, err = fileptr.Read(bs)

    if err != nil {
        fmt.Println("error", err)
        return
    }

    fileContent := string(bs)
    fmt.Print(fileContent)
}
