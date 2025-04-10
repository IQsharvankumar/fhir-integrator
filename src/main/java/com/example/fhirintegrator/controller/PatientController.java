
package com.example.fhirintegrator.controller;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import com.example.fhirintegrator.dto.PatientDto;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Identifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private final IGenericClient fhirClient;

    @PostMapping
    public ResponseEntity<String> createPatient(@RequestBody PatientDto patientDto) {
        Patient patient = new Patient();
        patient.addName(new HumanName().setFamily(patientDto.getLastName()).addGiven(patientDto.getFirstName()));
        patient.addIdentifier(new Identifier().setValue(patientDto.getMrn()).setSystem("urn:mrn"));
        
        Patient created = fhirClient.create().resource(patient).execute();
        return ResponseEntity.ok(created.getIdElement().getIdPart());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable String id) {
        Patient patient = fhirClient.read().resource(Patient.class).withId(id).execute();
        return ResponseEntity.ok(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePatient(@PathVariable String id, @RequestBody PatientDto patientDto) {
        Patient patient = fhirClient.read().resource(Patient.class).withId(id).execute();
        patient.getNameFirstRep().setFamily(patientDto.getLastName()).addGiven(patientDto.getFirstName());
        fhirClient.update().resource(patient).execute();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        fhirClient.delete().resourceById("Patient", id).execute();
        return ResponseEntity.ok().build();
    }
}
