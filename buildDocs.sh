#!/bin/bash

set -ex

if [[ "$OSTYPE" == "darwin"* ]]; then SEDOPTION='-i \x27\x27'; else SEDOPTION='-i'; fi;

DOCS_ROOT=build/docs

[ -d $DOCS_ROOT ] && rm -r $DOCS_ROOT
mkdir -p $DOCS_ROOT/api

# Build the docs with dokka
./gradlew dokkaHtmlMultiModule

# Copy the generated docs to our $DOCS_ROOT
cp -a build/dokka/htmlMultiModule/* $DOCS_ROOT/api

# Create a copy of our docs at our $DOCS_ROOT
cp -a docs/* $DOCS_ROOT

cp README.md $DOCS_ROOT/index.md
cp CHANGELOG.md $DOCS_ROOT/changelog.md
cp LICENSE.md $DOCS_ROOT/license.md
cp CODE_OF_CONDUCT.md $DOCS_ROOT/code-of-conduct.md

sed $SEDOPTION -e 's/LICENSE.md/license/' $DOCS_ROOT/index.md
sed $SEDOPTION -e 's/docs\/\([a-zA-Z-]*\).md/\1/' $DOCS_ROOT/index.md
