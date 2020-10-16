package es.bcn.imi.framework.vigia.inventari.business.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.bcn.imi.framework.vigia.inventari.business.EmployeeCatalogService;
import es.bcn.imi.framework.vigia.inventari.integration.orm.dao.EmployeeCatalogDao;
import es.bcn.vigia.fmw.libcommons.business.dto.EmployeeCatalogBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.MessageContansts;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.EmployeeCatalog;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.EmployeeCatalogDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.MassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEmployeeCatalogDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.convert.EmployeeCatalogConvert;
import es.bcn.vigia.fmw.libutils.convert.EmployeeCatalogDetailedConvert;
import es.bcn.vigia.fmw.libutils.convert.EmployeeCatalogMassiveConvert;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_EMPLOYEE_CATALOG)
public class EmployeeCatalogServiceImpl implements EmployeeCatalogService {

	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;
	
	private ReturnRDTO returnRDTO;
	
	private String isCode;

	private String isMessage;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public ReturnRDTO insert(EmployeeCatalogBDTO employeeCatalogBDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();


		try {
			
			final EmployeeCatalog employeeCatalog = EmployeeCatalogConvert.bdto2object(employeeCatalogBDTO);
			
			myBatisTemplate.execute(EmployeeCatalogDao.class, new MyBatisDaoCallback<Void>() {

				@Override
				public Void execute(MyBatisDao dao) {
					((EmployeeCatalogDao) dao).insert(employeeCatalog);
					return null;
				}
			});
			
			isCode = ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription();
			isMessage = ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage();
			
		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, employeeCatalogBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, employeeCatalogBDTO.toString()), e);
		}
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}

	@Override
	public ReturnMassiveRDTO selectMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		List<MassiveRDTO> massiveRDTOs = new ArrayList<>();
		returnRDTO = new ReturnRDTO();

		try {

			massiveRDTOs = getEmployeeCatalogMassive(queryParameterBDTO);

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception e) {
			
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnMassiveRDTO.setReturnRDTO(returnRDTO);
		returnMassiveRDTO.setMassiveRDTOs(massiveRDTOs);
		logger.info(LogsConstants.LOG_END);
		return returnMassiveRDTO;
	}

	@Override
	public ReturnEmployeeCatalogDetailedRDTO selectDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnEmployeeCatalogDetailedRDTO returnEmployeeCatalogDetailedRDTO = new ReturnEmployeeCatalogDetailedRDTO();
		List<EmployeeCatalogDetailedRDTO> employeeCatalogDetailedRDTOs = new ArrayList<>();
		returnRDTO = new ReturnRDTO();

		try {

			employeeCatalogDetailedRDTOs = getEmployeeCatalogDetailed(queryParameterBDTO);

		} catch (Exception e) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnEmployeeCatalogDetailedRDTO.setReturnRDTO(returnRDTO);
		returnEmployeeCatalogDetailedRDTO.setEmployeeCatalogDetailedRDTOs(employeeCatalogDetailedRDTOs);
		logger.info(LogsConstants.LOG_END);
		return returnEmployeeCatalogDetailedRDTO;
	}

	public List<MassiveRDTO> getEmployeeCatalogMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		List<MassiveRDTO> massiveRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			String[] p = queryParameterBDTO.getDateReference().split("-");
			Integer month = Integer.parseInt(p[0]);
			Integer year = Integer.parseInt(p[1]);
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, 0);
			String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
			map.put("dateReference", day + "-" + ((month < 10) ? "0" : "") + month + "-" + year);
			List<EmployeeCatalog> employeeCatalogs = myBatisTemplate.execute(EmployeeCatalogDao.class,
					new MyBatisDaoCallback<List<EmployeeCatalog>>() {
						@Override
						public List<EmployeeCatalog> execute(MyBatisDao dao) {
							return ((EmployeeCatalogDao) dao).selectEmployeeCatalogMassive(map);
						}
					});
	
			if (employeeCatalogs != null && !employeeCatalogs.isEmpty()) {
				massiveRDTOs = EmployeeCatalogMassiveConvert.object2rdto(employeeCatalogs);
	
			}
		} catch (Exception e) {			
				
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return massiveRDTOs;
	}

	public List<EmployeeCatalogDetailedRDTO> getEmployeeCatalogDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		List<EmployeeCatalogDetailedRDTO> employeeCatalogDetailedRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			String[] p = queryParameterBDTO.getDateReference().split("-");
			Integer month = Integer.parseInt(p[0]);
			Integer year = Integer.parseInt(p[1]);
			map.put("dateReference", "01-" + ((month < 10) ? "0" : "") + month + "-" + year);
			month++;
			if(month == 13) {
				month = 1;
				year++;
			}
			map.put("dateReference2", "01-" + ((month < 10) ? "0" : "") + month + "-" + year);
			List<EmployeeCatalog> employeeCatalogs = myBatisTemplate.execute(EmployeeCatalogDao.class,
					new MyBatisDaoCallback<List<EmployeeCatalog>>() {
						@Override
						public List<EmployeeCatalog> execute(MyBatisDao dao) {
							return ((EmployeeCatalogDao) dao).selectEmployeeCatalog(map);
						}
					});
	
			if (employeeCatalogs != null && !employeeCatalogs.isEmpty()) {
				employeeCatalogDetailedRDTOs = EmployeeCatalogDetailedConvert.object2rdto(employeeCatalogs);
				for(EmployeeCatalogDetailedRDTO employeeCatalogDetailedRDTO:employeeCatalogDetailedRDTOs) {
					employeeCatalogDetailedRDTO.setCodeUser(queryParameterBDTO.getCodeUser());
				}
				
				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}else {
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
						MessageContansts.MSG_ENTITY_EMPLOYEE_CATALOG, queryParameterBDTO.getCode()));
			}
		} catch (Exception e) {			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return employeeCatalogDetailedRDTOs;
	}

}
