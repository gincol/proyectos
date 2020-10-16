package es.bcn.imi.framework.vigia.inventari.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
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

import es.bcn.imi.framework.vigia.inventari.business.UbicationService;
import es.bcn.imi.framework.vigia.inventari.integration.orm.dao.UbicationDao;
import es.bcn.imi.framework.vigia.inventari.integration.orm.dao.gap.LabelGapDao;
import es.bcn.imi.framework.vigia.inventari.integration.orm.dao.gap.UbicationGapDao;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.integration.gap.inventary.ubication.service.IntegrationGapUbicationService;
import es.bcn.vigia.fmw.integration.gap.inventary.ubication.to.response.CreateUbicacioResponse;
import es.bcn.vigia.fmw.integration.gap.inventary.ubication.to.response.UpdateUbicacioResponse;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.UbicationBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.MessageContansts;
import es.bcn.vigia.fmw.libcommons.constants.ReturnGapConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.Ubication;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.LabelGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.MclGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.UbicationGap;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LabelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.MassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.MclRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.UbicationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnUbicationDetailedRDTO;
import es.bcn.vigia.fmw.libutils.convert.LabelGapConvert;
import es.bcn.vigia.fmw.libutils.convert.MclGapConvert;
import es.bcn.vigia.fmw.libutils.convert.UbicationConvert;
import es.bcn.vigia.fmw.libutils.convert.UbicationDetailedGapConvert;
import es.bcn.vigia.fmw.libutils.convert.UbicationMassiveGapConvert;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_UBICATION)
public class UbicationServiceImpl implements UbicationService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INT_GAP_UBICATION)
	IntegrationGapUbicationService service;

	private ReturnRDTO returnRDTO;

	private String isCode;

	private String isMessage;

	@Override
	public ReturnRDTO insert(UbicationBDTO ubicationBDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			CreateUbicacioResponse response = service.create(ubicationBDTO);

			if (!response.getCreateUbicacioResults().isEmpty()) {

				isCode 		= response.getCreateUbicacioResults().get(0).getResult().getResultCode();
				isMessage 	= response.getCreateUbicacioResults().get(0).getResult().getResultMessage();

			} else {

				isCode 		= ReturnEnum.CODE_ERROR_GAP_REGISTERED.getCodeDescription();
				isMessage 	= ReturnEnum.CODE_ERROR_GAP_REGISTERED.getMessage();
			
			}

			if (isCode.endsWith(ReturnGapConstants.CREATE_SUCCESS)) {
				
				isCode 		= ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription();
		
			} else {
				
				if (!isCode.equals(ReturnEnum.CODE_ERROR_GAP_REGISTERED.getCodeDescription())) {

					isCode 	= ReturnEnum.CODE_ERROR_GAP_GENERIC_VALIDATE.getCodeDescription();
				}
			}
			returnRDTO.setCode(isCode);
			returnRDTO.setMessage(isMessage);
		} catch (Exception e) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, ubicationBDTO), e);
			 throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, ubicationBDTO), e);
		}

			
		if (ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription().equals(returnRDTO.getCode()))
		{
			
			try {
				final Ubication ubication = UbicationConvert.bdto2object(ubicationBDTO);
				myBatisTemplate.execute(UbicationDao.class, new MyBatisDaoCallback<Void>() {

					@Override
					public Void execute(MyBatisDao dao) {
						((UbicationDao) dao).insert(ubication);
						return null;
					}
				});
				
				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage());
			} catch (Exception e) {
				logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, ubicationBDTO), e);
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_FMW));
			}

		}

		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public ReturnRDTO update(UbicationBDTO ubicationBDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();
		try {

			UpdateUbicacioResponse response = service.update(ubicationBDTO);

		
			if (!response.getUpdateUbicacioResults().isEmpty()) {

				isCode 		= response.getUpdateUbicacioResults().get(0).getResult().getResultCode();
				isMessage 	= response.getUpdateUbicacioResults().get(0).getResult().getResultMessage();

			} else {

				isCode 		= ReturnEnum.CODE_ERROR_GAP_UPDATED.getCodeDescription();
				isMessage 	= ReturnEnum.CODE_ERROR_GAP_UPDATED.getMessage();
			
			}

			if (isCode.endsWith(ReturnGapConstants.UPDATE_SUCCESS)) {
				
				isCode 		= ReturnEnum.CODE_SUCCESS_UPDATED.getCodeDescription();
		
			} else {
				
				if (!isCode.equals(ReturnEnum.CODE_ERROR_GAP_UPDATED.getCodeDescription())) {

					isCode 	= ReturnEnum.CODE_ERROR_GAP_GENERIC_VALIDATE.getCodeDescription();
				}
			}
		
			returnRDTO.setCode(isCode);
			returnRDTO.setMessage(isMessage);
		} catch (Exception e) {
		
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, ubicationBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, ubicationBDTO.toString()), e);
		}
		
		
		
			if (ReturnEnum.CODE_SUCCESS_UPDATED.getCodeDescription().equals(returnRDTO.getCode()))
			{
				try{

					final Ubication ubication = UbicationConvert.bdto2object(ubicationBDTO);
					
					myBatisTemplate.execute(UbicationDao.class, new MyBatisDaoCallback<Void>() {
						@Override
						public Void execute(MyBatisDao dao) {
							((UbicationDao) dao).update(ubication);
							((UbicationDao) dao).insert(ubication);
							return null;
						}
					});
					
					returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_UPDATED.getCodeDescription());
					returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_UPDATED.getMessage());

				}catch(Exception e)
				{
					logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, ubicationBDTO), e);
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_UPDATE_NOT_COMPLETE.getCodeDescription());
					returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_UPDATE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_FMW));
				}
			}

		

		logger.info(LogsConstants.LOG_END);

		return returnRDTO;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public ReturnRDTO delete(UbicationBDTO ubicationBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();
		try {

			UpdateUbicacioResponse response = service.update(ubicationBDTO);

			
			if (!response.getUpdateUbicacioResults().isEmpty()) {

				returnRDTO.setCode(response.getUpdateUbicacioResults().get(0).getResult().getResultCode());
				returnRDTO.setMessage(response.getUpdateUbicacioResults().get(0).getResult().getResultMessage());

			} else {

				returnRDTO.setCode(ReturnEnum.CODE_ERROR_GENERIC.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_ERROR_GENERIC.getMessage());
			}

			if (returnRDTO.getCode().endsWith(ReturnGapConstants.DELETE_SUCCESS)) {
				
				try {

					final Ubication ubication = UbicationConvert.bdto2object(ubicationBDTO);
					myBatisTemplate.execute(UbicationDao.class, new MyBatisDaoCallback<Void>() {
						@Override
						public Void execute(MyBatisDao dao) {
							((UbicationDao) dao).delete(ubication);
							return null;
						}
					});
					
					returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_REMOVED.getCodeDescription());
					returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_REMOVED.getMessage());
					
				}catch(Exception e){
					logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, ubicationBDTO), e);
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_DELETE_NOT_COMPLETE.getCodeDescription());
					returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_DELETE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_FMW));
				}
				

			}

		} catch (Exception e) {
			
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, ubicationBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, ubicationBDTO.toString()), e);
		}

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

			massiveRDTOs = getUbicationMassive(queryParameterBDTO);

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
	public ReturnUbicationDetailedRDTO selectDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnUbicationDetailedRDTO returnUbicationDetailedRDTO = new ReturnUbicationDetailedRDTO();
		UbicationDetailedRDTO ubicationDetailedRDTO = new UbicationDetailedRDTO();
		returnRDTO = new ReturnRDTO();

		try {

			ubicationDetailedRDTO = getUbicationDetailed(queryParameterBDTO);

		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnUbicationDetailedRDTO.setReturnRDTO(returnRDTO);
		returnUbicationDetailedRDTO.setUbicationDetailedRDTO(ubicationDetailedRDTO);
		logger.info(LogsConstants.LOG_END);
		return returnUbicationDetailedRDTO;
	}

	public List<MassiveRDTO> getUbicationMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		List<MassiveRDTO> massiveRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<UbicationGap> ubicationGaps = myBatisTemplateGap.execute(UbicationGapDao.class,
					new MyBatisDaoCallbackGap<List<UbicationGap>>() {
						@Override
						public List<UbicationGap> execute(MyBatisDaoGap dao) {
							return ((UbicationGapDao) dao).selectUbicationMassive(map);
						}
					});

			if (ubicationGaps != null && !ubicationGaps.isEmpty()) {
				massiveRDTOs = UbicationMassiveGapConvert.object2rdto(ubicationGaps);
			}
		} catch (Exception e) {			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return massiveRDTOs;
	}

	public UbicationDetailedRDTO getUbicationDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		UbicationDetailedRDTO ubicationDetailedRDTO = new UbicationDetailedRDTO();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<UbicationGap> ubicationGaps = myBatisTemplateGap.execute(UbicationGapDao.class,
					new MyBatisDaoCallbackGap<List<UbicationGap>>() {
						@Override
						public List<UbicationGap> execute(MyBatisDaoGap dao) {
							return ((UbicationGapDao) dao).selectUbication(map);
						}
					});
	
			if (ubicationGaps != null && !ubicationGaps.isEmpty()) {
				UbicationGap ubicationGap = ubicationGaps.get(0);
				ubicationDetailedRDTO = UbicationDetailedGapConvert.object2rdto(ubicationGap);
				ubicationDetailedRDTO.setCodeUser(queryParameterBDTO.getCodeUser());

				List<Ubication> ubications = myBatisTemplate.execute(UbicationDao.class,
					new MyBatisDaoCallback<List<Ubication>>() {
						@Override
						public List<Ubication> execute(MyBatisDao dao) {
							return ((UbicationDao) dao).selectUbication(map);
						}
					}
				);
				if(ubications != null && !ubications.isEmpty()) {
					Ubication ubication = ubications.get(0);					
					ubicationDetailedRDTO.setReasonChange(ubication.getReasonChange());
					ubicationDetailedRDTO.setTypeReference(ubication.getRefTypeEnvironment());
					ubicationDetailedRDTO.setCoordinate_1_X(ubication.getCoordinate_1_X());
					ubicationDetailedRDTO.setCoordinate_1_Y(ubication.getCoordinate_1_Y());
					ubicationDetailedRDTO.setCoordinate_2_X(ubication.getCoordinate_2_X());
					ubicationDetailedRDTO.setCoordinate_2_Y(ubication.getCoordinate_2_Y());
					ubicationDetailedRDTO.setCoordinate_3_X(ubication.getCoordinate_3_X());
					ubicationDetailedRDTO.setCoordinate_3_Y(ubication.getCoordinate_3_Y());
					ubicationDetailedRDTO.setCoordinate_4_X(ubication.getCoordinate_4_X());
					ubicationDetailedRDTO.setCoordinate_4_Y(ubication.getCoordinate_4_Y());
				}
				ubicationDetailedRDTO.setDocumentarySupportRDTOs(null);
				ubicationDetailedRDTO.setPerformanceRDTOs(null);
				
				if(ubicationGap.getLabel() != null && !ubicationGap.getLabel().isEmpty()) {
					List<LabelRDTO> labelRDTOs = getLabelsRDTO(ubicationGap);
					
					ubicationDetailedRDTO.setLabelRDTOs(labelRDTOs);
				}					
				
				List<MclGap> mclGaps =  myBatisTemplateGap.execute(UbicationGapDao.class,
					new MyBatisDaoCallbackGap<List<MclGap>>() {
						@Override
						public List<MclGap> execute(MyBatisDaoGap dao) {
							return ((UbicationGapDao) dao).selectMclByUbication(map);
						}
					}
				);
				if(mclGaps != null && !mclGaps.isEmpty()) {
					List<MclRDTO> mclRDTOs = MclGapConvert.object2rdto(mclGaps);
					ubicationDetailedRDTO.setMclRDTOs(mclRDTOs);					
				}
				
				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}else {
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
						MessageContansts.MSG_ENTITY_UBICATION, queryParameterBDTO.getCode()));
			}
		} catch (Exception e) {			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return ubicationDetailedRDTO;
	}
	
	public List<LabelRDTO> getLabelsRDTO(UbicationGap ubication) throws ImiException{
		List<LabelRDTO> labelRDTOs = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("value", ubication.getLabel());
		List<LabelGap> labelGaps = myBatisTemplateGap.execute(LabelGapDao.class,
			new MyBatisDaoCallbackGap<List<LabelGap>>() {
				@Override
				public List<LabelGap> execute(MyBatisDaoGap dao) {
					return ((LabelGapDao) dao).selectLabel(map);
				}
			}
		);
		if(labelGaps != null && !labelGaps.isEmpty()) {
			labelRDTOs = LabelGapConvert.object2rdto(labelGaps);
		}
		return labelRDTOs;
	}

}