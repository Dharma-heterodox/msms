package com.school.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.core.entity.StudentParent;
import com.school.core.repo.StudentParentRepo;
import com.school.core.service.StudentParentService;

@Service
public class StudentParentServiceImpl implements StudentParentService {

	@Autowired
	private StudentParentRepo studentParentRepo;
	
	@Override
	public List<StudentParent> getStudentParent(Long studentId, Long parentId) {
		return studentParentRepo.findStudentParent(studentId, parentId);
	}

	@Override
	public List<StudentParent> getAllByStudentId(Long studentId) {
		return studentParentRepo.findAllByStudentId(studentId);
	}

	@Override
	public List<StudentParent> getAllByParentId(Long parentId) {
		return studentParentRepo.findAllByParentId(parentId);
	}

	@Override
	public StudentParent createStudentParent(StudentParent studentParent) {
		return studentParentRepo.save(studentParent);
	}

}
