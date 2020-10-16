package es.bcn.imi.framework.vigia.frontal.gap.execution.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.GroupEventGap;



public interface GroupEventGapDao extends MyBatisDaoGap {
	 
	public abstract List<GroupEventGap> getGroupEventsGap(Map<String,Object> params);
}

