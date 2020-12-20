package com.school.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.school.core.dto.CircularDto;
import com.school.core.dto.ResponseObj;
import com.school.core.service.CircularService;

@RestController
@RequestMapping("/school/{schoolId}/circular")
public class CircularController {

	@Autowired
	CircularService circularService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public CircularDto createCircular(@PathVariable("schoolId") Long schoolId,
			@RequestParam("circular") String circular, @RequestParam("file") MultipartFile file) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		CircularDto circularDto = mapper.readValue(circular, CircularDto.class);
		return circularService.createCircular(schoolId, circularDto, file);
	}
	
	@GetMapping(value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public byte[] getCircular(@PathVariable("schoolId") Long schoolId, @PathVariable("id") Long id) {
		return circularService.getCircular(id);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<CircularDto> getAllCircularBySchoolId(@PathVariable("schoolId") Long schoolId) {
		return circularService.getAllCircularBySchoolId(schoolId);
	}
}
