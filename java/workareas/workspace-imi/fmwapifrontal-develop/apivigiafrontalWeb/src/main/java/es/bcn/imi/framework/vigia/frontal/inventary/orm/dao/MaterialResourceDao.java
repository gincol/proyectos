package es.bcn.imi.framework.vigia.frontal.inventary.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.MaterialResource;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface MaterialResourceDao extends MyBatisDao{

	public abstract List<MaterialResource> selectNoVehiclesByCode(Map<String, Object>  params);
	
	
}
