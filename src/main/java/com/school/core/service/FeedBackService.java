package com.school.core.service;

import java.util.List;

import com.school.core.dto.FeedBackDto;

public interface FeedBackService {

	List<FeedBackDto> getAllFeedBacksBySchoolId(Long schoolId);
	List<FeedBackDto> getAllFeedbacksByStudentId(Long schoolId, Long studentId);

	FeedBackDto createFeedBack(Long schoolId, FeedBackDto feedBacks);

}
