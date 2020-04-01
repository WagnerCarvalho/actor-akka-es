#!/bin/sh

echo "********************************************************"
echo "Starting app_streaming"
echo "********************************************************"

java -Dserver.port=$SERVER_PORT \
     -jar /usr/local/social-app-streaming/social-feed-0.0.1-SNAPSHOT.jar