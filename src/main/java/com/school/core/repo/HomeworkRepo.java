package com.school.core.repo;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.school.core.entity.Homework;

@Repository
public interface HomeworkRepo extends JpaRepository<Homework, Long> {

	List<Homework> findAllBySchoolId(Long schoolId);
	@Query(
			  value = "SELECT homework FROM Homework homework WHERE homework.schoolId = ?1 and homework.gradeId = ?2 and homework.sectionId = ?3 ")
	List<Homework> findByGradeAndSection(Long schoolId, Long gradeId, Long sectionId);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE Homework homework SET homework.approved=:auth where homework.id IN (:ids)")
	int authHomework(@Param("auth")boolean auth,@Param("ids")List<Long> ids);
	
	@Query(value="SELECT homework FROM Homework homework WHERE homework.sectionId = ?1 "
			+ " and homework.approved=?2 and homework.homeworkDate=?3")
	List<Homework> getList4Section(Long sectionId,boolean approved,LocalDate homeworkDate);
	
	@Query(value="SELECT homework FROM Homework homework WHERE homework.teacherId = ?1 and homework.sectionId = ?2 and homework.subjectId = ?3 and homework.homeworkDate=?4"
			+ " and homework.approved=false")
	List<Homework> getAllByTeacher(Long teacherId,Long sectionId,Long subjectId,LocalDate homeworkDate);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE Homework homework SET homework.description=:desc where homework.id =:ids")
	int editHomework(@Param("desc")String description,@Param("ids")Long ids);
	
}
