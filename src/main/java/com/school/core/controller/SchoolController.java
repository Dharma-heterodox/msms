package com.school.core.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.school.core.dto.ResponseObj;
import com.school.core.entity.Employee;
import com.school.core.entity.SchoolBoard;
import com.school.core.service.GradeService;
import com.school.core.service.SchoolService;
import com.school.core.service.SectionService;
import com.school.core.service.SubjectService;

@RestController
@RequestMapping("/school/{schoolId}")
public class SchoolController {

	@Autowired
	private SchoolService schoolService;

	@RequestMapping(value = "/teacher", method = RequestMethod.POST)
	public ResponseObj creaTeacher(@PathVariable("schoolId") String schoolId, @Valid @RequestBody Employee employee,
			BindingResult bindingResult) {
		employee.setCategory("teacher");
		employee.setSchoolId(Long.parseLong(schoolId));
		employee.setActive(true);
		return new ResponseObj(schoolService.saveEmployeeDetails(employee),HttpStatus.OK);
	}

	@RequestMapping(value = "/board", method = RequestMethod.POST)
	public ResponseObj addGrade(@PathVariable("schoolId") String schoolId, @Valid @RequestBody SchoolBoard board,
			BindingResult bindingResult) {
		board.setSchoolId(Long.parseLong(schoolId));
		board.setActive(true);
		return new ResponseObj(schoolService.addSchoolBoard(board),HttpStatus.OK);
	}
}
