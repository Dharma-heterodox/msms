package com.school.core.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.core.dto.ResponseObj;
import com.school.core.dto.SectionDto;
import com.school.core.service.SectionService;

@RestController
@RequestMapping("/school/{schoolId}/section")
public class SectionController {

	@Autowired
	private SectionService sectionService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SectionDto createSection(@PathVariable("schoolId") Long schoolId,
			@Valid @RequestBody SectionDto sectionDto) {
		return sectionService.createSection(schoolId, sectionDto);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<SectionDto> getAllSectionBySchoolId(@PathVariable("schoolId") Long schoolId) {
		return sectionService.getAllSectionBySchoolId(schoolId);
	}
}
