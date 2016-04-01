package main

import (
    "encoding/gob"
    "fmt"
    "net"
)

func send() {

    conn, err := net.Dial("tcp", "127.0.0.1:8080")

    defer conn.Close()

    if err != nil {
        fmt.Println("error", err)
        return
    }

    //
    // send the string
    //

    msg := "ping"

    fmt.Println("sending", msg)

    err = gob.NewEncoder(conn).Encode(msg)

    if err != nil {
        fmt.Println("error", err)
    }

    fmt.Println("sent")
}

func main() {

    send();
}
