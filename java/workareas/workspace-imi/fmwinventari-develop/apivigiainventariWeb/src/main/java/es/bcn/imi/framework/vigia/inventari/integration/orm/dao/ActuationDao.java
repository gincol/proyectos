package es.bcn.imi.framework.vigia.inventari.integration.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.Actuation;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface ActuationDao extends MyBatisDao {

	public abstract void insert(Actuation actuation);
	
	public abstract void delete(Actuation actuation);
	
	public abstract void insertActuationElement(Actuation actuation);
	
	public abstract void deleteActuationElement(Actuation actuation);

	public abstract List<Actuation> selectActuation(Map<String, Object> map);
	
}






