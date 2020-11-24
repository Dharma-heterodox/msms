package com.school.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.school.core.dto.EmployeeDto;
import com.school.core.dto.ResponseObj;
import com.school.core.service.EmployeeService;
import com.school.core.util.Roles;

@RestController
@RequestMapping("/school/{schoolId}/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@PostMapping
	public ResponseObj createEmployee(@PathVariable("schoolId") Long schoolId,
			@RequestBody EmployeeDto employeeDto) {
		return new ResponseObj(employeeService.createEmployee(schoolId, employeeDto),HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseObj getAllEmployeeBySchoolId(@PathVariable("schoolId") Long schoolId) {
		return new ResponseObj(employeeService.getAllEmployeeBySchoolId(schoolId),HttpStatus.OK);
	}
	
	@PostMapping(value="/upload")
	public ResponseObj upload(@PathVariable("schoolId") Long schoolId, @RequestParam("file") MultipartFile file) throws Exception {
		return new ResponseObj(employeeService.upload(schoolId, file),HttpStatus.OK);
	}
//	Created By : Dharma
//	Date:10-10-2020
//	Purpose: To retrieve employee details and teacher mapped class & subject .
	@GetMapping(value="/get/{userId}")
	public ResponseObj getEmployee(@PathVariable ("userId")Long userId)throws Exception {
		EmployeeDto employee=null;
		try {
			employee=employeeService.getEmployeeByUserId(userId);
			if(employee!=null && employee.getCategory().equals(Roles.STAFF.toString()) && employee.getSubCategory()==Roles.TEACHER.intValue) 
			{
				
			}
			
		}catch(Exception ex) {
			
		}
		
		return new ResponseObj(employee,HttpStatus.OK);
	}
}
