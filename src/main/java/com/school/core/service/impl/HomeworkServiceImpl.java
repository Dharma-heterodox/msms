package com.school.core.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.school.core.controller.HomewrokDoneController;
import com.school.core.dto.HomeworkDto;
import com.school.core.dto.StudentDto;
import com.school.core.dto.SubjectDto;
import com.school.core.entity.Homework;
import com.school.core.entity.Subject;
import com.school.core.entity.teacher.TeacherMapping;
import com.school.core.repo.HomeworkRepo;
import com.school.core.repo.SubjectRepo;
import com.school.core.repo.TeacherMappingRepo;
import com.school.core.service.HomeworkService;
import com.school.core.util.Constant;
import com.school.core.util.S3ServiceUtil;

@Service
public class HomeworkServiceImpl implements HomeworkService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private HomeworkRepo homeworkRepo;
	@Autowired
	private StudentServiceImpl studentService;
	@Autowired
	private TeacherMappingRepo teacherMapRepo;
	@Autowired
	private SubjectRepo subjectRepo;
	@Autowired
	private S3ServiceUtil s3Service;
	
	private Logger logger = LoggerFactory.getLogger(HomeworkServiceImpl.class);

	@Override
	public List<HomeworkDto> getAllHomeWorksBySchoolId(Long schoolId) {
		List<Homework> homeworks = homeworkRepo.findAllBySchoolId(schoolId);
		
		return homeworks.stream().map(h -> mapper.map(h, HomeworkDto.class)).collect(Collectors.toList());
	}
	
	public List<HomeworkDto> getAllHomeWorksByStudentId(Long schoolId, Long studentId) {
		StudentDto student = studentService.getStudentById(studentId);
		return getAllByGradeAndSection(schoolId, student.getGradeId(), student.getSectionId());
	}
	
	public byte[] getHomework(Long id) {
		Homework homework = homeworkRepo.getOne(id);
		return homework != null ? new byte[] {} : null;
	}

	@Override
	public HomeworkDto createHomework(Long schoolId, HomeworkDto homeworkDto, MultipartFile file) throws Exception {
		Homework homework = mapper.map(homeworkDto, Homework.class);
		homework.setSchoolId(schoolId);
////		homework.setActive(true);
//		try {
////			homework.setFile(file.getBytes());
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new Exception(e.getMessage(), e);
//		}
		homeworkRepo.save(homework);
		return mapper.map(homework, HomeworkDto.class);
	}
	
	@Override
	public List<HomeworkDto> getAllByGradeAndSection(Long schoolId, Long gradeId, Long sectionId) {
			List<Homework> homeworks = homeworkRepo.findByGradeAndSection(schoolId, gradeId, sectionId);
		return homeworks.stream().map(h -> mapper.map(h, HomeworkDto.class)).collect(Collectors.toList());
	}
	
	@Override
	public Homework getHomeWorkById(Long homeWorkId) throws Exception{
		return homeworkRepo.getOne(homeWorkId);
	}
//	Created By : Dharma
//	Date:16-10-2020
//	Purpose: To list all subject for particular grade
	@Override
	public List<SubjectDto> getAllByGradeId(Long gradeId)throws Exception{
		return getDtoFromEntity(subjectRepo.getAllByGradeId(gradeId),gradeId);
	}
//	Created By : Dharma
//	Date:16-10-2020
//	Purpose: 
	private List<SubjectDto> getDtoFromEntity(List<Subject> subject,Long gradeId){
		Map<Long,Long> mapTeacher=getTeacherAndSubject(gradeId);
		List<SubjectDto> dtoList=new ArrayList<SubjectDto>(subject.size());
		subject.stream().forEach(h -> {
			SubjectDto dto=new SubjectDto();
			dto.setActive(h.isActive());
			dto.setDescription(h.getDescription());
			dto.setGradeId(h.getGradeId());
			dto.setId(h.getId());
			dto.setSubjectName(h.getSubjectName());
			dto.setTitle(h.getTitle());
			dto.setTeacherId(mapTeacher.get(h.getId()));
			dtoList.add(dto);
		});
		return dtoList;
	}
	
	private Map<Long,Long> getTeacherAndSubject(Long gradeId) {
		List<TeacherMapping> mapped=teacherMapRepo.findTeachersSection(gradeId, Constant.currentAcademicYear);
		Map<Long,Long> mapTeacher=new HashMap<Long,Long>();
		try {
			mapped.forEach(h ->{
				mapTeacher.put(h.getSubjectId(), h.getTeacherId());
			});
		}catch(Exception ex) {
				ex.printStackTrace();
		}
		return mapTeacher;
	}
//	Created By : Dharma
//	Date:16-10-2020
//	Purpose: To save Homework(Image/PDF) in AWS S3 
	@Override
	public String putHomeworkInS3(Long teacherId,MultipartFile file)throws Exception{
		String url=null;
		try {
			logger.debug("putHomeworkInS3 service invoked : "+teacherId);
			if(file == null || file.isEmpty()) {
				throw new ValidationException("Please upload the home work file");
			}
			url=s3Service.createHWByTeachers(teacherId, file);
		}catch(Exception ex) {
			logger.debug("Exception at putHomeworkInS3 service");
			logger.error("Error at putHomeworkInS3 service : "+ex.getMessage());
			throw new Exception(ex);
		}
		return url;
		
	}
	
	private Homework getHomework(HomeworkDto dto)throws Exception{
		Homework work=new Homework();
		work.setApproved(false);;
		work.setDescription(dto.getDescription());//Actual Homework Desc
		work.setGradeId(dto.getGradeId());
		work.setHomeworkDate(dto.getHomeworkDate());
		work.setSchoolId(dto.getSchoolId());
		work.setSectionId(dto.getSectionId());
		work.setSubjectId(dto.getSubjectId());
		work.setTeacherId(dto.getTeacherId());
		work.setTitle(dto.getTitle());
		work.setFileURL(dto.getS3URL());
		work.setActive(true);
		return work;
	}
	
	@Override
	public Long saveHomework(HomeworkDto homeworkDto)throws Exception{
		try {
			logger.debug("To save homewrok for :: "+homeworkDto.getHomeworkDate()+" Sub :"+homeworkDto.getSectionId());
			Homework work=getHomework(homeworkDto);
			return homeworkRepo.save(work).getId();
		}catch(Exception ex) {
			logger.debug("Exception at saveHomework service");
			logger.error("Error at saveHomework service : "+ex.getMessage());
			ex.printStackTrace();
			throw new Exception(ex);
		}
	}
	
	@Override
	public boolean authorizeHomework(List<Long> ids,boolean auth)throws Exception{
		logger.debug("Admin "+auth+" request for ids :: "+ids.size());
		int i=homeworkRepo.authHomework(auth, ids);
		if(i<0) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<HomeworkDto> getAdminHWList(HomeworkDto homeworkDto)throws Exception{
		logger.debug("Admin HW List ::: "+homeworkDto.getHomeworkDate());
		List<HomeworkDto> dtoList=null;
		try {
			dtoList= getHWDto(homeworkRepo.getList4Section(homeworkDto.getSectionId(),false,homeworkDto.getHomeworkDate()));
		}catch(Exception ex) {
			logger.debug("Exception in getAdminHWList");
			logger.error("Exception while creating list :: "+ex.getMessage());
		}
		return dtoList;
	}
	
	
	private List<HomeworkDto> getHWDto(List<Homework> homework)throws Exception{
		List<HomeworkDto> dtoList=new ArrayList<HomeworkDto>();
			homework.forEach(h ->{
				HomeworkDto dto=new HomeworkDto();
				try {
				dto.setApproved(h.isApproved());
				dto.setDescription(h.getDescription());
				dto.setGradeId(h.getGradeId());
				dto.setHomeworkDate(h.getHomeworkDate());
				dto.setId(h.getId());
				dto.setSchoolId(h.getSchoolId());
				dto.setSectionId(h.getSectionId());
				dto.setSubjectId(h.getSubjectId());
				dto.setTeacherId(h.getTeacherId());
				dto.setTitle(h.getTitle());
				dto.setS3Download(h.getFileURL()==null ? null:s3Service.makeURL(h.getFileURL()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dtoList.add(dto);
			});
		return dtoList;
	}
	
	@Override
	public List<HomeworkDto> getHW4Student(Long sectionId,LocalDate homeworkDate)throws Exception{
		logger.debug("Service to fetch homewrok for section ::"+sectionId);
		List<HomeworkDto> dtoList=null;
		try {
			dtoList= getHWDto(homeworkRepo.getList4Section(sectionId,true,homeworkDate));
		}catch(Exception ex) {
			logger.debug("Exception in getHW4Student");
			logger.error("Exception while creating Student list :: "+ex.getMessage());
			throw new Exception(ex);
		}
		return dtoList;
	}
	
	@Override
	public List<HomeworkDto> getHome4Edit(HomeworkDto homeworkDto)throws Exception{
		logger.debug("Service to fetch homewrok for Edit ::"+homeworkDto.getTeacherId()+":"+homeworkDto.getSectionId()+":"+homeworkDto.getSubjectId());
		List<HomeworkDto> dtoList=null;
		dtoList= getHWDto(homeworkRepo.getAllByTeacher(homeworkDto.getTeacherId(), homeworkDto.getSectionId(), homeworkDto.getSubjectId(),homeworkDto.getHomeworkDate()));
		return dtoList;
	}
	
	@Override
	public int editHomework(HomeworkDto homeworkDto)throws Exception{
		logger.debug("Homework edit :: "+homeworkDto.getId());
		int i=homeworkRepo.editHomework(homeworkDto.getDescription(), homeworkDto.getId());
		return i;
	}
	
}
