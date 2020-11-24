package com.school.core.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.school.core.entity.Employee;
import com.school.core.entity.teacher.TeacherMapping;

@Repository
public interface TeacherMappingRepo extends JpaRepository<TeacherMapping, Long> {

	List<TeacherMapping> findAllBySchoolId(Long schoolId);
	@Query(
			  value = "SELECT mapping FROM TeacherMapping mapping WHERE mapping.schoolId = ?1 and mapping.gradeId = ?2 and mapping.sectionId = ?3 and mapping.academicYear = ?4 and mapping.active = true")
	List<TeacherMapping> findMappedTeachers(Long schoolId, Long gradeId, Long sectionId, String acadamicYear);
	
	@Query(
			  value = "SELECT mapping FROM TeacherMapping mapping WHERE mapping.teacherId = ?1 and mapping.academicYear = ?2 and mapping.active = true")
	List<TeacherMapping> findMapByTeacher(Long teacherId, String acadamicYear);
	
	@Query(
			  value = "SELECT mapping FROM TeacherMapping mapping WHERE mapping.gradeId = ?1 and mapping.academicYear = ?2 and mapping.active = true")
	List<TeacherMapping> findTeachersSection(Long gradeId,String acadamicYear);
}
