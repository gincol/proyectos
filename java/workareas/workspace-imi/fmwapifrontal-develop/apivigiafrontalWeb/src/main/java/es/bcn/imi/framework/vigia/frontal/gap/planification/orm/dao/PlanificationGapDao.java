package es.bcn.imi.framework.vigia.frontal.gap.planification.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.PlanningGap;



public interface PlanificationGapDao extends MyBatisDaoGap {
	 
	public abstract List<PlanningGap> getPlanificationsGap(Map<String,Object> params);
}

