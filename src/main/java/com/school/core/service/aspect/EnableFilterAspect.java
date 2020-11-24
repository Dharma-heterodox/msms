package com.school.core.service.aspect;

import javax.persistence.EntityManager;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.school.core.context.TenantContext;
import com.school.core.entity.BaseEntity;

//@FilterDef(name = BaseEntity.TENANT_FILTER, parameters = @ParamDef(name = BaseEntity.TENANT_ID_PARAM, type = "int"))
//@Aspect
//@Component
public class EnableFilterAspect {

//	@AfterReturning(
//            pointcut="bean(entityManagerFactory) && execution(* createEntityManager(..))",
//            returning="retVal")
//    public void getSessionAfter(JoinPoint joinPoint, Object retVal) {
//		Session session = ((EntityManager) retVal).unwrap(Session.class);
//        if(!ObjectUtils.isEmpty(TenantContext.getCurrentTenant())) { 
//        	session.enableFilter(BaseEntity.TENANT_FILTER).setParameter(BaseEntity.
//        		  TENANT_ID_PARAM, TenantContext.getCurrentTenant().intValue()).validate(); 
//    	} 
//    }
}