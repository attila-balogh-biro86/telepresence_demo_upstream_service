package com.littlebig.service.one.web;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class ClientController {

  private final RestTemplate restTemplate;

  @Value("${external.api.hostname}")
  private String externalApiHostname;

  @Autowired
  public ClientController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @GetMapping("/external/clients")
  public ResponseEntity<List<Client>> getExternalEntities() {
    String externalServiceUrl = "http://"+externalApiHostname+"/clients"; // Replace with actual URL

    ResponseEntity<Client[]> responseEntity = restTemplate.getForEntity(externalServiceUrl, Client[].class);
    Client[] externalEntities = responseEntity.getBody();

    if (externalEntities != null) {
      List<Client> entityList = Arrays.asList(externalEntities);
      return ResponseEntity.ok(entityList);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

}
