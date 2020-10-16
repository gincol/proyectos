package es.bcn.imi.framework.vigia.frontal.inventary.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.ValueList;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface ValueListDao extends MyBatisDao {
	
	public abstract List<ValueList> getValueLists(String type);
	
	public abstract List<String> getDomains();
	
	public abstract List<ValueList> getDomainValues(Map<String, Object> map);
	
}

