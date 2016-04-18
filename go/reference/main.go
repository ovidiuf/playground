package main

import "fmt"

type something int

func (s *something) m() {
    fmt.Println(*s)
}

func main() {

    //
    // this generates:
    //
    // ./main.go:13: cannot call pointer method on something(10)
    // ./main.go:13: cannot take the address of something(10)
    //
    something(10).m()

}


