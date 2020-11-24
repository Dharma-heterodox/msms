package com.school.core.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.school.core.entity.BoardMaster;
import com.school.core.entity.MediumMaster;

@Repository
public class MasterRepoImpl implements MasterCustomRepo {


    @PersistenceContext
	private EntityManager em;
	
    @Transactional
	public BoardMaster saveBoard(BoardMaster board) {
		em.persist(board);
		return board;
	}
	
    @Transactional
	public MediumMaster saveMedium(MediumMaster medium) {
			em.persist(medium);
			return medium;
	}
    
}
