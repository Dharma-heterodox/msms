package com.school.core.repo.section;

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
public class SectionRepoImpl implements SectionRepoCustom{
	
	@PersistenceContext
	private EntityManager em;
	
	 @Override
	    public Map<String,Long> getGradeSectionMap(Long schoolId)throws Exception{
	    	Map<String, Long> grSection=new HashMap<String, Long>();
			TypedQuery<Object[]> query=em.createQuery("SELECT sc.gradeId||'-'||sc.section,sc.id FROM Section sc where sc.schoolId=:schoolId", Object[].class);
			query.setParameter("schoolId", schoolId);
			List<Object[]> results=query.getResultList();
			results.forEach(h -> {
				grSection.put((String)h[0],(Long)h[1]);
			});
			return grSection;
		}

}
