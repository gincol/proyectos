package es.bcn.imi.framework.vigia.frontal.gap.execution.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ServiceGap;



public interface ServiceGapDao extends MyBatisDaoGap {
	 
	public abstract List<ServiceGap> getServicesGap(Map<String,Object> params);
}

