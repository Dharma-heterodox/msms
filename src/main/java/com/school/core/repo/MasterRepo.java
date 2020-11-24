package com.school.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.core.entity.BoardMaster;

@Repository
public interface MasterRepo extends JpaRepository<BoardMaster, Long>, MasterCustomRepo {

	
}
