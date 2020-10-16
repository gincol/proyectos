package es.bcn.imi.framework.vigia.frontal.inventary.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.inventary.orm.dao.CompatibilityDao;
import es.bcn.imi.framework.vigia.frontal.inventary.services.CompatibilityService;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesMCFSensor;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesMCLUbication;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesRRMMSensor;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_COMPATIBILITY)
public class CompatibilityServiceImpl implements CompatibilityService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;

	
	@Override
	public List<CompatibilityTypesMCLUbication> getCompatilibityTypeMCLUbication(String codeMCLType,
			String codeUbicationType) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		Map<String, Object> params = new HashMap<>();
		params.put("codeMCLType", codeMCLType);
		params.put("codeUbicationType", codeUbicationType);
		
		List<CompatibilityTypesMCLUbication> compatibilities = myBatisTemplate.execute(CompatibilityDao.class,
				new MyBatisDaoCallback<List<CompatibilityTypesMCLUbication>>() {
				
					@Override
					public List<CompatibilityTypesMCLUbication> execute(MyBatisDao dao) {
						return ((CompatibilityDao) dao).getCompatibilityTypesMCLUbication(params);
					}

				});

		logger.info(LogsConstants.LOG_END);
		
		return compatibilities;

	}
	
	@Override
	public List<CompatibilityTypesMCLUbication> getCompatibleTypesMCLByTypeUbication(String codeUbicationType) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("codeUbicationType", codeUbicationType);
		
		List<CompatibilityTypesMCLUbication> compatibilities = myBatisTemplate.execute(CompatibilityDao.class,
				new MyBatisDaoCallback<List<CompatibilityTypesMCLUbication>>() {
				
					@Override
					public List<CompatibilityTypesMCLUbication> execute(MyBatisDao dao) {
						return ((CompatibilityDao) dao).getCompatibilityTypesMCLUbication(params);
					}

				});

		logger.info(LogsConstants.LOG_END);
		
		return compatibilities;

	}

	@Override
	public List<CompatibilityTypesMCFSensor> getCompatibleTypesSensorByTypeMCF(String codeMCFType) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("codeMCFType", codeMCFType);
		
		List<CompatibilityTypesMCFSensor> compatibilities = myBatisTemplate.execute(CompatibilityDao.class,
				new MyBatisDaoCallback<List<CompatibilityTypesMCFSensor>>() {
				
					@Override
					public List<CompatibilityTypesMCFSensor> execute(MyBatisDao dao) {
						return ((CompatibilityDao) dao).getCompatibilityTypesSensorMCF(params);
					}

				});

		logger.info(LogsConstants.LOG_END);
		
		return compatibilities;
	}

	@Override
	public List<CompatibilityTypesRRMMSensor> getCompatibleTypesSensorByTypeRRMM(String codeMaterialResourceType) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("codeRRMMType", codeMaterialResourceType);
		
		List<CompatibilityTypesRRMMSensor> compatibilities = myBatisTemplate.execute(CompatibilityDao.class,
				new MyBatisDaoCallback<List<CompatibilityTypesRRMMSensor>>() {
				
					@Override
					public List<CompatibilityTypesRRMMSensor> execute(MyBatisDao dao) {
						return ((CompatibilityDao) dao).getCompatibilityTypesSensorRRMM(params);
					}

				});

		logger.info(LogsConstants.LOG_END);
		
		return compatibilities;
	}
	
	@Override
	public List<CompatibilityTypesMCFSensor> getCompatibilityTypeMCFTypeSensor(String codeMCFType, String codeSensorType) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("codeMCFType", codeMCFType);
		params.put("codeSensorType", codeSensorType);
		List<CompatibilityTypesMCFSensor> compatibilities = myBatisTemplate.execute(CompatibilityDao.class,
				new MyBatisDaoCallback<List<CompatibilityTypesMCFSensor>>() {
				
					@Override
					public List<CompatibilityTypesMCFSensor> execute(MyBatisDao dao) {
						return ((CompatibilityDao) dao).getCompatibilityTypesSensorMCF(params);
					}

				});

		logger.info(LogsConstants.LOG_END);
		
		return compatibilities;
	}

	@Override
	public List<CompatibilityTypesRRMMSensor> getCompatibilityTypeRRMMTypeSensor(String codeMaterialResourceType, String codeSensorType) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("codeRRMMType", codeMaterialResourceType);
		params.put("codeSensorType", codeSensorType);
		
		List<CompatibilityTypesRRMMSensor> compatibilities = myBatisTemplate.execute(CompatibilityDao.class,
				new MyBatisDaoCallback<List<CompatibilityTypesRRMMSensor>>() {
				
					@Override
					public List<CompatibilityTypesRRMMSensor> execute(MyBatisDao dao) {
						return ((CompatibilityDao) dao).getCompatibilityTypesSensorRRMM(params);
					}

				});

		logger.info(LogsConstants.LOG_END);
		
		return compatibilities;
	}
}