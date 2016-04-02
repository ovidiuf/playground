
package main

import "fmt"
import "time"

func main() {

    //
    // originally, the channel is created bi-directional, but the signatures of the
    // functions that use it restrict how it can be used
    //
    channel := make(chan string)

    go sender(channel)
    go receiver(channel)

    var line string
    fmt.Scanln(&line)
}

//
// this function is syntactically restricted to only send to the channel
//
func sender(channel chan<- string) {

    for {
        channel <- "."
        time.Sleep(2 * time.Second)
    }

}

//
// this function is syntactically restricted to only receive from the channel
//
func receiver(channel <-chan string) {

    for {
        _ = <- channel
        fmt.Println("received from channel")
    }
}

