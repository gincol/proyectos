package es.bcn.imi.framework.vigia.frontal.inventary.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.Sensor;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface SensorDao extends MyBatisDao {
	
	public abstract List<Sensor> getSensorsContract(String codeContract);

	public abstract List<Sensor> getSensorsByCodes(Map<String, Object> params);

	public abstract List<Sensor> getSensorsByParams(Map<String, Object> params);
	
	public abstract List<Sensor> getSensorByCodeSensorCodeMCF(Map<String, Object> params);  
	
	public abstract List<Sensor> getSensorByCodeSensorCodeRRMM(Map<String, Object> params);
}

