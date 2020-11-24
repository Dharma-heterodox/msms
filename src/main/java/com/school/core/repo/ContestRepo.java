package com.school.core.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.school.core.entity.Contest;

public interface ContestRepo extends JpaRepository<Contest, Long> {

	List<Contest> findAllBySchoolId(Long schoolId);
	
	@Query(
			  value = "SELECT contest FROM Contest contest WHERE contest.schoolId = ?1 and contest.grade != ?2 and contest.active = true")
	List<Contest> findAllBySchoolIdAndGradeId(Long schoolId, String grade);
	
	@Query(
			  value = "SELECT contest FROM Contest contest WHERE contest.schoolId = ?1 and (contest.grade is null or contest.grade != ?2) and contest.active = true")
	List<Contest> findMyContest(Long schoolId, String grade);
}
