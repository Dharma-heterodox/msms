package com.school.core.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.school.core.dto.StudentDto;

public interface StudentService {
	StudentDto createStudent(Long schoolId, StudentDto studentDto)throws Exception;

	List<StudentDto> getAllStudentBySchoolId(Long schoolId);
	StudentDto getStudentById(Long studentId);
	List<StudentDto> getStudentsByParentId(Long parentId)throws Exception;
	StudentDto addStudent(StudentDto studentDto);
	boolean upload(Long schoolId, MultipartFile file);
	StudentDto findByAdmissionNo(Long schoolId, String admissionNo);

}
