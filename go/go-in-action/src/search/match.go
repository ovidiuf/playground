package search

import (
	"log"
)

// Result contains the result of a search
type Result struct {
	Field   string
	Content string
}

// Matcher defines the behavior required by types that want
// to implement a new search type
type Matcher interface {
	Search(feed *Feed, searchTerm string) ([]*Result, error)
}

// Match() will search the feed and send matches to the results channel
// Match is launched as a goroutine for each individual feed to run
// searches concurrently
func Match(matcher Matcher, feedPtr *Feed, searchTerm string, results chan<- *Result) {
	// perform the search against the specified matcher
	searchResults, err := matcher.Search(feedPtr, searchTerm)

	if err != nil {
		log.Println(err)
		return
	}

	// write the results to the channel
	for _, result := range searchResults {
		results <- result
	}
}

// Display writes results to the terminal window as they
// are received by individual goroutines
func Display(results chan *Result) {

	// The channel blocks until the result is written on the channel
	// Once the channel is closed the for loop terminates
	for result := range results {
		log.Printf("%s:\n%s\n\n", result.Field, result.Content)
	}

}
