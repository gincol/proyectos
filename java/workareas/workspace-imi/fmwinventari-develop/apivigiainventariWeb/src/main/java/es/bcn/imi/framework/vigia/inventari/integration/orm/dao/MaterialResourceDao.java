package es.bcn.imi.framework.vigia.inventari.integration.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.MaterialResource;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.MaterialResourceVehicles;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface MaterialResourceDao extends MyBatisDao{

	public abstract void insert(MaterialResource materialResource);

	public abstract void insertSensors(MaterialResource materialResource);

	public abstract void update(MaterialResource materialResource);

	public abstract void delete(MaterialResource materialResource);

	public abstract List<MaterialResource> selectMaterialResource(Map<String, Object> map);

	public abstract List<MaterialResourceVehicles> selectMaterialResourceVehicle(Map<String, Object> map);
	
}
