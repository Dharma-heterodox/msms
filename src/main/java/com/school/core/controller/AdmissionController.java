package com.school.core.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.core.dto.AdmissionDto;
import com.school.core.dto.ResponseObj;
import com.school.core.service.AdmissionService;

@RestController
@CrossOrigin
public class AdmissionController {

	@Autowired
	private AdmissionService admissionService;

	@PostMapping("/admission")
	public ResponseObj createAdmission(@RequestBody AdmissionDto admissionDto) {
		admissionDto.setActive(true);
		return new ResponseObj(admissionService.createAdmission(admissionDto.getSchoolId(), admissionDto),HttpStatus.CREATED);
	}

	@GetMapping(value="/school/{schoolId}/admission")
	public ResponseObj getAllAdmissionBySchoolId(@PathVariable("schoolId") Long schoolId) {
		return new ResponseObj(admissionService.getAllAdmissionBySchoolId(schoolId),HttpStatus.OK);
	}
	
	@GetMapping(value = "/school/{schoolId}/admission/download")
    public ResponseEntity<InputStreamResource> downlod(@PathVariable("schoolId") Long schoolId) throws IOException {
    ByteArrayInputStream in = admissionService.download(schoolId);
    // return IOUtils.toByteArray(in);
    
    HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=admissions.xlsx");
    
     return ResponseEntity
                  .ok()
                  .headers(headers)
                  .body(new InputStreamResource(in));
    }
}
