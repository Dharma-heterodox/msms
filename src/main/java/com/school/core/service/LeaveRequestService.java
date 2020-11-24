package com.school.core.service;

import java.util.List;

import com.school.core.dto.LeaveRequestDto;

public interface LeaveRequestService {

	List<LeaveRequestDto> getAllLeaveRequestsBySchoolId(Long schoolId);

	LeaveRequestDto createLeaveRequest(Long schoolId, LeaveRequestDto leaveRequests);
	
	List<LeaveRequestDto> getAllLeaveRequestsByStudentId(Long studentId);
}
