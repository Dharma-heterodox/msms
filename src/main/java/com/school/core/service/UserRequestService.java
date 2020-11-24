package com.school.core.service;

import java.util.List;

public interface UserRequestService {
	
	int createStudentParentAcc(List<Long> ids,Long schoolId)throws Exception;

}
