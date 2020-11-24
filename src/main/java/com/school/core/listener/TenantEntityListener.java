package com.school.core.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.security.core.context.SecurityContextHolder;

import com.school.core.context.TenantContext;
import com.school.core.entity.BaseEntity;

public class TenantEntityListener {
    @PrePersist
    @PreUpdate
    private void setTenantId(Object object) {
//        if(object instanceof BaseEntity && TenantContext.getCurrentTenant() != null){
//            ((BaseEntity) object).setTenantId(TenantContext.getCurrentTenant());
//        }
        if(object instanceof BaseEntity) {
        	//LocalDateTime now = LocalDateTime.now();
        	String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        	if(((BaseEntity) object).getCreatedBy() == null) {
        		//((BaseEntity) object).setCreatedTime(now);
        		((BaseEntity) object).setCreatedBy(userName);
        	}
        	((BaseEntity) object).setUpdatedBy(userName);
    		//((BaseEntity) object).setUpdatedTime(now);
        }
    }
}