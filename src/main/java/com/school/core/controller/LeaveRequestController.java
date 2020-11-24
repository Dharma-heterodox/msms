package com.school.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.core.dto.LeaveRequestDto;
import com.school.core.dto.ResponseObj;
import com.school.core.service.LeaveRequestService;

@RestController
@RequestMapping("/{schoolId}/leaveRequest")
public class LeaveRequestController {

	@Autowired
	private LeaveRequestService leaveRequestService;

	@GetMapping
	public ResponseObj getAllLeaveRequestsBySchoolId(@PathVariable("schoolId") Long schoolId) {
		return new ResponseObj(leaveRequestService.getAllLeaveRequestsBySchoolId(schoolId),HttpStatus.OK);
	}
	
	@GetMapping(value = "/student/{studentId}")
	public ResponseObj getAllLeaveRequestsByStudentId(@PathVariable("schoolId") Long schoolId, @PathVariable("studentId") Long studentId) {
		return new ResponseObj(leaveRequestService.getAllLeaveRequestsByStudentId(studentId),HttpStatus.OK);
	}

	@PostMapping
	public ResponseObj createLeaveRequests(@PathVariable("schoolId") Long schoolId, @RequestBody LeaveRequestDto feedBack) {
		feedBack.setStatus("New");
		return new ResponseObj(leaveRequestService.createLeaveRequest(schoolId, feedBack),HttpStatus.OK);
	}

}
