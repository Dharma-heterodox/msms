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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.school.core.dto.ContestDto;
import com.school.core.dto.ContestRegistrationDto;
import com.school.core.dto.ResponseObj;
import com.school.core.service.ContestRegistrationService;
import com.school.core.service.ContestService;

@RestController
@RequestMapping("/school/{schoolId}/contest")
@CrossOrigin
public class ContestController {

	@Autowired
	private ContestService contestService;
	@Autowired
	private ContestRegistrationService contestRegistrationService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ContestRegistrationDto> getAllContestsBySchoolId(@PathVariable("schoolId") Long schoolId) {
		return contestRegistrationService.gteAllRegistrations(schoolId);
	}

	@GetMapping(value="/grade/{grade}")
	@ResponseStatus(HttpStatus.OK)
	public List<ContestDto> getMyContest(@PathVariable("schoolId") Long schoolId,@PathVariable("grade") String grade) {
		return contestService.getMyContest(schoolId, grade);
	}
	
	@GetMapping(value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public byte[] getContest(@PathVariable("schoolId") Long schoolId, @PathVariable("id") Long id) {
		return contestService.getContest(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public ContestDto createContest(@PathVariable("schoolId") Long schoolId, @RequestParam("contest") String contest, 
			@RequestParam("file") MultipartFile file) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		ContestDto contestDto = mapper.readValue(contest, ContestDto.class);
		contestDto.setActive(true);
		return contestService.createContest(schoolId, contestDto, file);
	}
	
	@PostMapping(value = "/apply")
	@ResponseStatus(HttpStatus.OK)
	public ContestRegistrationDto applyContest(@PathVariable("schoolId") Long schoolId, @RequestBody ContestRegistrationDto contestRegistrationDto) throws Exception {
		return contestRegistrationService.createRegistration(schoolId, contestRegistrationDto);
	}
	
	@GetMapping(value = "/contesters/download")
    public ResponseEntity<InputStreamResource> downloda(@PathVariable("schoolId") Long schoolId) throws IOException {
    ByteArrayInputStream in = contestRegistrationService.download(schoolId);
    // return IOUtils.toByteArray(in);
    
    HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=contesters.xlsx");
    
     return ResponseEntity
                  .ok()
                  .headers(headers)
                  .body(new InputStreamResource(in));
    }
}
