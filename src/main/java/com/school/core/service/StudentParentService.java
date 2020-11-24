package com.school.core.service;

import java.util.List;

import com.school.core.entity.StudentParent;

public interface StudentParentService {

	List<StudentParent> getStudentParent(Long studentId, Long parentId);
	List<StudentParent> getAllByStudentId(Long studentId);
	List<StudentParent> getAllByParentId(Long parentId);
	StudentParent createStudentParent(StudentParent studentParent);
}
