package com.school.core.repo.subject;

import java.util.Map;

public interface SubjectRepoCustom {
	
	Map<String,Long> getGradeSubjectMap(Long schoolId)throws Exception;
	
	Map<Long,String> getSubjectIdMap(Long schoolId)throws Exception;

}
