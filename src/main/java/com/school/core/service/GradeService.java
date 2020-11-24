package com.school.core.service;

import java.util.List;
import java.util.Map;

import com.school.core.dto.GradeDto;
import com.school.core.entity.Grade;

public interface GradeService {

	List<GradeDto> getAllGradesBySchoolId(Long schoolId)throws Exception ;
	GradeDto getByGrade(Long schoolId, String grade);
	GradeDto saveGradeBySchoolId(Long schoolId, GradeDto gradeDto);
	Map<String,Long> getAllGradesBySchool(Long schoolId)throws Exception;

}
