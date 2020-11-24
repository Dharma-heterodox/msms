package com.school.core.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.core.entity.Feedback;

@Repository
public interface FeedBackRepo extends JpaRepository<Feedback, Long> {

	List<Feedback> findAllBySchoolId(Long schoolId);
	List<Feedback> findAllByStudentId(Long studentId);

}
