package es.bcn.imi.framework.vigia.frontal.inventary.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.Actuation;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface ActuationDao extends MyBatisDao {
	
	public abstract List<Actuation> getActuations(Map<String, Object> params);
}

