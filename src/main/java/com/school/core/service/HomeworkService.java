package com.school.core.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.school.core.dto.HomeworkDto;
import com.school.core.dto.SubjectDto;
import com.school.core.entity.Homework;

public interface HomeworkService {

	List<HomeworkDto> getAllHomeWorksByStudentId(Long schoolId, Long studentId);
	List<HomeworkDto> getAllHomeWorksBySchoolId(Long schoolId);
	List<HomeworkDto> getAllByGradeAndSection(Long schoolId, Long gradeId, Long sectionId);
	HomeworkDto createHomework(Long schoolId, HomeworkDto homeworkDto, MultipartFile file) throws Exception;
	byte[] getHomework(Long id);
	Homework getHomeWorkById(Long homeWorkId) throws Exception;
	List<SubjectDto> getAllByGradeId(Long gradeId)throws Exception;
	String putHomeworkInS3(Long teacherId,MultipartFile file)throws Exception;
	Long saveHomework(HomeworkDto homeworkDto)throws Exception;
	boolean authorizeHomework(List<Long> ids,boolean auth)throws Exception;
	List<HomeworkDto> getAdminHWList(HomeworkDto homeworkDto)throws Exception;
	List<HomeworkDto> getHW4Student(Long sectionId,LocalDate homeworkDate)throws Exception;
	List<HomeworkDto> getHome4Edit(HomeworkDto homeworkDto)throws Exception;
	int editHomework(HomeworkDto homeworkDto)throws Exception;

}
