
package main

import (
    "fmt"
    "sync"
    "time"
    "math/rand"
)

//
// An example of using a WaitGroup to delay main() exit until all concurrent goroutines
// finish their work
//
func main() {

    //
    // create the wait group
    //
    var waitGroup sync.WaitGroup

    concurrentGoroutineCount := 10

    waitGroup.Add(concurrentGoroutineCount)

    //
    // launch the goroutines
    //
    for i := 0; i < concurrentGoroutineCount; i ++  {

        go func(i int) {

            //
            // sleep for a random amount of seconds, then report that I am exiting, and exit
            //

            var sleepSeconds int
            sleepSeconds = rand.Intn(21)

            var sleepDuration time.Duration
            sleepDuration = time.Duration(sleepSeconds * int(time.Second))
            time.Sleep(sleepDuration)

            fmt.Printf("goroutine %d is exiting ...\n", i)
            waitGroup.Done()
        }(i)
    }

    //
    // wait for all goroutines to exit
    //
    waitGroup.Wait()
    fmt.Println("main() exiting ...")
}

