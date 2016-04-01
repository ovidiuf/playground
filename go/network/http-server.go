package main

import (
    "net/http"
    "io"
)

func hello(res http.ResponseWriter, req *http.Request) {

    res.Header().Set("Content-Type", "text/html")

    io.WriteString(res,
        `<DOCTYPE html>
        <html>
            <head>
                <title>Hi</title>
            </head>
            <body>
                Hi
            </body>
        </html>
        `)
}

func main() {

    http.HandleFunc("/hello", hello)
    http.ListenAndServe(":9000", nil)
}
