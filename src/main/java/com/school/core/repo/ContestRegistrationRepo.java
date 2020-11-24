package com.school.core.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.school.core.entity.ContestRegistration;

@Repository
public interface ContestRegistrationRepo extends JpaRepository<ContestRegistration, Long> {

	List<ContestRegistration> findAllBySchoolId(Long schoolId);
	@Query(
			  value = "SELECT contestRegistration FROM ContestRegistration contestRegistration where contestRegistration.schoolId= ?1 and contestRegistration.contest = ?2 and contestRegistration.contactNo = ?3")
	List<ContestRegistration> findContest(Long schoolId, String contest, String contactNo);
}
