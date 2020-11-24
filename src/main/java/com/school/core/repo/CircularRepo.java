package com.school.core.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.core.entity.Circular;

@Repository
public interface CircularRepo extends JpaRepository<Circular, Long> {
	List<Circular> findAllBySchoolId(Long schoolId);
}
