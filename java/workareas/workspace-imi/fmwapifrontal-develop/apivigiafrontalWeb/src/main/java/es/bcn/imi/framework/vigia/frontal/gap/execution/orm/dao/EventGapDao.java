package es.bcn.imi.framework.vigia.frontal.gap.execution.orm.dao;

import java.util.List;

import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.EventGap;



public interface EventGapDao extends MyBatisDaoGap {
	 
	public abstract List<EventGap> getEventsGap(String code);
}

