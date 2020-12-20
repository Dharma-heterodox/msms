package com.school.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.core.dto.GradeDto;
import com.school.core.dto.ResponseObj;
import com.school.core.service.GradeService;

@RestController
@RequestMapping("/{schoolId}/grade")
public class GradeController {

	@Autowired
	private GradeService gradeService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<GradeDto> getAllGradesBySchoolId(@PathVariable("schoolId") Long schoolId)throws Exception {
		return gradeService.getAllGradesBySchoolId(schoolId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public GradeDto saveGrade(@PathVariable("schoolId") Long schoolId, @RequestBody GradeDto gradeDto) {
		return gradeService.saveGradeBySchoolId(schoolId, gradeDto);
	}
}
