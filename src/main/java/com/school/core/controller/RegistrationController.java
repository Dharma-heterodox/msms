package com.school.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.core.dto.ParentDto;
import com.school.core.dto.ResponseObj;
import com.school.core.service.ParentService;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
	
	@Autowired
	private ParentService parentService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value="/parent")
	public ResponseObj createParent(@RequestBody ParentDto parentDto) {
		return new ResponseObj(parentService.createParent(parentDto),HttpStatus.OK);
	}

}
