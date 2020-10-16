package es.bcn.imi.framework.vigia.frontal.inventary.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesMCFSensor;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesMCLUbication;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesRRMMSensor;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface CompatibilityDao extends MyBatisDao {
	
	public abstract List<CompatibilityTypesMCLUbication> getCompatibilityTypesMCLUbication(Map<String,Object> params);

	public abstract List<CompatibilityTypesMCFSensor> getCompatibilityTypesSensorMCF(Map<String, Object> params);

	public abstract List<CompatibilityTypesRRMMSensor> getCompatibilityTypesSensorRRMM(Map<String, Object> params);

	
}

