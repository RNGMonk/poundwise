# Poundwise

Poundwise is a personal finance app meant to replicate most of the functionality of the now defunct [Money Dashboard](https://www.moneydashboard.com/) app.

This app is being used to drive out features for a small opinionated web framework called Heretic and my own alternative to the Clojure standard library.

## Heretic

Heretic aims to be a small, opinionated Clojure web framework that makes it possible to rapidly prototype web applications.

This is a server side only framework which aims to deeply integrate with [Htmx](https://htmx.org/) for the kinds of interactivety expected of modern apps.

The philosophy is one of simplicity and convention over configuration and we aim to have as small a surface area as possible. Heretic is influenced by Rails, Coast on Clojure, Re-frame, GraphQL and some of the authors own experiments.

Everything in Heretic is expressed either as a `Query` (read only) or a `Command` (mutation) and as such there are only two routes in a Heretic application, namely `"/query"` and `"/command"`.

New functionality is added by extending the query or command multimethod with new actions. A router is provided to map `GET` requests to the corresponding query action, but all commands can be sent directly to `"/command"`.

By default, returning a vector from a query or command will be rendered as [Hiccup](https://github.com/weavejester/hiccup) with a 200 response, while maps are considered ring json responses whose body value will be converted to JSON.

## Std.core

This is a working name for now, but std.core aims to be a more ergonomic subset of the Clojure standard library mostly to the author's taste.

One of the goals is to make writing performant JVM code in Clojure more ergonomic, which means embracing mutation and procedural style when needed. For this I take some inspiration from [Fennel](https://fennel-lang.org/) and [Janet](https://janet-lang.org/). While this is something that Clojure actively discourages, my opinon is that it should be at least as ergonomic to write procedural code in Clojure as it is in Java and ideally more elegant.