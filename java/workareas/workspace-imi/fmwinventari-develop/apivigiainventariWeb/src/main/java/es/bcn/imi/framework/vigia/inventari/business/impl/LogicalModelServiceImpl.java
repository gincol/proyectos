package es.bcn.imi.framework.vigia.inventari.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.inventari.business.LogicalModelService;
import es.bcn.imi.framework.vigia.inventari.integration.orm.dao.LogicalModelDao;
import es.bcn.imi.framework.vigia.inventari.integration.orm.dao.gap.LogicalModelGapDao;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.integration.gap.inventary.logicalmodel.service.IntegrationGapLogicalModelService;
import es.bcn.vigia.fmw.integration.gap.inventary.logicalmodel.to.LogicUbicacioTO;
import es.bcn.vigia.fmw.integration.gap.inventary.logicalmodel.to.MobiliariLogicTO;
import es.bcn.vigia.fmw.integration.gap.inventary.logicalmodel.to.response.CreateMobiliariLogicResponse;
import es.bcn.vigia.fmw.integration.gap.inventary.logicalmodel.to.response.ReadMobiliariLogicResponse;
import es.bcn.vigia.fmw.integration.gap.inventary.logicalmodel.to.response.UpdateMobiliariLogicResponse;
import es.bcn.vigia.fmw.integration.gap.inventary.physicalmodel.to.MobiliariFisicTO;
import es.bcn.vigia.fmw.libcommons.business.dto.LogicalModelBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.UbicationBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.MessageContansts;
import es.bcn.vigia.fmw.libcommons.constants.ReturnGapConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.LogicalModel;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.PhysicalModel;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.MclGap;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.LogicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.LogicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.convert.LogicalModelDetailedGapConvert;
import es.bcn.vigia.fmw.libutils.convert.LogicalModelMassiveGapConvert;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_LOGICAL_MODEL)
public class LogicalModelServiceImpl implements LogicalModelService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INT_GAP_LOGICAL_MODEL)
	IntegrationGapLogicalModelService service;

	private ReturnRDTO returnRDTO;

	@Override
	public ReturnRDTO insert(LogicalModelBDTO logicalModelBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();
		try {

			CreateMobiliariLogicResponse response = service.create(logicalModelBDTO);

			if (!response.getCreateMobiliariLogicResults().isEmpty()) {

				returnRDTO.setCode(response.getCreateMobiliariLogicResults().get(0).getResult().getResultCode());
				returnRDTO.setMessage(response.getCreateMobiliariLogicResults().get(0).getResult().getResultMessage());

			} else {

				returnRDTO.setCode(ReturnEnum.CODE_ERROR_GENERIC.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_ERROR_GENERIC.getMessage());

			}

			if (returnRDTO.getCode().endsWith(ReturnGapConstants.CREATE_SUCCESS)) {
				
				try {

					final LogicalModel logicalModel = logicalModelBDTOToLogicalModel(logicalModelBDTO);
					myBatisTemplate.execute(LogicalModelDao.class, new MyBatisDaoCallback<Void>() {
	
						@Override
						public Void execute(MyBatisDao dao) {
							((LogicalModelDao) dao).insert(logicalModel);
							return null;
						}
					});
					
					returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription());
					returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage());
					
				}catch (Exception e) {
					logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, logicalModelBDTO), e);			
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getCodeDescription());
					returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_FMW));
					return returnRDTO;
				}
			}

		} catch (Exception e) {
			
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC);
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, logicalModelBDTO.toString()), e);
		}

		logger.info(LogsConstants.LOG_END);
		return returnRDTO;
	}

	@Override
	public ReturnRDTO update(LogicalModelBDTO logicalModelBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {

			UpdateMobiliariLogicResponse response = service.update(logicalModelBDTO);

			if (!response.getUpdateMobiliariLogicResults().isEmpty()) {
				returnRDTO.setCode(response.getUpdateMobiliariLogicResults().get(0).getResult().getResultCode());
				returnRDTO.setMessage(response.getUpdateMobiliariLogicResults().get(0).getResult().getResultMessage());
			} else {
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_GENERIC.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_ERROR_GENERIC.getMessage());
			}

			if (returnRDTO.getCode().endsWith(ReturnGapConstants.UPDATE_SUCCESS)) {
				
				try {
					final LogicalModel logicalModel = logicalModelBDTOToLogicalModel(logicalModelBDTO);
					myBatisTemplate.execute(LogicalModelDao.class, new MyBatisDaoCallback<Void>() {
	
						@Override
						public Void execute(MyBatisDao dao) {
							((LogicalModelDao) dao).update(logicalModel);
							((LogicalModelDao) dao).insert(logicalModel);
							return null;
						}
					});
					
					returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_UPDATED.getCodeDescription());
					returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_UPDATED.getMessage());	
					
				}catch (Exception e) {
					logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, logicalModelBDTO), e);			
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_UPDATE_NOT_COMPLETE.getCodeDescription());
					returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_UPDATE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_FMW));
					return returnRDTO;
				}
			}

		} catch (Exception e) {
			
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC);

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, logicalModelBDTO.toString()), e);
		}
		logger.info(LogsConstants.LOG_END);
		return returnRDTO;
	}

	@Override
	public ReturnRDTO delete(LogicalModelBDTO logicalModelBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {

			UpdateMobiliariLogicResponse response = service.update(logicalModelBDTO);

			if (!response.getUpdateMobiliariLogicResults().isEmpty()) {
				returnRDTO.setCode(response.getUpdateMobiliariLogicResults().get(0).getResult().getResultCode());
				returnRDTO.setMessage(response.getUpdateMobiliariLogicResults().get(0).getResult().getResultMessage());
			} else {
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_GENERIC.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_ERROR_GENERIC.getMessage());
			}

			if (returnRDTO.getCode().endsWith(ReturnGapConstants.UPDATE_SUCCESS)) {
				
				try {

					final LogicalModel logicalModel = logicalModelBDTOToLogicalModel(logicalModelBDTO);
					myBatisTemplate.execute(LogicalModelDao.class, new MyBatisDaoCallback<Void>() {
	
						@Override
						public Void execute(MyBatisDao dao) {
							((LogicalModelDao) dao).delete(logicalModel);
							return null;
						}
					});
					
					returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_REMOVED.getCodeDescription());
					returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_REMOVED.getMessage());
					
				}catch (Exception e) {
					logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, logicalModelBDTO), e);			
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_DELETE_NOT_COMPLETE.getCodeDescription());
					returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_DELETE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_FMW));
					return returnRDTO;
				}
			}

		} catch (Exception e) {
			
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC);

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, logicalModelBDTO.toString()), e);
		}

		logger.info(LogsConstants.LOG_END);
		return returnRDTO;
	}

	@Override
	public ReturnLogicalModelRDTO select(LogicalModelBDTO logicalModelBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnLogicalModelRDTO result = new ReturnLogicalModelRDTO();
		LogicalModelRDTO logicalModelRDTO = new LogicalModelRDTO();
		returnRDTO = new ReturnRDTO();

		try {

			ReadMobiliariLogicResponse response = service.read(logicalModelBDTO.getCode());
			if (!response.getReadMobiliariLogicResults().isEmpty()) {

				MobiliariLogicTO mobiliariLogicTO = response.getReadMobiliariLogicResults().get(0).getMobiliariLogic();
				List<LogicUbicacioTO> logicUbicacioTO = response.getReadMobiliariLogicResults().get(0)
						.getLogicUbicacio();

				List<MobiliariFisicTO> lstMobiliariFisicTO = response.getReadMobiliariLogicResults().get(0)
						.getMobiliariFisic();

				if (mobiliariLogicTO == null) {
					returnRDTO.setCode(response.getReadMobiliariLogicResults().get(0).getResult().getResultCode());
					returnRDTO
							.setMessage(response.getReadMobiliariLogicResults().get(0).getResult().getResultMessage());
					result.setReturnRDTO(returnRDTO);
					result.setLogicalModelRDTO(logicalModelRDTO);
					return result;
				}

				logicalModelRDTO.setCode(mobiliariLogicTO.getCodi());
				logicalModelRDTO.setCodeState(mobiliariLogicTO.getCodiEstatMCL());
				logicalModelRDTO.setCodeFractionType(mobiliariLogicTO.getCodiTipusFraccio());
				logicalModelRDTO.setCodeType(mobiliariLogicTO.getCodiTipusMCL());
				logicalModelRDTO.setCoordinateX(mobiliariLogicTO.getCoordinadaX());
				logicalModelRDTO.setCoordinateY(mobiliariLogicTO.getCoordinadaY());
				logicalModelRDTO.setDateStart(mobiliariLogicTO.getDataInici());
				logicalModelRDTO.setDateEnd(mobiliariLogicTO.getDataFi());

				if (logicUbicacioTO != null && !logicUbicacioTO.isEmpty()) {
					UbicationRDTO ubicationRDTO = new UbicationRDTO();
					ubicationRDTO.setCode(logicUbicacioTO.get(0).getCodiUbicacio());
					ubicationRDTO.setDateStart(logicUbicacioTO.get(0).getDataFi());
					ubicationRDTO.setDateEnd(logicUbicacioTO.get(0).getDataInici());
					ubicationRDTO.setPosition(logicUbicacioTO.get(0).getPosicio());
					logicalModelRDTO.setUbicationRDTO(ubicationRDTO);
				}

				if (lstMobiliariFisicTO != null && !lstMobiliariFisicTO.isEmpty()) {
					List<PhysicalModelRDTO> toReturnList = new ArrayList<PhysicalModelRDTO>();

					for (MobiliariFisicTO mobiliariFisicTO : lstMobiliariFisicTO) {
						PhysicalModelRDTO physicalModelRDTO = new PhysicalModelRDTO();

						physicalModelRDTO.setCode(mobiliariFisicTO.getCodi());
						physicalModelRDTO.setCodeBrand(mobiliariFisicTO.getCodiMarca());
						physicalModelRDTO.setCodeFractionType(mobiliariFisicTO.getCodiTipusFraccio());
						physicalModelRDTO.setCodeInstallation(mobiliariFisicTO.getCodiInstalacio());
						physicalModelRDTO.setCodeRFID(mobiliariFisicTO.getCodiRFID());
						physicalModelRDTO.setCodeState(mobiliariFisicTO.getCodiEstatMCF());
						physicalModelRDTO.setCodeType(mobiliariFisicTO.getCodiTipusMCF());
						physicalModelRDTO.setCodeContract(mobiliariFisicTO.getCodiContractista());
						physicalModelRDTO.setDateStart(mobiliariFisicTO.getDataInici());
						physicalModelRDTO.setDateEnd(mobiliariFisicTO.getDataFi());
						toReturnList.add(physicalModelRDTO);
					}

					logicalModelRDTO.setListPhysicalModelRDTO(toReturnList);
				}
				LogicalModel logicalModel = findByCode(logicalModelBDTO);
				if (logicalModel != null) {
					logicalModelRDTO.setCodeContract(logicalModel.getCodeContract());
					logicalModelRDTO.setReasonChange(logicalModel.getReasonChange());
					logicalModelRDTO.setDateInsert(logicalModel.getDateInsert());
					logicalModelRDTO.setDateUpdate(logicalModel.getDateUpdate());
					logicalModelRDTO.setDateDelete(logicalModel.getDateDelete());
					logicalModelRDTO.setUserInsert(logicalModel.getUserInsert());
					logicalModelRDTO.setUserUpdate(logicalModel.getUserUpdate());
					logicalModelRDTO.setUserDelete(logicalModel.getUserDelete());
				}

				returnRDTO.setCode(response.getReadMobiliariLogicResults().get(0).getResult().getResultCode());
				returnRDTO.setMessage(response.getReadMobiliariLogicResults().get(0).getResult().getResultMessage());
				
				if (returnRDTO.getCode().endsWith(ReturnGapConstants.READ_SUCCESS)) {
					
					returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
					returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
				}
				
				result.setReturnRDTO(returnRDTO);
				result.setLogicalModelRDTO(logicalModelRDTO);

			} else {

				returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC);
				
				result.setReturnRDTO(returnRDTO);
				result.setLogicalModelRDTO(logicalModelRDTO);

			}

		} catch (Exception e) {
			returnRDTO.setCode(ReturnEnum.CODE_ERROR_GENERIC.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_ERROR_GENERIC.getMessage());
			result.setReturnRDTO(returnRDTO);
			result.setLogicalModelRDTO(logicalModelRDTO);
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, logicalModelRDTO.toString()), e);
		}

		logger.info(LogsConstants.LOG_END);
		return result;
	}

	private LogicalModel findByCode(LogicalModelBDTO logicalModelBDTO) {
		LogicalModel logicalModelIn = new LogicalModel();
		logicalModelIn.setCode(logicalModelBDTO.getCode());
		LogicalModel logicalModelOut = myBatisTemplate.execute(LogicalModelDao.class,
				new MyBatisDaoCallback<LogicalModel>() {
					@Override
					public LogicalModel execute(MyBatisDao dao) {
						return ((LogicalModelDao) dao).select(logicalModelIn);
					}
				});
		return logicalModelOut;
	}

	private LogicalModel logicalModelBDTOToLogicalModel(LogicalModelBDTO logicalModelBDTO) throws ImiException {

		LogicalModel logicalModel = new LogicalModel();

		try {

			BeanUtils.copyProperties(logicalModelBDTO, logicalModel);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ERROR_BDTO_TO_OBJECT, UbicationBDTO.class.getSimpleName(),
					logicalModelBDTO, PhysicalModel.class.getSimpleName(), logicalModel, ex));
			throw new ImiException(
					String.format(LogsConstants.LOG_ERROR_BDTO_TO_OBJECT, UbicationBDTO.class.getSimpleName(),
							logicalModelBDTO, PhysicalModel.class.getSimpleName(), logicalModel, ex));
		}

		return logicalModel;
	}

	@Override
	public ReturnLogicalModelMassiveRDTO selectMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnLogicalModelMassiveRDTO returnMassiveRDTO = new ReturnLogicalModelMassiveRDTO();
		List<LogicalModelMassiveRDTO> logicalModelMassiveRDTOs = new ArrayList<>();
		returnRDTO = new ReturnRDTO();

		try {

			logicalModelMassiveRDTOs = getMclMassive(queryParameterBDTO);

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnMassiveRDTO.setReturnRDTO(returnRDTO);
		returnMassiveRDTO.setLogicalModelMassiveRDTOs(logicalModelMassiveRDTOs);
		logger.info(LogsConstants.LOG_END);
		return returnMassiveRDTO;
	}

	@Override
	public ReturnLogicalModelDetailedRDTO selectDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnLogicalModelDetailedRDTO returnLogicalModelDetailedRDTO = new ReturnLogicalModelDetailedRDTO();
		LogicalModelDetailedRDTO logicalModelDetailedRDTO = new LogicalModelDetailedRDTO();
		returnRDTO = new ReturnRDTO();

		try {

			logicalModelDetailedRDTO = getMclDetailed(queryParameterBDTO);

		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnLogicalModelDetailedRDTO.setReturnRDTO(returnRDTO);
		returnLogicalModelDetailedRDTO.setLogicalModelDetailedRDTO(logicalModelDetailedRDTO);
		logger.info(LogsConstants.LOG_END);
		return returnLogicalModelDetailedRDTO;
	}

	public List<LogicalModelMassiveRDTO> getMclMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		List<LogicalModelMassiveRDTO> logicalModelMassiveRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<MclGap> mclGaps = myBatisTemplateGap.execute(LogicalModelGapDao.class,
					new MyBatisDaoCallbackGap<List<MclGap>>() {
						@Override
						public List<MclGap> execute(MyBatisDaoGap dao) {
							return ((LogicalModelGapDao) dao).selectMclMassive(map);
						}
					});

			if (mclGaps != null && !mclGaps.isEmpty()) {
				logicalModelMassiveRDTOs = LogicalModelMassiveGapConvert.object2rdto(mclGaps);
			}
		} catch (Exception e) {			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return logicalModelMassiveRDTOs;
	}

	public LogicalModelDetailedRDTO getMclDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		LogicalModelDetailedRDTO logicalModelDetailedRDTO = new LogicalModelDetailedRDTO();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<MclGap> mclDetailedGaps = myBatisTemplateGap.execute(LogicalModelGapDao.class,
					new MyBatisDaoCallbackGap<List<MclGap>>() {
						@Override
						public List<MclGap> execute(MyBatisDaoGap dao) {
							return ((LogicalModelGapDao) dao).selectMcl(map);
						}
					});
	
			if (mclDetailedGaps != null && !mclDetailedGaps.isEmpty()) {
				logicalModelDetailedRDTO = LogicalModelDetailedGapConvert.object2rdto(mclDetailedGaps.get(0));
				logicalModelDetailedRDTO.setCodeUser(queryParameterBDTO.getCodeUser());
				
				List<LogicalModel> logicalModels = myBatisTemplate.execute(LogicalModelDao.class,
					new MyBatisDaoCallback<List<LogicalModel>>() {
						@Override
						public List<LogicalModel> execute(MyBatisDao dao) {
							return ((LogicalModelDao) dao).selectLogicalModel(map);
						}
					});
				
				if(logicalModels != null && !logicalModels.isEmpty()) {
					LogicalModel logicalModel = logicalModels.get(0);
					logicalModelDetailedRDTO.setReasonChange(logicalModel.getReasonChange());
				}
				
				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}else {
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
						MessageContansts.MSG_ENTITY_LOGICAL_MODEL, queryParameterBDTO.getCode()));
			}
		} catch (Exception e) {			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return logicalModelDetailedRDTO;
	}

}
