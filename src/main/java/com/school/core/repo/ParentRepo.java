package com.school.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.core.entity.Parent;

@Repository
public interface ParentRepo extends JpaRepository<Parent, Long> {

	//List<Parent> findAllBySchoolId(Long schoolId);
	Parent findByMobile(String mobile);
}
