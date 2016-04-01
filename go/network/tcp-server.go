package main

import (
    "encoding/gob"
    "fmt"
    "net"
)

func server() {

    //
    // Listen to a port
    //

    listener, err := net.Listen("tcp", ":8080")

    fmt.Println("listener created")

    defer listener.Close()

    if (err != nil) {
        fmt.Println("error", err)
        return;
    }

    for {

        //
        // Accept a connection
        //

        fmt.Println("listening for a connection ...")

        conn, err := listener.Accept()

        if (err != nil) {
            fmt.Println("error", err)
            continue
        }

        fmt.Println("connection established")

        //
        // Handle the connection on a different thread
        //

        go handleConnection(conn)
    }
}

func handleConnection(conn net.Conn) {

        defer conn.Close()

        var msg string

        err := gob.NewDecoder(conn).Decode(&msg)

        if err != nil {
            fmt.Println("error", err)
            return
        }

        fmt.Println("received", msg)
}

func main() {

    go server()

    //
    // wait for a key to finish
    //
    var input string
    fmt.Scanln(&input)
}
