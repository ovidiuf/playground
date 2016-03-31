
package main

import (
    "fmt"
    "path/filepath"
    "os"
)

//
// The main function recursively walks the current directory
//
func main() {

    err := filepath.Walk(".", func(path string, info os.FileInfo, err error) error {

        fmt.Println(path)
        return nil
    })

    if err != nil {
        fmt.Println("error", err)
        return
    }

}