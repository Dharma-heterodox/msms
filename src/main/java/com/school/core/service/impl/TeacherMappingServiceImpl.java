package com.school.core.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.school.core.dto.EmployeeDto;
import com.school.core.dto.StudentDto;
import com.school.core.dto.SubjectDto;
import com.school.core.dto.TeacherMappingDto;
import com.school.core.entity.teacher.EmployeeReqErrors;
import com.school.core.entity.teacher.EmployeeRequest;
import com.school.core.entity.teacher.TeacherMapping;
import com.school.core.entity.teacher.TeacherMappingRequest;
import com.school.core.repo.EmployeeRepo;
import com.school.core.repo.StudentRepo;
import com.school.core.repo.SubjectRepo;
import com.school.core.repo.TeacherMappingRepo;
import com.school.core.repo.TeacherMappingReqRepo;
import com.school.core.repo.grade.GradeRepo;
import com.school.core.repo.section.SectionRepo;
import com.school.core.service.EmployeeService;
import com.school.core.service.FileUploads;
import com.school.core.service.StudentService;
import com.school.core.service.SubjectService;
import com.school.core.service.TeacherMappingService;
import com.school.core.util.Constant;
import com.school.core.util.ErrorCodeV;

@Service
public class TeacherMappingServiceImpl implements TeacherMappingService,FileUploads {

	//private String academicYear =  "2020-2021";
	
	@Autowired
	private TeacherMappingRepo teacherMappingRepo;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Autowired
	private GradeRepo gradeRepo;
	
	@Autowired
	private SectionRepo sectionRepo;
	
	@Autowired
	private SubjectRepo subjectRepo;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private TeacherMappingReqRepo mappingRequestRepo;
	
	private static Logger logger = LoggerFactory.getLogger(TeacherMappingServiceImpl.class);
	
	@Override
	public List<TeacherMappingDto> getAllTeacherMappingBySchoolId(Long schoolId) {
		List<TeacherMapping> teacherMappings = teacherMappingRepo.findAllBySchoolId(schoolId);
		return teacherMappings.stream().map(a -> modelMapper.map(a, TeacherMappingDto.class)).collect(Collectors.toList());
	}

	public List<TeacherMappingDto> getAllTeacherMappingByStudentId(Long schoolId, Long studentId, String academicYear) {
		StudentDto student = studentService.getStudentById(studentId);
		return findMappedTeachers(schoolId, student.getGradeId(), student.getSectionId(), academicYear == null ? Constant.currentAcademicYear : academicYear);
	}

	@Override
	public TeacherMappingDto createTeacherMapping(Long schoolId, TeacherMappingDto teacherMappingDto) {
		TeacherMapping teacherMapping = modelMapper.map(teacherMappingDto, TeacherMapping.class);
		teacherMapping.setSchoolId(schoolId);
		teacherMapping.setActive(true);
		if(teacherMapping.getAcademicYear() == null)
			teacherMapping.setAcademicYear(Constant.currentAcademicYear);
		teacherMapping = teacherMappingRepo.save(teacherMapping);
		return modelMapper.map(teacherMapping, TeacherMappingDto.class);
	}
	@Override
	public List<TeacherMappingDto> findMappedTeachers(Long schoolId, Long gradeId, Long sectionId, String academicYear) {
		List<TeacherMapping> teacherMappings = teacherMappingRepo.findMappedTeachers(schoolId, gradeId, sectionId, academicYear);
		if (teacherMappings == null)
				return null;
		List<TeacherMappingDto> mappings = teacherMappings.stream().map(a -> modelMapper.map(a, TeacherMappingDto.class)).collect(Collectors.toList());
		List<TeacherMappingDto> teachers = new ArrayList<TeacherMappingDto>();
		if(!CollectionUtils.isEmpty(mappings)) {
			List<SubjectDto> subjects = subjectService.findAllBySchoolIdAndGradeId(schoolId, gradeId);
			for(TeacherMappingDto mapping : mappings) {
				for(SubjectDto subject: subjects) {
					if(mapping.getSubjectId().equals(subject.getId())) {
						mapping.setSubject(subject.getSubjectName());
						break;
					}
				}
				EmployeeDto employee = employeeService.getEmployee(mapping.getTeacherId());
				String lastName = employee.getLastName() != null ? employee.getLastName(): "";
				String teacherName = employee.getFirstName() + " " + lastName;
				mapping.setTeacherName(teacherName);
				teachers.add(mapping);
			}
		}
		return teachers;
	}
	
	public TeacherMappingDto getClassTeacher(Long schoolId, Long gradeId, Long sectionId, String academicYear) {
		List<TeacherMappingDto> teacherMappings = findMappedTeachers(schoolId, gradeId, sectionId, academicYear  == null ? Constant.currentAcademicYear : academicYear );
		TeacherMappingDto teacherMapping = null;
		if(!CollectionUtils.isEmpty(teacherMappings)) {
			for(TeacherMappingDto mapping: teacherMappings) {
				if(mapping.isClassTeacher()) {
					teacherMapping = mapping;
					break;
				}
			}
		}
		if(teacherMapping == null)
			return null;
		return modelMapper.map(teacherMapping, TeacherMappingDto.class);
				
	}
	
//	Created By : Dharma
//	Date:10-10-2020
//	Purpose: To retrieve teacher mapping by Teacher Id while login .
	@Override
	public Set<TeacherMappingDto> getTechMapByTeacher(Long teacherId)throws Exception
	{
		Set<TeacherMappingDto> teacherMappings = null;
		List<TeacherMapping> mappingEntity=null;
		try {
			logger.debug("Get Teacher Mapping by TeacherId :: "+teacherId);
			mappingEntity=teacherMappingRepo.findMapByTeacher(teacherId, Constant.currentAcademicYear);
			teacherMappings=getMappingDtoObj(mappingEntity);
		}catch(Exception ex){
			logger.error("Error in getTechMapByTeacher :: "+ex.getMessage());
			throw new Exception(ex);
		}
		return teacherMappings;
	}
	
	private Set<TeacherMappingDto> getMappingDtoObj(List<TeacherMapping> mappingList)throws Exception
	{
		Map<Long,TeacherMappingDto> map=new HashMap<Long,TeacherMappingDto>();
		Set<TeacherMappingDto> teacherMappings = new HashSet<TeacherMappingDto>();
		mappingList.stream().forEach(h -> {
			if(map.containsKey(h.getSectionId())){
				TeacherMappingDto dto=map.get(h.getSectionId());
				dto.getSubjectList().add(h.getSubjectId());
				teacherMappings.add(dto);
			}else {
				TeacherMappingDto dto=new TeacherMappingDto();
				dto.setAcademicYear(h.getAcademicYear());
				dto.setActive(true);
//				dto.setClassTeacher((h.get);
				dto.setDateFrom(h.getDateFrom());
				dto.setGradeId(h.getGradeId());
				dto.setId(h.getId());
				dto.setSectionId(h.getSectionId());
				dto.setSubjectId(h.getSubjectId());
				dto.getSubjectList().add(h.getSubjectId());
				dto.setTeacherId(h.getTeacherId());
				dto.setSection(h.getSection());
				dto.setSubject(h.getSubject());
				dto.setGrade(h.getGrade());
				map.put(h.getSectionId(), dto);
				teacherMappings.add(dto);
			}
			
		});
		return teacherMappings;
	}
	
	@Transactional
	@Override
	public int upload(Long schoolId,MultipartFile file)throws Exception{
		List<TeacherMappingRequest> requestList=readFile(schoolId, file);
		requestList=mappingRequestRepo.saveAll(requestList);
		return requestList.size(); 
	}
	
	private List<TeacherMappingRequest> readFile(Long schoolId,MultipartFile file)throws Exception{
		Workbook workbook = null;
		Map<String, Long> gradeMap = null;
		Map<String,Long> grSection=null;
		Map<String,Long> grSubject=null;
		Map<Integer,Long> employeeMap=null;
		List<TeacherMappingRequest> requestList=new ArrayList<TeacherMappingRequest>();
		try {
	        workbook = new XSSFWorkbook(file.getInputStream());
	        gradeMap = gradeRepo.getGradeMap(schoolId);
			grSection= sectionRepo.getGradeSectionMap(schoolId);
			grSubject=subjectRepo.getGradeSubjectMap(schoolId);
			employeeMap=employeeRepo.getEmployeeMap(schoolId);
	        Sheet datatypeSheet = workbook.getSheetAt(0);
	        int length = 8;
	        DataFormatter objDefaultFormat = new DataFormatter();
	        FormulaEvaluator objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
	        for(int i=1; i<length; i++) {
	        	Row row = datatypeSheet.getRow(i);
	        	if(row == null)
	        		break;
	        	TeacherMappingRequest request=new TeacherMappingRequest();
	        	for(int j=1; j<6; j++) {
	        		Cell currentCell = row.getCell(j);
	        		objFormulaEvaluator.evaluate(currentCell); // This will evaluate the cell, And any type of cell will return string value
	        	    String cellValueStr = objDefaultFormat.formatCellValue(currentCell,objFormulaEvaluator).trim();
	        	    if(!isEmpty(cellValueStr) && "END".equals(cellValueStr))
	        	    	break;
	        	    try {
	        	    	switch(j) {
		        		case 1:
		        			request.setEmployeeId(Integer.valueOf(cellValueStr));
		        			request.setTeacherId(employeeMap.get(request.getEmployeeId()));
		        			break;
		        		case 2:
		        			request.setTeacherName(cellValueStr);
		        			break;
		        		case 3:
		        			request.setGrade(cellValueStr);
		        			request.setGradeId(gradeMap.get(cellValueStr));
		        			break;
		        		case 4:
		        			request.setSection(cellValueStr);
		        			request.setSectionId(grSection.get(request.getGradeId()+"-"+cellValueStr));
		        			break;
		        		case 5:
		        			request.setSubject(cellValueStr);
		        			request.setSubjectId(grSubject.get(request.getGradeId()+"-"+cellValueStr));
		        			break;
		        		
	        			default:
	        				break;
	        		}
	        	    	request.setAcademicYear(Constant.currentAcademicYear);
	        	    	request.setRequestStatus(Constant.REQUEST_SUCCESS);
						request.setSchoolId(schoolId);
						requestList.add(request);
	        		}catch(Exception jex) {
	        			logger.error("Row :"+i+" Cell :" + j + " : "+cellValueStr+" - " + jex.getMessage());
	        		}
	            }
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
			if(workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return requestList;
	}
	
	@Override
	@Transactional
	public int approveMappings(Long schoolId,List<Long> ids)throws Exception{
		List<TeacherMappingRequest> reqList=null;
		List<TeacherMapping> mappingList=null;
		try {
			reqList=mappingRequestRepo.getPendingRequestId(ids);
			mappingList=getTeacherMapping(reqList);
			mappingList=teacherMappingRepo.saveAll(mappingList);
			mappingRequestRepo.updateRequestStatus(ids);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return reqList.size();
	}
	
	private List<TeacherMapping> getTeacherMapping(List<TeacherMappingRequest> request)throws Exception{
		List<TeacherMapping> mappingList=new ArrayList<TeacherMapping>();
		request.forEach(h ->{
			TeacherMapping mapping=new TeacherMapping();
			mapping.setAcademicYear(Constant.currentAcademicYear);
			mapping.setActive(true);
			mapping.setClassTeacher(false);
			mapping.setGrade(h.getGrade());
			mapping.setGradeId(h.getGradeId());
			mapping.setSchoolId(h.getSchoolId());
			mapping.setSection(h.getSection());
			mapping.setSectionId(h.getSectionId());
			mapping.setSubject(h.getSubject());
			mapping.setSubjectId(h.getSubjectId());
			mapping.setSource(h.getSource());
			mapping.setTeacherId(h.getTeacherId());
			mappingList.add(mapping);
		});
		return mappingList;
	}
}