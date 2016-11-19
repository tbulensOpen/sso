package main

import (
        "fmt"
	"html"
	"log"
	"net/http"
)

func main() {

http.HandleFunc("/sso", func(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hello, %q", html.EscapeString(r.URL.Path))
	
    fmt.Println("AAA " + r.URL.Path)
	fmt.Println("BBB " + r.URL.Host)
})

log.Fatal(http.ListenAndServe(":8080", nil))

}