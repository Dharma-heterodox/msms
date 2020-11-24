package com.school.core.repo.grade;

import java.util.Map;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.school.core.entity.Grade;


public interface GradeRepoCustom {
	
	Map<String, Long> getGradeMap(Long schoolId)throws Exception;

}
