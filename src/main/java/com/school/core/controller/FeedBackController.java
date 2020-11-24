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

import com.school.core.dto.FeedBackDto;
import com.school.core.dto.ResponseObj;
import com.school.core.service.FeedBackService;

@RestController
@RequestMapping("/school/{schoolId}/feedback")
public class FeedBackController {

	@Autowired
	private FeedBackService feedbackService;

	@GetMapping
	public List<FeedBackDto> getAllFeedBacksBySchoolId(@PathVariable("schoolId") Long schoolId) {
		return feedbackService.getAllFeedBacksBySchoolId(schoolId);
	}
	
	@GetMapping(value="/student/{studentId}")
	public List<FeedBackDto> getAllFeedbacksByStudentId(@PathVariable("schoolId") Long schoolId, @PathVariable("studentId") Long studentId) {
		return feedbackService.getAllFeedbacksByStudentId(schoolId, studentId);
	}

	@PostMapping
	public ResponseObj createFeedBacks(@PathVariable("schoolId") Long schoolId, @RequestBody FeedBackDto feedBack) {
		feedBack.setStatus("New");
		return new ResponseObj(feedbackService.createFeedBack(schoolId, feedBack),HttpStatus.OK);
	}

}
