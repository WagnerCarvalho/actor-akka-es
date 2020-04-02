#!/bin/sh
echo "********************************************************"
echo "Generate binary files for batch app"
echo "********************************************************"

cd app && ./gradlew bootJar && cd ..

echo "********************************************************"
echo "Generate binary files for service api"
echo "********************************************************"

cd app_streaming && ./gradlew bootJar && cd ..

echo "********************************************************"
echo "Run compose docker to create environments"
echo "********************************************************"

docker-compose up