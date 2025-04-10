
package com.example.fhirintegrator.controller;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import com.example.fhirintegrator.dto.PatientDto;
import org.hl7.fhir.r4.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IGenericClient fhirClient;

    @Test
    public void createPatientTest() throws Exception {
        MethodOutcome outcome = new MethodOutcome();
        Patient patient = new Patient();
        patient.setId("test-id");
        outcome.setResource(patient);
        
        when(fhirClient.create().resource(any(Patient.class)).execute())
            .thenReturn(outcome);

        mockMvc.perform(post("/api/patients")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"mrn\":\"12345\"}"))
            .andExpect(status().isOk());
    }
}
