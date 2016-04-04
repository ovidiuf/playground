package matchers

import (

    "encoding/xml"
    "errors"
    "fmt"
    "log"
    "net/http"
    "regexp"
    "search"
)

type (

    // item defines the fields associated with the item tag
    // in the rss document

    item struct {
        XMLName     xml.Name `xml:"item"`
        PubDate     string   `xml:"pubDate"`
        Title       string   `xml:"title"`
        Description string   `xml:"description"`
        Link        string   `xml:"link"`
        GUID        string   `xml:"guid"`
        GeoRssPoint string   `xml:"georss:point"`
    }

    // image defines the fields associated with the image tag
    // in the rss document
    image struct {
        XMLName     xml.Name    `xml:"image"`
        URL         string      `xml:"url"`
        Title       string      `xml:"title"`
        Link        string      `xml:"link"`
    }

    // channel defines the fields associated with the channel tag
    // in the rss document
    channel struct {

        XMLName         xml.Name    `xml:"channel"`
        Title           string      `xml:"title"`
        Description     string      `xml:"description"`
        Link            string      `xml:"link"`
        PubDate         string      `xml:"pubDate"`
        LastBuildDate   string      `xml:"lastBuildDate"`
        TTL             string      `xml:"ttl"`
        Language        string      `xml:"language"`
        ManagingEditor  string      `xml:"managingEditor"`
        WebMaster       string      `xml:"webMaster"`
        Image           string      `xml:"image"`
        Item            []item      `xml:"item"`
    }

    // rssDocument defines the fields associated with the rss document
    rssDocument struct {
        XMLName xml.Name    `xml:"rss"`
        Channel  channel    `xml:"channel"`
    }
)

// rssMatcher implements the Matcher interface
type rssMatcher struct {}

// init registers the matcher with the program
func init() {

    var matcher rssMatcher
    search.Register("rss", matcher)
}

// retrieve performs a HTTP Get request for the rss feed and decodes
func (m rssMatcher) retrieve (feed *search.Feed) (*rssDocument, error) {

    if feed.URI == "" {
        return nil, errors.New("No rss feed URI provided")
    }

    // Retrieve the rss feed document from the web
    respPtr, err := http.Get(feed.URI)

    if err != nil {
        return nil, err
    }

    // Close the response once we return from the function
    defer respPtr.Body.Close()

    // check the status code for a 200 so we know we have received a proper response
    if respPtr.StatusCode != 200 {
        return nil, fmt.Errorf("HTTP Response Error %d\n", respPtr.StatusCode)
    }

    // Decode the rss feed document into our struct type
    // we don't need to check for errors, the caller can do this
    var document rssDocument
    err = xml.NewDecoder(respPtr.Body).Decode(&document)
    return &document, err
}

// Search looks at the document for the specified search term
func (m rssMatcher) Search(feed *search.Feed, searchTerm string) ([]*search.Result, error) {

    var results []*search.Result

    log.Printf("Search Feed Type[%s] Site[%s] For Uri[%s]\n", feed.Type, feed.Name, feed.URI)

    // retrieve the data to search
    document, err := m.retrieve(feed)
    if err != nil {
        return nil, err
    }

    for _, channelItem := range document.Channel.Item {

        // check the title for the search term
        matched, err := regexp.MatchString(searchTerm, channelItem.Title)

        if err != nil {
            return nil, err
        }

        // if we found a match save the result
        if matched {
            results = append(results, &search.Result {
                Field: "Title",
                Content: channelItem.Title,
            })
        }

        // check the description for the search term
        matched, err = regexp.MatchString(searchTerm, channelItem.Description)

        if err != nil {
            return nil, err
        }

        // if we found a match save the result
        if matched {
            results = append(results, &search.Result{
                Field: "Description",
                Content: channelItem.Description,
            })
        }
    }

    return results, nil
}



