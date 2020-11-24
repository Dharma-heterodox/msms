package com.school.core.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.school.core.dto.CircularDto;

public interface CircularService {

	CircularDto createCircular(Long schoolId, CircularDto circularDto, MultipartFile file) throws Exception;

	List<CircularDto> getAllCircularBySchoolId(Long schoolId);
	byte[] getCircular(Long id);
}
