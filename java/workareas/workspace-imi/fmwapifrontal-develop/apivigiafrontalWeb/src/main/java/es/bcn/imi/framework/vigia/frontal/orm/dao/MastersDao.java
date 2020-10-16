package es.bcn.imi.framework.vigia.frontal.orm.dao;

import java.util.List;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesMCFSensor;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesMCLUbication;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesRRMMSensor;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface MastersDao extends MyBatisDao {

	public abstract List<CompatibilityTypesMCLUbication> getCompatibilityMclUbication();

	public abstract List<CompatibilityTypesRRMMSensor> getCompatibilitySensorRRMM();

	public abstract List<CompatibilityTypesMCFSensor> getCompatibilitySensorMcf();
	
}

