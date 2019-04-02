---
title: Building
---

# Building

## Building Documentation
To build the documentation, invoke from the root folder of the project:

    make docs

This will regenerate the API documentation.

## Viewing Documentation
The documentation in the repository can be found online at
<https://virtlink.com/aesi/>.

To locally view the built documentation, you need:

- Ruby 2.1.0 or higher (`ruby --version`)
- Bundler (`gem install bundler`)

1.  Install Jekyll and the other dependencies:

        cd docs/
        bundle install

2.  Run Jekyll.

        bundle exec jekyll serve

3.  View the documentation at <http://localhost:4000/>.

