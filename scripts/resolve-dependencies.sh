#!/bin/bash

pushd . > /dev/null
cd "$( cd "$( dirname "$0" )" && pwd )"

cd ../..
git clone https://github.com/FTSRG/rdf-graph-driver.git
cd rdf-graph-driver
git pull
mvn clean install

popd > /dev/null
