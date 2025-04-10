
# FHIR Integrator

A Spring Boot application that integrates with HAPI FHIR test server.

## API Endpoints

### Create Patient
```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","mrn":"12345"}'
```

### Get Patient
```bash
curl http://localhost:8080/api/patients/{id}
```

### Update Patient
```bash
curl -X PUT http://localhost:8080/api/patients/{id} \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","mrn":"12345"}'
```

### Delete Patient
```bash
curl -X DELETE http://localhost:8080/api/patients/{id}
```
