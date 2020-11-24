package com.school.core.repo.grade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.school.core.entity.Grade;
@Repository
@Transactional(readOnly=true)
public class GradeRepoImpl implements GradeRepoCustom{
	
		@PersistenceContext
		private EntityManager em;
		@Override
	    public Map<String, Long> getGradeMap(Long schoolId)throws Exception{
	    	Map<String, Long> grades=new HashMap<String, Long>();
			TypedQuery<Object[]> query=em.createQuery("SELECT gr.grade,gr.id FROM Grade gr where gr.schoolId=:schoolId", Object[].class);
			query.setParameter("schoolId", schoolId);
			List<Object[]> results=query.getResultList();
			results.forEach(h -> {
				grades.put((String)h[0],(Long) h[1]);
			});
			return grades;
		}

}
