package com.school.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.core.entity.BoardMaster;
import com.school.core.entity.MediumMaster;
import com.school.core.repo.MasterRepo;
import com.school.core.service.MasterService;

@Service
public class MasterServiceImpl implements MasterService {

	@Autowired
	private MasterRepo masterRepo;
	
	public BoardMaster saveBoard(BoardMaster board) {
		return masterRepo.saveBoard(board);
	}
	
	public MediumMaster saveMedium(MediumMaster medium) {
			return masterRepo.saveMedium(medium);
	}
}
