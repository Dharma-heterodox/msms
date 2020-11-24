package com.school.core.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.core.entity.News;

@Repository
public interface NewsRepo extends JpaRepository<News, Long> {

	List<News> findAllBySchoolId(Long schoolId);
}
