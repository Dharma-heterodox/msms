package com.school.core.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.school.core.dto.SectionDto;

public interface SectionService {

	SectionDto createSection(Long schoolId, @Valid SectionDto sectionDto);

	List<SectionDto> getAllSectionBySchoolId(Long schoolId);
	List<SectionDto> findAllBySchoolIdAndGradeId(Long schoolId, Long gradeId);

	SectionDto getBySection(Long schoolId, Long gradeId, String section);
	
	Map<Long, List<SectionDto>> getAllBySchool(Long schoolId)throws Exception;
}
