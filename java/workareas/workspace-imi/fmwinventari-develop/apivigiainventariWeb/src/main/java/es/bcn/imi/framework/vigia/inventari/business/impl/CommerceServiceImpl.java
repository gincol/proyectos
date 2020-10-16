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

import es.bcn.imi.framework.vigia.inventari.business.CommerceService;
import es.bcn.imi.framework.vigia.inventari.integration.orm.dao.gap.CommerceGapDao;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.integration.gap.inventary.comerc.service.IntegrationGapCommerceService;
import es.bcn.vigia.fmw.integration.gap.inventary.comerc.to.response.CreateComercResponse;
import es.bcn.vigia.fmw.integration.gap.inventary.comerc.to.response.UpdateComercResponse;
import es.bcn.vigia.fmw.libcommons.business.dto.CommerceBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.MessageContansts;
import es.bcn.vigia.fmw.libcommons.constants.ReturnGapConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.CommerceDetailedGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.CommerceElementGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.CommerceGap;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.CommerceElementRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.MassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceElementsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.convert.CommerceDetailedGapConvert;
import es.bcn.vigia.fmw.libutils.convert.CommerceElementGapConvert;
import es.bcn.vigia.fmw.libutils.convert.CommerceMassiveGapConvert;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy
@Service(ServicesConstants.SRV_COMMERCE)
public class CommerceServiceImpl implements CommerceService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INT_GAP_COMMERCE)
	IntegrationGapCommerceService service;

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	private ReturnRDTO returnRDTO;

	private String isCode;

	private String isMessage;

	@Override
	public ReturnRDTO insert(CommerceBDTO commerceBDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			CreateComercResponse response = service.create(commerceBDTO);

			if (!response.getCreateComercResults().isEmpty()) {

				isCode = response.getCreateComercResults().get(0).getResult().getResultCode();
				isMessage = response.getCreateComercResults().get(0).getResult().getResultMessage();

			} else {

				isCode = ReturnEnum.CODE_ERROR_GAP_REGISTERED.getCodeDescription();
				isMessage = ReturnEnum.CODE_ERROR_GAP_REGISTERED.getMessage();
			}

			if (isCode.endsWith(ReturnGapConstants.CREATE_SUCCESS)) {

				isCode = ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription();
				isMessage = ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage();

			} else {
				if (!isCode.equals(ReturnEnum.CODE_ERROR_GAP_REGISTERED.getCodeDescription())) {

					isCode = ReturnEnum.CODE_ERROR_GAP_GENERIC_VALIDATE.getCodeDescription();
				}
			}

		} catch (Exception e) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, commerceBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, commerceBDTO.toString()), e);
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO update(CommerceBDTO commerceBDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			UpdateComercResponse response = service.update(commerceBDTO);

			if (!response.getUpdateComercResults().isEmpty()) {

				isCode = response.getUpdateComercResults().get(0).getResult().getResultCode();
				isMessage = response.getUpdateComercResults().get(0).getResult().getResultMessage();
			} else {

				isCode = ReturnEnum.CODE_ERROR_GAP_UPDATED.getCodeDescription();
				isMessage = ReturnEnum.CODE_ERROR_GAP_UPDATED.getMessage();
			}

			if (isCode.endsWith(ReturnGapConstants.UPDATE_SUCCESS)) {

				isCode = ReturnEnum.CODE_SUCCESS_UPDATED.getCodeDescription();
				isMessage = ReturnEnum.CODE_SUCCESS_UPDATED.getMessage();

			} else {

				if (!isCode.equals(ReturnEnum.CODE_ERROR_GAP_UPDATED.getCodeDescription())) {

					isCode = ReturnEnum.CODE_ERROR_GAP_GENERIC_VALIDATE.getCodeDescription();
				}
			}

		} catch (Exception e) {

			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, commerceBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, commerceBDTO.toString()), e);
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);

		return returnRDTO;

	}

	@Override
	public ReturnRDTO delete(CommerceBDTO commerceBDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			UpdateComercResponse response = service.update(commerceBDTO);

			if (!response.getUpdateComercResults().isEmpty()) {

				isCode = response.getUpdateComercResults().get(0).getResult().getResultCode();
				isMessage = response.getUpdateComercResults().get(0).getResult().getResultMessage();

			} else {

				isCode = ReturnEnum.CODE_ERROR_GAP_REMOVED.getCodeDescription();
				isMessage = ReturnEnum.CODE_ERROR_GAP_REMOVED.getMessage();
			}

			if (isCode.endsWith(ReturnGapConstants.UPDATE_SUCCESS)) {

				isCode = ReturnEnum.CODE_SUCCESS_REMOVED.getCodeDescription();
				isMessage = ReturnEnum.CODE_SUCCESS_REMOVED.getMessage();

			} else {

				if (!isCode.equals(ReturnEnum.CODE_ERROR_GAP_REMOVED.getCodeDescription())) {

					isCode = ReturnEnum.CODE_ERROR_GAP_GENERIC_VALIDATE.getCodeDescription();
				}
			}

		} catch (Exception e) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, commerceBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, commerceBDTO.toString()), e);
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertElements(CommerceBDTO commerceBDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			UpdateComercResponse response = service.createElements(commerceBDTO);

			if (!response.getUpdateComercResults().isEmpty()) {

				isCode = response.getUpdateComercResults().get(0).getResult().getResultCode();
				isMessage = response.getUpdateComercResults().get(0).getResult().getResultMessage();

			} else {

				isCode = ReturnEnum.CODE_ERROR_GAP_UPDATED.getCodeDescription();
				isMessage = ReturnEnum.CODE_ERROR_GAP_UPDATED.getMessage();
			}

			if (isCode.endsWith(ReturnGapConstants.UPDATE_SUCCESS)) {

				isCode = ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription();
				isMessage = ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage();

			} else {

				if (!isCode.equals(ReturnEnum.CODE_ERROR_GAP_UPDATED.getCodeDescription())) {

					isCode = ReturnEnum.CODE_ERROR_GAP_GENERIC_VALIDATE.getCodeDescription();
				}
			}

		} catch (Exception e) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, commerceBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, commerceBDTO.toString()), e);
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

			massiveRDTOs = getCommerceMassive(queryParameterBDTO);

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
	public ReturnCommerceDetailedRDTO selectDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnCommerceDetailedRDTO returnCommerceDetailedRDTO = new ReturnCommerceDetailedRDTO();
		CommerceDetailedRDTO commerceDetailedRDTO = new CommerceDetailedRDTO();
		returnRDTO = new ReturnRDTO();

		try {

			commerceDetailedRDTO = getCommerceDetailed(queryParameterBDTO);

		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnCommerceDetailedRDTO.setReturnRDTO(returnRDTO);
		returnCommerceDetailedRDTO.setCommerceDetailedRDTO(commerceDetailedRDTO);
		logger.info(LogsConstants.LOG_END);
		return returnCommerceDetailedRDTO;
	}

	@Override
	public ReturnCommerceElementsRDTO selectElements(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnCommerceElementsRDTO returnCommerceElementsRDTO = new ReturnCommerceElementsRDTO();
		List<CommerceElementRDTO> commerceElementRDTOs = new ArrayList<>();
		returnRDTO = new ReturnRDTO();

		try {

			commerceElementRDTOs = getCommerceElements(queryParameterBDTO);

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnCommerceElementsRDTO.setReturnRDTO(returnRDTO);
		returnCommerceElementsRDTO.setCommerceElementRDTOs(commerceElementRDTOs);
		logger.info(LogsConstants.LOG_END);
		return returnCommerceElementsRDTO;
	}

	public List<MassiveRDTO> getCommerceMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		List<MassiveRDTO> massiveRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<CommerceGap> commerceGaps = myBatisTemplateGap.execute(CommerceGapDao.class,
					new MyBatisDaoCallbackGap<List<CommerceGap>>() {
						@Override
						public List<CommerceGap> execute(MyBatisDaoGap dao) {
							return ((CommerceGapDao) dao).selectCommerceMassive(map);
						}
					});

			if (commerceGaps != null && !commerceGaps.isEmpty()) {
				massiveRDTOs = CommerceMassiveGapConvert.object2rdto(commerceGaps);

			}
		} catch (Exception e) {			
						
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return massiveRDTOs;
	}

	public CommerceDetailedRDTO getCommerceDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		CommerceDetailedRDTO commerceDetailedRDTO = new CommerceDetailedRDTO();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<CommerceDetailedGap> commerceDetailedGaps = myBatisTemplateGap.execute(CommerceGapDao.class,
					new MyBatisDaoCallbackGap<List<CommerceDetailedGap>>() {
						@Override
						public List<CommerceDetailedGap> execute(MyBatisDaoGap dao) {
							return ((CommerceGapDao) dao).selectCommerce(map);
						}
					});
	
			if (commerceDetailedGaps != null && !commerceDetailedGaps.isEmpty()) {
				commerceDetailedRDTO = CommerceDetailedGapConvert.object2rdto(commerceDetailedGaps.get(0));
				commerceDetailedRDTO.setCodeUser(queryParameterBDTO.getCodeUser());
				
				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}else {
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
						MessageContansts.MSG_ENTITY_COMMERCE, queryParameterBDTO.getCode()));
			}
		} catch (Exception e) {			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return commerceDetailedRDTO;
	}

	public List<CommerceElementRDTO> getCommerceElements(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		List<CommerceElementRDTO> commerceElementRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<CommerceElementGap> commerceElementGaps = myBatisTemplateGap.execute(CommerceGapDao.class,
					new MyBatisDaoCallbackGap<List<CommerceElementGap>>() {
						@Override
						public List<CommerceElementGap> execute(MyBatisDaoGap dao) {
							return ((CommerceGapDao) dao).selectElementsByCommerce(map);
						}
					});
	
			if (commerceElementGaps != null && !commerceElementGaps.isEmpty()) {
				commerceElementRDTOs = CommerceElementGapConvert.object2rdto(commerceElementGaps);
	
			}
		} catch (Exception e) {			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return commerceElementRDTOs;
	}
}