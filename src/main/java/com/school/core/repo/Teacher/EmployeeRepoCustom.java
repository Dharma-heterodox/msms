package com.school.core.repo.Teacher;

import java.util.Map;

public interface EmployeeRepoCustom {
	
	Map<Integer,Long> getEmployeeMap(Long schoolId)throws Exception;

}
