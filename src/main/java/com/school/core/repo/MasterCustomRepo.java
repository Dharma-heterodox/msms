package com.school.core.repo;

import java.util.List;
import java.util.Map;

import com.school.core.entity.BoardMaster;
import com.school.core.entity.MediumMaster;

public interface MasterCustomRepo {

	BoardMaster saveBoard(BoardMaster board);
	MediumMaster saveMedium(MediumMaster medium);
}
