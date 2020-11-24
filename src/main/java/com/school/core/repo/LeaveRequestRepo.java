package com.school.core.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.core.entity.LeaveRequest;

@Repository
public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, Long> {

	List<LeaveRequest> findAllBySchoolId(Long schoolId);
	
	List<LeaveRequest> findAllByStudentId(Long studentId);

}
