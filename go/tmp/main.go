package main

import (
    "fmt"
)

func main() {

    enclosing(1, 2, 3)
}

func enclosing(args ...int) {

    for _, a := range args {
        fmt.Println(a)
    }

    enclosed(args ...)
}

func enclosed(args ...int) {

    for _, a := range args {
        fmt.Println(a)
    }

}