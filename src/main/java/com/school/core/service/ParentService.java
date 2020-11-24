package com.school.core.service;

import java.util.List;

import com.school.core.dto.ParentDto;
import com.school.core.dto.StudentDto;

public interface ParentService {

	ParentDto createParent(ParentDto parentDto);

	List<ParentDto> getAllParentByStudentId(Long studentId);
	
	ParentDto addStudent(Long parnetId, StudentDto studentDto);
	ParentDto getParentByMobile(String mobile)throws Exception;
}
