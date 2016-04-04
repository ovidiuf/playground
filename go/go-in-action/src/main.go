
package main

import (
    "log"
    "os"
    "search"
  _ "matchers"
)


//
// init is called prior to main
//
func init() {
    // change the device for logging to stdout
    log.SetOutput(os.Stdout)
}

func main() {

    // perform the search for specified terms
    search.Run("president")
}
