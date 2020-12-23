package com.school.core.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.school.core.entity.Subject;
import com.school.core.repo.subject.SubjectRepoCustom;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Long>,SubjectRepoCustom {
	
	List<Subject> findAllBySchoolId(Long schoolId);
	@Query(
			  value = "SELECT subject FROM Subject subject WHERE subject.schoolId = ?1 and subject.gradeId = ?2 and subject.active = true")
	List<Subject> findAllBySchoolIdAndGradeId(Long schoolId, Long gradeId);
	@Query(
			  value = "SELECT subject FROM Subject subject WHERE subject.schoolId = ?1 and subject.subjectName = ?2 and subject.active = true")
	Subject findAllBySchoolIdAndSubject(Long schoolId, String subject);
	@Query(
			  value = "SELECT subject FROM Subject subject WHERE subject.schoolId = ?1 and subject.gradeId = ?2 and subject.subjectName = ?3 and subject.active = true")
	Subject findSubject(Long schoolId, Long gradeId, String subject);
	
	@Query(
			  value = "SELECT subject FROM Subject subject WHERE subject.gradeId = ?1 and subject.active = true")
	List<Subject> getAllByGradeId( Long gradeId);

}
