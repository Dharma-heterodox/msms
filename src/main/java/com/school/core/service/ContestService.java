package com.school.core.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.school.core.dto.ContestDto;

public interface ContestService {
	List<ContestDto> getAllBySchoolId(Long schoolId);
	List<ContestDto> getAllBySchoolIdAndGradeId(Long schoolId, String grade);
	List<ContestDto> getMyContest(Long schoolId, String grade);
	ContestDto createContest(Long schoolId, ContestDto contestDto, MultipartFile file) throws Exception;
	byte[] getContest(Long id);
}
