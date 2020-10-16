package es.bcn.imi.framework.vigia.frontal.business.domains.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.business.domains.DomainsService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao.ValueListGapDao;
import es.bcn.imi.framework.vigia.frontal.inventary.orm.dao.ValueListDao;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.ValueList;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ValueListGap;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDomainsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ValueListRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDomainValuesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDomainsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.convert.ValueListConvert;
import es.bcn.vigia.fmw.libutils.convert.ValueListGapConvert;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_FRONTAL_DOMAINS)
public class DomainsServiceImpl implements DomainsService {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	private ReturnRDTO returnRDTO;

	@Override
	public ReturnDomainsRDTO selectDomains() throws ImiException {
		logger.info(LogsConstants.LOG_START);		
		ReturnDomainsRDTO returnDomainsRDTO = new ReturnDomainsRDTO();
		returnRDTO = new ReturnRDTO();
		List<String> names = new ArrayList<>();

		try {

			names.addAll(getDomainsGap());
			names.addAll(getDomains());
			
			Collections.sort(names);

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		returnDomainsRDTO.setReturnRDTO(returnRDTO);
		returnDomainsRDTO.setNames(names);
		
		logger.debug(LogsConstants.LOG_END);
		return returnDomainsRDTO;
	}

	@Override
	public ReturnDomainValuesRDTO selectDomainValues(QueryParameterDomainsRDTO queryParameterDomainsRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);		
		ReturnDomainValuesRDTO returnDomainsRDTO = new ReturnDomainValuesRDTO();
		returnRDTO = new ReturnRDTO();
		List<ValueListRDTO> valueListRDTOs = new ArrayList<>();

		try {

			valueListRDTOs.addAll(getDomainValuesGap(queryParameterDomainsRDTO));
			valueListRDTOs.addAll(getDomainValues(queryParameterDomainsRDTO));

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		
		returnDomainsRDTO.setReturnRDTO(returnRDTO);
		returnDomainsRDTO.setValueListRDTOs(valueListRDTOs);
		logger.debug(LogsConstants.LOG_END);

		return returnDomainsRDTO;
	}

	public List<String> getDomainsGap() throws ImiException {
		logger.info(LogsConstants.LOG_START);		
		List<String> names = new ArrayList<>();
		try {
			names = myBatisTemplateGap.execute(ValueListGapDao.class,
				new MyBatisDaoCallbackGap<List<String>>() {
					@Override
					public List<String> execute(MyBatisDaoGap dao) {
						return ((ValueListGapDao) dao).getDomainsGap();
					}
				}
			);
		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, ""), e);
			throw new ImiException(e);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		return names;
	}

	public List<String> getDomains() throws ImiException {
		logger.info(LogsConstants.LOG_START);		
		List<String> names = new ArrayList<>();
		try {
			names = myBatisTemplate.execute(ValueListDao.class,
				new MyBatisDaoCallback<List<String>>() {
					@Override
					public List<String> execute(MyBatisDao dao) {
						return ((ValueListDao) dao).getDomains();
					}
				}
			);
		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, ""), e);
			throw new ImiException(e);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		return names;
	}

	public List<ValueListRDTO> getDomainValuesGap(QueryParameterDomainsRDTO queryParameterDomainsRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);		
		List<ValueListRDTO> valueListRDTOs = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("name", queryParameterDomainsRDTO.getName());
		map.put("dateReference", queryParameterDomainsRDTO.getDateReference());
		try {
			List<ValueListGap> valueListGaps = myBatisTemplateGap.execute(ValueListGapDao.class,
					new MyBatisDaoCallbackGap<List<ValueListGap>>() {
						@Override
						public List<ValueListGap> execute(MyBatisDaoGap dao) {
							return ((ValueListGapDao) dao).getDomainValuesGap(map);
						}
					});

			if (valueListGaps != null && !valueListGaps.isEmpty()) {
				valueListRDTOs = ValueListGapConvert.object2rdto(valueListGaps);
			}
		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterDomainsRDTO), e);
			throw new ImiException(e);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		return valueListRDTOs;
	}

	public List<ValueListRDTO> getDomainValues(QueryParameterDomainsRDTO queryParameterDomainsRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);		
		List<ValueListRDTO> valueListRDTOs = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("name", queryParameterDomainsRDTO.getName());
		map.put("dateReference", queryParameterDomainsRDTO.getDateReference());
		try {
			List<ValueList> valueLists = myBatisTemplate.execute(ValueListDao.class,
					new MyBatisDaoCallback<List<ValueList>>() {
						@Override
						public List<ValueList> execute(MyBatisDao dao) {
							return ((ValueListDao) dao).getDomainValues(map);
						}
					});

			if (valueLists != null && !valueLists.isEmpty()) {
				valueListRDTOs = ValueListConvert.object2rdto(valueLists);
			}
		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterDomainsRDTO), e);
			throw new ImiException(e);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		return valueListRDTOs;
	}

}
