
package main

import (
    "fmt"
    "os"
)

//
// The main function displays the content of the current directory
//
func main() {

    //
    // os.Open() works with directories too
    //

    dirPtr, err := os.Open(".")

    //
    // we make sure the file will be closed, no matter what happens in this
    // function. We defer immediately so we don't forget. Even if an error
    // occurs and filePtr is nil, the runtime will handle this situation
    // gracefully
    //

    defer dirPtr.Close()

    if err != nil {

        //
        // exit on error
        //
        fmt.Println("error", err)
        return
    }

    fileInfos, err := dirPtr.Readdir(0)

    for i, fileInfo := range fileInfos {

        name := fileInfo.Name()
        isDir := fileInfo.IsDir()

        fileOrDir := "file"
        if isDir {
            fileOrDir = "dir "
        }

        fmt.Printf("%d %s %s\n", i, fileOrDir, name);
    }
}