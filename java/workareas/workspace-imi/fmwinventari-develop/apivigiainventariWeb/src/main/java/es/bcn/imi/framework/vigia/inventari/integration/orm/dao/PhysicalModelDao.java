package es.bcn.imi.framework.vigia.inventari.integration.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.PhysicalModel;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface PhysicalModelDao extends MyBatisDao{

	public abstract void insert(PhysicalModel physicalModel);

	public abstract void insertSensors(PhysicalModel physicalModel);

	public abstract void update(PhysicalModel physicalModel);

	public abstract void delete(PhysicalModel physicalModel);
	
	public abstract PhysicalModel select(PhysicalModel physicalModel);

	public abstract List<PhysicalModel> selectPhysicalModel(Map<String, Object> map);
}
