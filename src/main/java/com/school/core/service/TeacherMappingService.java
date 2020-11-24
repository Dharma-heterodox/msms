package com.school.core.service;

import java.util.List;
import java.util.Set;

import com.school.core.dto.TeacherMappingDto;

public interface TeacherMappingService {

	TeacherMappingDto createTeacherMapping(Long schoolId, TeacherMappingDto teacherMaping);
	List<TeacherMappingDto> getAllTeacherMappingBySchoolId(Long schoolId);
	List<TeacherMappingDto> findMappedTeachers(Long schoolId, Long gradeId, Long sectionId, String acadamicYear);
	TeacherMappingDto getClassTeacher(Long schoolId, Long gradeId, Long sectionId, String academicYear);
	List<TeacherMappingDto> getAllTeacherMappingByStudentId(Long schoolId, Long studentId, String academicYear);
	Set<TeacherMappingDto> getTechMapByTeacher(Long teacherId)throws Exception;
}
