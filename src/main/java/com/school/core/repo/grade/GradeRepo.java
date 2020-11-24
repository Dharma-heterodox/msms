package com.school.core.repo.grade;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.school.core.entity.Grade;

@Repository
public interface GradeRepo extends JpaRepository<Grade, Integer>,GradeRepoCustom {
	

	@Query(
			  value = "SELECT grade FROM Grade grade WHERE grade.schoolId = ?1 and grade.grade = ?2 and grade.active = true")
	Grade findAllBySchoolIdAndGrade(Long schoolId, String grade);
	
	List<Grade> findAllBySchoolId(Long schoolId);
	
	
}
