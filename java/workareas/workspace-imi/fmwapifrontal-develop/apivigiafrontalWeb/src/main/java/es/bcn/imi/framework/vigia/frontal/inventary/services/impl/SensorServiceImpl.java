package es.bcn.imi.framework.vigia.frontal.inventary.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.inventary.orm.dao.SensorDao;
import es.bcn.imi.framework.vigia.frontal.inventary.services.SensorService;
import es.bcn.vigia.fmw.libcommons.business.dto.SensorBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.Sensor;
import es.bcn.vigia.fmw.libutils.convert.SensorConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_SENSOR)
public class SensorServiceImpl implements SensorService {
	
	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;
	
	@Override
	public List<SensorBDTO> getSensorsContract(String codeContract) throws ImiException {
		List<Sensor> sensors = myBatisTemplate.execute(SensorDao.class,
				new MyBatisDaoCallback<List<Sensor>>() {
				
					@Override
					public List<Sensor> execute(MyBatisDao dao) {
						return ((SensorDao) dao).getSensorsContract(codeContract);
					}

				});

		
		return SensorConvert.object2bdto(sensors);
	}	
	
	@Override
	public List<SensorBDTO> getSensorsByCodes(List<String> codeSensors) throws ImiException {
		Map<String, Object> params = new HashMap<>();
		params.put("codeSensors",codeSensors);
		List<Sensor> sensors = myBatisTemplate.execute(SensorDao.class,
				new MyBatisDaoCallback<List<Sensor>>() {
				
					@Override
					public List<Sensor> execute(MyBatisDao dao) {
						return ((SensorDao) dao).getSensorsByCodes(params);
					}

				});

		
		return SensorConvert.object2bdto(sensors);
	}

	@Override
	public List<SensorBDTO> getSensorsByParams(Map<String, Object> params) throws ImiException {
		List<Sensor> sensors = myBatisTemplate.execute(SensorDao.class,
				new MyBatisDaoCallback<List<Sensor>>() {
				
					@Override
					public List<Sensor> execute(MyBatisDao dao) {
						return ((SensorDao) dao).getSensorsByParams(params);
					}

				});

		
		return SensorConvert.object2bdto(sensors);

	}
	
	@Override
	public List<SensorBDTO> getSensorsByCodeMCF(Map<String, Object> params) throws ImiException {
		List<Sensor> sensors = myBatisTemplate.execute(SensorDao.class,
				new MyBatisDaoCallback<List<Sensor>>() {
				
					@Override
					public List<Sensor> execute(MyBatisDao dao) {
						return ((SensorDao) dao).getSensorByCodeSensorCodeMCF(params);
					}

				});

		
		return SensorConvert.object2bdto(sensors);

	}
	
	@Override
	public List<SensorBDTO> getSensorsByCodeRRMM(Map<String, Object> params) throws ImiException {
		List<Sensor> sensors = myBatisTemplate.execute(SensorDao.class,
				new MyBatisDaoCallback<List<Sensor>>() {
				
					@Override
					public List<Sensor> execute(MyBatisDao dao) {
						return ((SensorDao) dao).getSensorByCodeSensorCodeRRMM(params);
					}

				});

		
		return SensorConvert.object2bdto(sensors);

	}
}