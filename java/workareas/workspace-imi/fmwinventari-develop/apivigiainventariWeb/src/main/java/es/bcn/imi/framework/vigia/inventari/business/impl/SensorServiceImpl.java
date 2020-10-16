package es.bcn.imi.framework.vigia.inventari.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.inventari.business.PhysicalModelService;
import es.bcn.imi.framework.vigia.inventari.business.SensorService;
import es.bcn.imi.framework.vigia.inventari.integration.orm.dao.SensorDao;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.SensorBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.MessageContansts;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.Sensor;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.SensorDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.SensorMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnSensorDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnSensorMassiveRDTO;
import es.bcn.vigia.fmw.libutils.convert.SensorConvert;
import es.bcn.vigia.fmw.libutils.convert.SensorDetailedConvert;
import es.bcn.vigia.fmw.libutils.convert.SensorMassiveConvert;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_SENSOR)
public class SensorServiceImpl implements SensorService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_PHYSICAL_MODEL)
	private PhysicalModelService physicalModelService;
	
	private ReturnRDTO returnRDTO;
	
	private String isCode;

	private String isMessage;


	@Override
	public ReturnRDTO insert(SensorBDTO sensorBDTO) throws ImiException {
		
		
		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();


		try {
			
			final Sensor sensor = SensorConvert.bdto2object(sensorBDTO);
			
			myBatisTemplate.execute(SensorDao.class, new MyBatisDaoCallback<Void>() {
				@Override
				public Void execute(MyBatisDao dao) {
					((SensorDao) dao).insert(sensor);
					return null;
				}
			});
			
			isCode = ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription();
			isMessage = ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage();
			
		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, sensorBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, sensorBDTO.toString()), e);
		}
		
	try {
		if(isCode.equals(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription())) {
			updateAssociationsSensor(sensorBDTO);
		} else {
				isCode = ReturnEnum.CODE_ERROR_REGISTERED.getCodeDescription();
				isMessage = ReturnEnum.CODE_ERROR_REGISTERED.getMessage();
			}

		} catch (Exception e) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, sensorBDTO), e);
			returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getCodeDescription());
			returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_FMW));
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;

	}

	@Override
	public ReturnRDTO update(SensorBDTO sensorBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();
		try {
			
			final Sensor sensor = SensorConvert.bdto2object(sensorBDTO);
			
			myBatisTemplate.execute(SensorDao.class, new MyBatisDaoCallback<Void>() {
				@Override
				public Void execute(MyBatisDao dao) {
					((SensorDao) dao).update(sensor);
					((SensorDao) dao).insert(sensor);
					return null;
				}
			});
			
			isCode = ReturnEnum.CODE_SUCCESS_UPDATED.getCodeDescription();
			isMessage = ReturnEnum.CODE_SUCCESS_UPDATED.getMessage();
			
		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, sensorBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, sensorBDTO.toString()), e);
		}

		try {
			
			if(isCode.equals(ReturnEnum.CODE_SUCCESS_UPDATED.getCodeDescription())) {
			
				updateAssociationsSensor(sensorBDTO);
			} else {

				isCode = ReturnEnum.CODE_ERROR_REGISTERED.getCodeDescription();
				isMessage = ReturnEnum.CODE_ERROR_REGISTERED.getMessage();
			}

		} catch (Exception e) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, sensorBDTO), e);
			returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_UPDATE_NOT_COMPLETE.getCodeDescription());
			returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_UPDATE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_FMW));
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
		
		
	}

	@Override
	public ReturnRDTO delete(SensorBDTO sensorBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();
		try {
			sensorBDTO.setUserUpdate(sensorBDTO.getUserDelete());
			final Sensor sensor = SensorConvert.bdto2object(sensorBDTO);
			
			
			myBatisTemplate.execute(SensorDao.class, new MyBatisDaoCallback<Void>() {
				@Override
				public Void execute(MyBatisDao dao) {
					((SensorDao) dao).update(sensor);
					((SensorDao) dao).insert(sensor);
					((SensorDao) dao).delete(sensor);
					return null;
				}
			});
			
			isCode = ReturnEnum.CODE_SUCCESS_REMOVED.getCodeDescription();
			isMessage = ReturnEnum.CODE_SUCCESS_REMOVED.getMessage();
			
		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, sensorBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, sensorBDTO.toString()), e);
		}

		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
		
		
	}

	@Override
	public List<SensorBDTO> selectByCodeMCF(Map<String, Object> map) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		List<Sensor> sensors = myBatisTemplate.execute(SensorDao.class, new MyBatisDaoCallback<List<Sensor>>() {
			@Override
			public List<Sensor> execute(MyBatisDao dao) {
				return ((SensorDao) dao).selectByMCF(map);
			}
		});

		logger.info(LogsConstants.LOG_END);	
		return SensorConvert.object2bdto(sensors);
	}

	@Override
	public List<SensorBDTO> selectByCodeRRMM(Map<String, Object> map) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		List<Sensor> sensors = myBatisTemplate.execute(SensorDao.class, new MyBatisDaoCallback<List<Sensor>>() {
			@Override
			public List<Sensor> execute(MyBatisDao dao) {
				return ((SensorDao) dao).selectByRRMM(map);
			}
		});
		logger.info(LogsConstants.LOG_END);	
		return SensorConvert.object2bdto(sensors);
	}

	@Override
	public ReturnSensorDetailedRDTO selectDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);
		
		ReturnSensorDetailedRDTO returnSensorDetailedRDTO = new ReturnSensorDetailedRDTO();
		SensorDetailedRDTO sensorDetailedRDTO = new SensorDetailedRDTO();
		
		returnRDTO = new ReturnRDTO();
		
		Map<String, Object> map;
		
		try {
			
				map = queryParameterBDTO.getMap();
				List<Sensor> sensors = myBatisTemplate.execute(SensorDao.class,
					new MyBatisDaoCallback<List<Sensor>>() {
						@Override
						public List<Sensor> execute(MyBatisDao dao) {
							return ((SensorDao) dao).getSensorsByParams(map);
						}
					}
				);
				if(sensors != null && !sensors.isEmpty()) {
					Sensor sensor = sensors.get(0);					
					sensorDetailedRDTO = SensorDetailedConvert.object2rdto(sensor);
					
					returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
					returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
				}else {
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription());
					returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
								MessageContansts.MSG_ENTITY_SENSOR, queryParameterBDTO.getCode()));
				}
				
				returnSensorDetailedRDTO.setSensorDetailedRDTO(sensorDetailedRDTO);
				returnSensorDetailedRDTO.setReturnRDTO(returnRDTO);
		} catch (Exception e) {			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		logger.info(LogsConstants.LOG_END);
		
		return returnSensorDetailedRDTO;


	}

	
	private void updateAssociationsSensor(SensorBDTO sensorBDTO) throws ImiException {

		try {
			if (!StringUtils.isEmpty(sensorBDTO.getCodeMCF()) || !StringUtils.isEmpty(sensorBDTO.getCodeRRMM()))
			{
				Map<String, Object> params = new HashMap<>();
				params.put("codeContract",sensorBDTO.getCodeContract());
				params.put("codeSensor",sensorBDTO.getCode());
				
				if (!StringUtils.isEmpty(sensorBDTO.getCodeMCF()))
				{
					params.put("codeMCF",sensorBDTO.getCodeMCF());
					
				
					myBatisTemplate.execute(SensorDao.class, new MyBatisDaoCallback<Void>() {

						@Override
						public Void execute(MyBatisDao dao) {
							((SensorDao) dao).insertSensorMCF(params);
							return null;
						}
					});
				}
				if (!StringUtils.isEmpty(sensorBDTO.getCodeRRMM()))
				{
					params.put("codeRRMM",sensorBDTO.getCodeRRMM());
					
					myBatisTemplate.execute(SensorDao.class, new MyBatisDaoCallback<Void>() {
						
						@Override
						public Void execute(MyBatisDao dao) {
							((SensorDao) dao).insertSensorRRMMM(params);
							return null;
						}
					});
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, sensorBDTO.toString()), e);
		}
	
	}

	@Override
	public ReturnSensorMassiveRDTO selectMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnSensorMassiveRDTO returnSensorMassiveRDTO = new ReturnSensorMassiveRDTO();
		List<SensorMassiveRDTO> sensorMassiveRDTOs = new ArrayList<>();
		returnRDTO = new ReturnRDTO();

		try {

			sensorMassiveRDTOs = getSensorMassive(queryParameterBDTO);

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnSensorMassiveRDTO.setReturnRDTO(returnRDTO);
		returnSensorMassiveRDTO.setSensorMassiveRDTOs(sensorMassiveRDTOs);
		logger.info(LogsConstants.LOG_END);
		return returnSensorMassiveRDTO;

	}
	

	public List<SensorMassiveRDTO> getSensorMassive(QueryParameterBDTO queryParameterBDTO)
			throws ImiException {
		List<SensorMassiveRDTO> massiveRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<Sensor> sensors = myBatisTemplate.execute(SensorDao.class,
					new MyBatisDaoCallback<List<Sensor>>() {
						@Override
						public List<Sensor> execute(MyBatisDao dao) {
							return ((SensorDao) dao).getSensorsByParams(map);
						}
					});
	
			if (sensors != null && !sensors.isEmpty()) {
				massiveRDTOs = SensorMassiveConvert.object2rdto(sensors);
	
			}
		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return massiveRDTOs;
	}
}
		
