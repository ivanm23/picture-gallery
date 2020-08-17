# picture-gallery

> A simple app for uploading and viewing photos created using Clojure and ClojureScript

generated using Luminus version `2.9.10.12`

## Prerequisites

[Leiningen][lein] 2.0 or above
[PostgreSQL][postgresql]

## Development
Database initialization:

    lein run migrate

Starting the app web server:

    lein run

Compiling ClojureScript:

    lein figwheel


The app should be available on localhost:3000, and the nREPL on localhost:7000
