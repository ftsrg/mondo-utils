#*******************************************************************************
# Copyright (c) 2010-2014, Gabor Szarnyas, Istvan Rath and Daniel Varro
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
# Gabor Szarnyas - initial API and implementation
#*******************************************************************************
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

export MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m"

if [ $skipTests ]; then
	mvn clean install -DskipTests || exit 1
else
	mvn clean install || exit 1
fi
