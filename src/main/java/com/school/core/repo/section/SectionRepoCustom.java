package com.school.core.repo.section;

import java.util.Map;

public interface SectionRepoCustom {
	
	Map<String,Long> getGradeSectionMap(Long schoolId)throws Exception;

}
