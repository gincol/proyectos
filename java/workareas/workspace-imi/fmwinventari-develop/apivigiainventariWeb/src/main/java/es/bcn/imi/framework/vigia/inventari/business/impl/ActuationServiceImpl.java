package es.bcn.imi.framework.vigia.inventari.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.inventari.business.ActuationService;
import es.bcn.imi.framework.vigia.inventari.integration.orm.dao.ActuationDao;
import es.bcn.vigia.fmw.libcommons.business.dto.ActuationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterActuationBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.Actuation;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.ActuationGetRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.convert.ActuationConvert;
import es.bcn.vigia.fmw.libutils.convert.ActuationGetConvert;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_ACTUATION)
public class ActuationServiceImpl implements ActuationService {

	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;
	
	
	private ReturnRDTO returnRDTO;
	
	private String isCode;

	private String isMessage;


	@Override
	public ReturnRDTO insert(ActuationBDTO actuationBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		final Actuation actuation = ActuationConvert.bdto2object(actuationBDTO);
		try {
			
			myBatisTemplate.execute(ActuationDao.class, new MyBatisDaoCallback<Void>() {

				@Override
				public Void execute(MyBatisDao dao) {
					((ActuationDao) dao).insert(actuation);
					return null;
				}
			});
			
			isCode = ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription();
			isMessage = ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage();
			
		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, actuationBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, actuationBDTO.toString()), e);
		}
		
		try {
			
			if(isCode.equals(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription())) {
				 
				myBatisTemplate.execute(ActuationDao.class, new MyBatisDaoCallback<Void>() {

					@Override
					public Void execute(MyBatisDao dao) {
						((ActuationDao) dao).insertActuationElement(actuation);
						return null;
					}
				});

			} else {

				isCode = ReturnEnum.CODE_ERROR_REGISTERED.getCodeDescription();
				isMessage = ReturnEnum.CODE_ERROR_REGISTERED.getMessage();
			}

		} catch (Exception e) {


			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, actuationBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, actuationBDTO.toString()),
					e);
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}
	
	
	@Override
	public ReturnRDTO delete(ActuationBDTO actuationBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		final Actuation actuation = ActuationConvert.bdto2object(actuationBDTO);
		
		try {
		
			myBatisTemplate.execute(ActuationDao.class, new MyBatisDaoCallback<Void>() {

				@Override
				public Void execute(MyBatisDao dao) {
					((ActuationDao) dao).delete(actuation);
					return null;
				}
			});
			
			isCode = ReturnEnum.CODE_SUCCESS_REMOVED.getCodeDescription();
			isMessage = ReturnEnum.CODE_SUCCESS_REMOVED.getMessage();
			
		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, actuationBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, actuationBDTO.toString()), e);
		}
		try {
			
			if(isCode.equals(ReturnEnum.CODE_SUCCESS_REMOVED.getCodeDescription())) {
				 
				
				myBatisTemplate.execute(ActuationDao.class, new MyBatisDaoCallback<Void>() {

					@Override
					public Void execute(MyBatisDao dao) {
						((ActuationDao) dao).deleteActuationElement(actuation);
						return null;
					}
				});
					
					
				
					
			} else {

				isCode = ReturnEnum.CODE_ERROR_REMOVED.getCodeDescription();
				isMessage = ReturnEnum.CODE_ERROR_REMOVED.getMessage();
			}
			

		} catch (Exception e) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, actuationBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, actuationBDTO.toString()),
					e);
		}
		
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;				
	}

	@Override
	public ReturnActuationRDTO selectActuation(QueryParameterActuationBDTO queryParameterActuationBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnActuationRDTO returnActuationRDTO = new ReturnActuationRDTO();
		List<ActuationGetRDTO> actuationGetRDTOs  = new ArrayList<>();
		returnRDTO = new ReturnRDTO();

		try {

			actuationGetRDTOs = getActuation(queryParameterActuationBDTO);
			
			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterActuationBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnActuationRDTO.setReturnRDTO(returnRDTO);
		returnActuationRDTO.setActuationGetRDTOs(actuationGetRDTOs);
		logger.info(LogsConstants.LOG_END);
		return returnActuationRDTO;
	}

	public List<ActuationGetRDTO> getActuation(QueryParameterActuationBDTO queryParameterActuationBDTO) throws ImiException {
		List<ActuationGetRDTO> actuationGetRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterActuationBDTO.getMap();
			List<Actuation> actuations = myBatisTemplate.execute(ActuationDao.class,
					new MyBatisDaoCallback<List<Actuation>>() {
						@Override
						public List<Actuation> execute(MyBatisDao dao) {
							return ((ActuationDao) dao).selectActuation(map);
						}
					});

			if (actuations != null && !actuations.isEmpty()) {
				actuationGetRDTOs = ActuationGetConvert.object2rdto(actuations);
				for(ActuationGetRDTO actuationGetRDTO : actuationGetRDTOs) {
					actuationGetRDTO.setCodeUser(queryParameterActuationBDTO.getCodeUser());
				}
			}
		} catch (Exception e) {			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterActuationBDTO.toString()), e);
			throw new ImiException(e);
		}
		return actuationGetRDTOs;
	}
}
