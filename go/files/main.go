
package main

import (
    "fmt"
    "errors"
)

func append(i int, s string) string {
    bs := make([] byte, len(s) + 1)
    bs[0] = byte(i)
    copy(bs[1:], []byte(s))
    return string(bs)
}

func main() {

    err := errors.New("something")

    fmt.Println(append(101, "blah"))

    //bs := []int {1, 2, 3}
    //
    //for _, value := range bs {
    //    process(value, func (i int, s string) string {
    //        bs := new([] byte, len(s) + 1)
    //        bs[0] = byte(i)
    //        copy(bs[1:], []byte(s))
    //        return string(bs)
    //    })
    //}
}

//func process(i int, f func(int)) {
//    f(i)
//}