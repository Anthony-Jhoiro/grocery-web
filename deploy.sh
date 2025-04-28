#!/bin/bash

set -e

echo "Removing target directory"
rm -rf target/

echo "Loading environments"

echo ".env.local must contain the following variables:"
echo "MAVEN_CENTRAL_USERNAME"
echo "MAVEN_CENTRAL_PASSWORD"
echo "GPG_KEY_ID"

source .env.local

export MAVEN_CENTRAL_USERNAME="$MAVEN_CENTRAL_USERNAME"
export MAVEN_CENTRAL_PASSWORD="$MAVEN_CENTRAL_PASSWORD"
export GPG_KEY_ID="$GPG_KEY_ID"

echo "Deploying to Maven Central"
mvn -s settings.xml clean deploy
