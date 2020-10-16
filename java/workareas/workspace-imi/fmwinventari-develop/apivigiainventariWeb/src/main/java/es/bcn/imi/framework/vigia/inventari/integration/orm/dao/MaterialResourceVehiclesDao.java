package es.bcn.imi.framework.vigia.inventari.integration.orm.dao;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.MaterialResourceVehicles;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface MaterialResourceVehiclesDao extends MyBatisDao{
	
	public abstract void insert(MaterialResourceVehicles materialResourceVehicles);
	
	public abstract void update(MaterialResourceVehicles materialResourceVehicles);
	
	public abstract void delete(MaterialResourceVehicles materialResourceVehicles);

	public abstract MaterialResourceVehicles select(MaterialResourceVehicles materialResourceVehicles);

}
