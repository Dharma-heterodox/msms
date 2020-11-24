package com.school.core.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.school.core.dto.EmployeeDto;

public interface EmployeeService {

	EmployeeDto createEmployee(Long schoolId, EmployeeDto employee);
	List<EmployeeDto> getAllEmployeeBySchoolId(Long schoolId);
	EmployeeDto getEmployee(Long employeeId);
	boolean upload(Long schoolId, MultipartFile file);
	EmployeeDto getEmployeeByUserId(Long userId)throws Exception;
}
