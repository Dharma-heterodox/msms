package com.school.core.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.school.core.entity.UserRequest;
import com.school.core.entity.teacher.EmployeeRequest;
import com.school.core.util.Constant;
@Repository
public interface EmployeeRequestRepo extends JpaRepository<EmployeeRequest, Long>{
	
	@Query("SELECT empReq FROM EmployeeRequest empReq where empReq.requestStatus ="+Constant.REQUEST_SUCCESS
			+" and empReq.schoolId=:schoolId ORDER BY empReq.id ASC")
	List<EmployeeRequest> getEmployeeReq(@Param("schoolId")Long schoolId);
	
	@Query("SELECT empReq FROM EmployeeRequest empReq where empReq.requestStatus ="+Constant.REQUEST_SUCCESS
			+ " and empReq.id IN (:ids) ORDER BY empReq.id ASC")
	List<EmployeeRequest> getEmployeeReq(@Param("ids")List<Long> ids);
	
	@Modifying
	@Query("UPDATE EmployeeRequest empReq SET empReq.requestStatus =:reqStatus WHERE empReq.id IN (:ids) ")
	int updateReqStatus(@Param("reqStatus")int requestedType,@Param("ids")List<Long> ids);
	
	@Modifying
	@Query("UPDATE EmployeeRequest empReq SET empReq.requestStatus =:reqStatus WHERE empReq.schoolId=:schoolId AND empReq.requestStatus ="+Constant.REQUEST_SUCCESS)
	int updateSchoolReqStatus(@Param("reqStatus")int requestedType,@Param("schoolId")Long schoolId);

}
