package main

import (
    "fmt"
    "flag"
)


func main() {

    //
    // Define the flags
    //

    sPtr := flag.String("-s", ".", "the path vale")
    iPtr := flag.Int("i", 1, "the i value")

    flag.Parse()

    fmt.Println("flag s value: ", *sPtr)
    fmt.Println("flag i value: ", *iPtr)

    //
    // The rest of the arguments
    //

    args := flag.Args()

    for j, arg := range args {
        fmt.Println(j, arg)
    }

}
