package com.school.core.repo;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.school.core.entity.HomeworkDone;

@Repository
public interface HomeworkDoneRepo extends JpaRepository<HomeworkDone, Long>{
	
	
	/* Swami */
	
	@Query(value="SELECT homeworkdone.fileURL from HomeworkDone homeworkdone WHERE homeworkdone.id = ?1 ")
	String findOneByHomeworkDoneId(Long homeworkDoneId);
	
	@Query(value="SELECT homeworkdone from HomeworkDone homeworkdone WHERE homeworkdone.teacherId= ?1 and homeworkdone.subjectId= ?2 and homeworkdone.sectionId= ?3 "
			+ " and homeworkdone.homeworkDate=?4 ORDER BY homeworkdone.id ASC")
	List<HomeworkDone> findAllForTeacher(long teacherid,long subjectId,long sectionId,LocalDate homeworkDate);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE HomeworkDone hwd SET hwd.rating = ?1 , hwd.verified = true where hwd.id = ?2")
	int verifyHomewrokDone(String command,Long homeworkDoneId);
	
	
	@Query(value="SELECT homeworkDone from HomeworkDone homeworkDone WHERE homeworkDone.sectionId= ?1 and homeworkDone.subjectId= ?2 and "
			+ "homeworkDone.homeworkDate= ?3 ")
	List<HomeworkDone> getAllBySectionId(long sectionId,long subjectId,LocalDate homeworkDate);

}
