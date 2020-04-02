# Getting Started
Spring Boot + Kotlin + ElasticSearch

## Endpoints
Test Ping 
```
curl -X GET http://localhost:5000/ping
```

Create Contact
```
curl -X POST \
  http://localhost:5000/v1/contact/create/99 \
  -H 'Content-Type: application/json' \
  -d '{
    "actor": {
        "friends": {
            "user_id": [123, 234, 345]
        },
        "contact": {
            "user_id": [1111, 2222, 3333, 8888]
        }
    }
}'
```

GET Contact
```
curl -X GET \
  http://localhost:5000/v1/contact/get/{_id}
```

Create Feed
```
curl -X POST \
  http://localhost:5000/v1/feed/create-social \
  -H 'Content-Type: application/json' \
  -d '{
    "region": "BR",
    "id": 99,
    "actor": {
        "name": {
            "first": "Wagner",
            "last": "Carvalho"
        }
    },
    "verb": "Add Video"
}'
```

GET Feed
```
curl -X GET \
  http://localhost:5000/v1/feed/get/{_id}
```





