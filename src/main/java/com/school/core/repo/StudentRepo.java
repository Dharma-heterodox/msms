package com.school.core.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.school.core.entity.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

	List<Student> findAllBySchoolId(Long schoolId);
	@Query(
			  value = "SELECT student FROM Student student, StudentParent studentParent WHERE student.id = studentParent.studentId and studentParent.parentId = ?1")
	List<Student> findAllStudentsByParentId(Long parentId);
	@Query(
			  value = "SELECT student FROM Student student where student.schoolId= ?1 and student.admissionNo = ?2")
	Student findByAdmissionNo(Long schoolId, String admissionNo);
	
	@Query(value="SELECT student from Student student where student.sectionId = ?1")
	List<Student> findAllBySectionId(Long sectionId);
	
	@Query(value="SELECT student FROM Student student WHERE student.id NOT IN (:ids)")
	List<Student> findStudentsHWND(@Param("ids")List<Long> studentId);
	
	@Query(value="SELECT student.studId from Student student where student.schoolId = ?1")
	Set<Integer> getAllStudentsId(Long schoolId);
}
