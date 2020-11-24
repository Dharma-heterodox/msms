package com.school.core.service;

import com.school.core.entity.BoardMaster;
import com.school.core.entity.MediumMaster;

public interface MasterService {

	BoardMaster saveBoard(BoardMaster board);
	MediumMaster saveMedium(MediumMaster medium);
}
