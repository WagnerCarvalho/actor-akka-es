#!/bin/sh

echo "********************************************************"
echo "Wait for ElasticSearch to be available"
echo "********************************************************"

while ! nc -z $ELASTIC_HOST $ELASTIC_HOST_PORT; do
  printf 'elastic is still not available. Retrying...\n'
  sleep 3
done

curl -X DELETE http://elasticsearch:9200/_all 
curl -X PUT http://elasticsearch:9200/social_feed/
curl -X PUT http://elasticsearch:9200/social_contact/
curl -X PUT http://elasticsearch:9200/social_feed/_mapping -H 'Content-Type: application/json' -d '{"dynamic":"strict","properties":{"region":{"type":"keyword"},"id":{"type":"integer"},"actor":{"properties":{"name":{"properties":{"first":{"type":"text"},"last":{"type":"text"}}}}},"verb":{"type":"keyword"}}}'
curl -X PUT http://elasticsearch:9200/social_contact/_mapping -H 'Content-Type: application/json' -d '{"dynamic":"strict","properties":{"actor":{"properties":{"contact":{"properties":{"user_id":{"type":"text"}}},"friends":{"properties":{"user_id":{"type":"text"}}}}}}}'

echo "********************************************************"
echo "Starting app"
echo "********************************************************"

java -Dspring.profiles.active=local \
     -Dserver.port=$SERVER_PORT \
     -jar /usr/local/social-app/social-0.0.1-SNAPSHOT.jar