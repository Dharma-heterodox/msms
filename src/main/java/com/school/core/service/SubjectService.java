package com.school.core.service;

import java.util.List;
import java.util.Map;

import com.school.core.dto.SubjectDto;

public interface SubjectService {

	SubjectDto createSubject(Long schoolId, SubjectDto subjectDto);

	List<SubjectDto> getAllSubjectsBySchoolId(Long schoolId);
	List<SubjectDto> findAllBySchoolIdAndGradeId(Long schoolId, Long gradeId);
	SubjectDto getSubject(Long schoolId, Long gradeId, String subject);
	Map<Long,List<SubjectDto>> getAllSubjects4Grade(Long schoolId);
}
