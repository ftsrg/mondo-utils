#!/bin/bash

cd "$( cd "$( dirname "$0" )" && pwd )/.."

while [ "$1" != "" ]; do
	case $1 in
		"--skipTests")
		skipTests=true
		;;
	esac
	shift
done

export MAVEN_OPTS="-Xmx512m"

if [ $skipTests ]; then
	mvn clean install -DskipTests || exit 1
else
	mvn clean install || exit 1
fi
