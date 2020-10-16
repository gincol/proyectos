package es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ValueListGap;

public interface ValueListGapDao extends MyBatisDaoGap {
	 
	public abstract List<ValueListGap> getValueListsGap(String type);
	
	public abstract List<String> getDomainsGap();
	
	public abstract List<ValueListGap> getDomainValuesGap(Map<String, Object> map);
	
}

