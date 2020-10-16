package es.bcn.imi.framework.vigia.inventari.integration.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.Sensor;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface SensorDao extends MyBatisDao {

	public abstract void insert(Sensor sensor);

	public abstract void update(Sensor sensor);

	public abstract List<Sensor> selectByMCF(Map<String, Object> map);

	public abstract List<Sensor> selectByRRMM(Map<String, Object> map);

	public abstract List<Sensor> getSensorsByParams(Map<String, Object> map);
	public abstract void insertSensorMCF(Map<String, Object> params);

	public abstract void insertSensorRRMMM(Map<String, Object> params);

	public abstract void delete(Sensor sensor);

}
