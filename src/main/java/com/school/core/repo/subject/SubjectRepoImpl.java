package com.school.core.repo.subject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly=true)
public class SubjectRepoImpl implements SubjectRepoCustom{
	
	@PersistenceContext
	private EntityManager em;
	
	 @Override
	    public Map<String,Long> getGradeSubjectMap(Long schoolId)throws Exception{
	    	Map<String, Long> grSection=new HashMap<String, Long>();
			TypedQuery<Object[]> query=em.createQuery("SELECT sc.gradeId||'-'||sc.subjectName,sc.id FROM Subject sc where sc.schoolId=:schoolId", Object[].class);
			query.setParameter("schoolId", schoolId);
			List<Object[]> results=query.getResultList();
			results.forEach(h -> {
				grSection.put((String)h[0],(Long)h[1]);
			});
			return grSection;
		}
	 
	 @Override
	    public Map<Long,String> getSubjectIdMap(Long schoolId)throws Exception{
	    	Map<Long, String> grSection=new HashMap<Long, String>();
			TypedQuery<Object[]> query=em.createQuery("SELECT sc.id,sc.subjectName FROM Subject sc where sc.schoolId=:schoolId", Object[].class);
			query.setParameter("schoolId", schoolId);
			List<Object[]> results=query.getResultList();
			results.forEach(h -> {
				grSection.put((Long)h[0],(String)h[1]);
			});
			return grSection;
		}

}
