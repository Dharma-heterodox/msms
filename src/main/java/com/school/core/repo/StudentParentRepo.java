package com.school.core.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.school.core.entity.StudentParent;

@Repository
public interface StudentParentRepo extends JpaRepository<StudentParent, Long> {

	@Query(
			  value = "SELECT studentParent FROM StudentParent studentParent WHERE studentParent.studentId = ?1 and studentParent.parentId = ?2")
	List<StudentParent> findStudentParent(Long studentId, Long parentId);
	List<StudentParent> findAllByStudentId(Long studentId);
	List<StudentParent> findAllByParentId(Long parentId);
	
}
