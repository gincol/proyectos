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

import es.bcn.imi.framework.vigia.inventari.business.LogicalModelService;
import es.bcn.imi.framework.vigia.inventari.business.PhysicalModelService;
import es.bcn.imi.framework.vigia.inventari.business.SensorService;
import es.bcn.imi.framework.vigia.inventari.integration.orm.dao.PhysicalModelDao;
import es.bcn.imi.framework.vigia.inventari.integration.orm.dao.gap.PhysicalModelGapDao;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.integration.gap.inventary.aggregateamortization.response.CreateAmortAgregadaResponse;
import es.bcn.vigia.fmw.integration.gap.inventary.aggregateamortization.service.IntegrationGapAggregateAmortizationService;
import es.bcn.vigia.fmw.integration.gap.inventary.physicalmodel.service.IntegrationGapPhysicalModelService;
import es.bcn.vigia.fmw.integration.gap.inventary.physicalmodel.to.response.CreateMobiliariFisicResponse;
import es.bcn.vigia.fmw.integration.gap.inventary.physicalmodel.to.response.UpdateMobiliariFisicResponse;
import es.bcn.vigia.fmw.libcommons.business.dto.AggregateAmortizationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.LogicalModelBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.PhysicalModelBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.SensorBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.MessageContansts;
import es.bcn.vigia.fmw.libcommons.constants.ReturnGapConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.PhysicalModel;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.McfGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.PhysicalModelAmortizationGap;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.PhysicalModelAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.PhysicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.PhysicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.convert.LogicalModelConvert;
import es.bcn.vigia.fmw.libutils.convert.PhysicalModelAmortizationGapConvert;
import es.bcn.vigia.fmw.libutils.convert.PhysicalModelConvert;
import es.bcn.vigia.fmw.libutils.convert.PhysicalModelDetailedGapConvert;
import es.bcn.vigia.fmw.libutils.convert.PhysicalModelMassiveGapConvert;
import es.bcn.vigia.fmw.libutils.convert.SensorConvert;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_PHYSICAL_MODEL)
public class PhysicalModelServiceImpl implements PhysicalModelService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INT_GAP_PHYSICAL_MODEL)
	IntegrationGapPhysicalModelService service;
	
	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INT_GAP_AGGREGATE_AMORTIZATION)
	IntegrationGapAggregateAmortizationService aggregateAmortizationService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_LOGICAL_MODEL)
	private LogicalModelService logicalModelService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_SENSOR)
	private SensorService sensorService;

	private ReturnRDTO returnRDTO;
	
	private String isCode;

	private String isMessage;

	@Override
	public ReturnRDTO insert(PhysicalModelBDTO physicalModelBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();
		try {

			CreateMobiliariFisicResponse response = service.create(physicalModelBDTO);

			if (!response.getCreateMobiliariFisicResults().isEmpty()) {

				returnRDTO.setCode(response.getCreateMobiliariFisicResults().get(0).getResult().getResultCode());
				returnRDTO.setMessage(response.getCreateMobiliariFisicResults().get(0).getResult().getResultMessage());

			} else {

				returnRDTO.setCode(ReturnEnum.CODE_ERROR_GENERIC.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_ERROR_GENERIC.getMessage());

			}

			if (returnRDTO.getCode().endsWith(ReturnGapConstants.CREATE_SUCCESS)) {
				
				try {

					updateLogicalModel(physicalModelBDTO);
	
					final PhysicalModel physicalModel = PhysicalModelConvert.bdto2object(physicalModelBDTO);
					myBatisTemplate.execute(PhysicalModelDao.class, new MyBatisDaoCallback<Void>() {
	
						@Override
						public Void execute(MyBatisDao dao) {
							((PhysicalModelDao) dao).insert(physicalModel);
							((PhysicalModelDao) dao).insertSensors(physicalModel);
							return null;
						}
					});
					
					returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription());
					returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage());
					
				}catch (Exception e) {
					logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, physicalModelBDTO), e);			
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getCodeDescription());
					returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_FMW));
					return returnRDTO;
				}
			}

		} catch (Exception e) {

			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC);

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, physicalModelBDTO.toString()), e);

		}

		logger.info(LogsConstants.LOG_END);
		return returnRDTO;
	}

	@Override
	public ReturnRDTO update(PhysicalModelBDTO physicalModelBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {

			UpdateMobiliariFisicResponse response = service.update(physicalModelBDTO);

			if (!response.getUpdateMobiliariFisicResults().isEmpty()) {

				returnRDTO.setCode(response.getUpdateMobiliariFisicResults().get(0).getResult().getResultCode());
				returnRDTO.setMessage(response.getUpdateMobiliariFisicResults().get(0).getResult().getResultMessage());

			} else {

				returnRDTO.setCode(ReturnEnum.CODE_ERROR_GENERIC.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_ERROR_GENERIC.getMessage());

			}

			if (returnRDTO.getCode().endsWith(ReturnGapConstants.UPDATE_SUCCESS)) {
				
				try {

					final PhysicalModel physicalModel = PhysicalModelConvert.bdto2object(physicalModelBDTO);
					myBatisTemplate.execute(PhysicalModelDao.class, new MyBatisDaoCallback<Void>() {
	
						@Override
						public Void execute(MyBatisDao dao) {
							((PhysicalModelDao) dao).update(physicalModel);
							((PhysicalModelDao) dao).insert(physicalModel);
							((PhysicalModelDao) dao).insertSensors(physicalModel);
							return null;
						}
					});
					
					returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_UPDATED.getCodeDescription());
					returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_UPDATED.getMessage());
				}catch (Exception e) {
					logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, physicalModelBDTO), e);			
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_UPDATE_NOT_COMPLETE.getCodeDescription());
					returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_UPDATE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_FMW));
					return returnRDTO;
				}
			}

		} catch (Exception e) {

			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC);

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, physicalModelBDTO.toString()), e);

		}

		logger.info(LogsConstants.LOG_END);
		return returnRDTO;
	}

	@Override
	public ReturnRDTO delete(PhysicalModelBDTO physicalModelBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {

			UpdateMobiliariFisicResponse response = service.update(physicalModelBDTO);

			if (!response.getUpdateMobiliariFisicResults().isEmpty()) {

				returnRDTO.setCode(response.getUpdateMobiliariFisicResults().get(0).getResult().getResultCode());
				returnRDTO.setMessage(response.getUpdateMobiliariFisicResults().get(0).getResult().getResultMessage());

			} else {

				returnRDTO.setCode(ReturnEnum.CODE_ERROR_GENERIC.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_ERROR_GENERIC.getMessage());

			}

			if (returnRDTO.getCode().endsWith(ReturnGapConstants.UPDATE_SUCCESS)) {
				
				try {

					final PhysicalModel physicalModel = PhysicalModelConvert.bdto2object(physicalModelBDTO);
					myBatisTemplate.execute(PhysicalModelDao.class, new MyBatisDaoCallback<Void>() {
	
						@Override
						public Void execute(MyBatisDao dao) {
							((PhysicalModelDao) dao).delete(physicalModel);
							return null;
						}
					});
					
					returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_REMOVED.getCodeDescription());
					returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_REMOVED.getMessage());
				}catch (Exception e) {
					logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, physicalModelBDTO), e);			
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_DELETE_NOT_COMPLETE.getCodeDescription());
					returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_DELETE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_FMW));
					return returnRDTO;
				}

			}

		} catch (Exception e) {
			
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC);
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, physicalModelBDTO.toString()), e);

		}

		logger.info(LogsConstants.LOG_END);
		return returnRDTO;
	}

	private void updateLogicalModel(PhysicalModelBDTO physicalModelBDTO) throws ImiException {
		if (physicalModelBDTO.getCodeMCL() != null) {
			LogicalModelBDTO logicalModelBDTO = new LogicalModelBDTO();
			logicalModelBDTO.setCode(physicalModelBDTO.getCodeMCL());
			logicalModelBDTO.setCodeContract(physicalModelBDTO.getCodeContract());
			ReturnLogicalModelRDTO returnLogicalModelRDTO = logicalModelService.select(logicalModelBDTO);

			LogicalModelRDTO logicalModelRDTO = returnLogicalModelRDTO.getLogicalModelRDTO();

			PhysicalModelRDTO physicalModelRDTO = new PhysicalModelRDTO();
			physicalModelRDTO.setCode(physicalModelBDTO.getCode());

			List<PhysicalModelRDTO> list = new ArrayList<PhysicalModelRDTO>();
			list.add(physicalModelRDTO);
			logicalModelRDTO.setListPhysicalModelRDTO(list);
			LogicalModelBDTO logicalModelBDTOFinal = LogicalModelConvert.rdto2bto(logicalModelRDTO);
			logicalModelService.update(logicalModelBDTOFinal);
		}

	}

	@Override
	public ReturnRDTO insert(AggregateAmortizationBDTO aggregateAmortizationBDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();
		
		try {
			
			CreateAmortAgregadaResponse response = aggregateAmortizationService.create(aggregateAmortizationBDTO);
			
			if(!response.getCreateAmortAgregadaResults().isEmpty()) {
				
				isCode 		= response.getCreateAmortAgregadaResults().get(0).getResult().getResultCode();
				isMessage 	= response.getCreateAmortAgregadaResults().get(0).getResult().getResultMessage();
			}else {
				
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
		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, aggregateAmortizationBDTO.toString()), e);
			 throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, aggregateAmortizationBDTO.toString()), e);
		}
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);
		
		return returnRDTO;
	}

	@Override
	public ReturnPhysicalModelMassiveRDTO selectMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnPhysicalModelMassiveRDTO returnMassiveRDTO = new ReturnPhysicalModelMassiveRDTO();
		List<PhysicalModelMassiveRDTO> mclMassiveRDTOs = new ArrayList<>();
		returnRDTO = new ReturnRDTO();

		try {

			mclMassiveRDTOs = getPhysicalModelMassive(queryParameterBDTO);

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnMassiveRDTO.setReturnRDTO(returnRDTO);
		returnMassiveRDTO.setPhysicalModelMassiveRDTOs(mclMassiveRDTOs);
		logger.info(LogsConstants.LOG_END);
		return returnMassiveRDTO;
	}

	@Override
	public ReturnPhysicalModelDetailedRDTO selectDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnPhysicalModelDetailedRDTO returnPhysicalModelDetailedRDTO = new ReturnPhysicalModelDetailedRDTO();
		PhysicalModelDetailedRDTO mclDetailedRDTO = new PhysicalModelDetailedRDTO();
		returnRDTO = new ReturnRDTO();

		try {

			mclDetailedRDTO = getPhysicalModelDetailed(queryParameterBDTO);

		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnPhysicalModelDetailedRDTO.setReturnRDTO(returnRDTO);
		returnPhysicalModelDetailedRDTO.setPhysicalModelDetailedRDTO(mclDetailedRDTO);
		logger.info(LogsConstants.LOG_END);
		return returnPhysicalModelDetailedRDTO;
	}

	@Override
	public ReturnPhysicalModelAmortizationRDTO selectAmortization(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnPhysicalModelAmortizationRDTO returnPhysicalModelAmortizationRDTO = new ReturnPhysicalModelAmortizationRDTO();
		List<PhysicalModelAmortizationRDTO> physicalModelAmortizationRDTOs = new ArrayList<>();
		returnRDTO = new ReturnRDTO();

		try {

			physicalModelAmortizationRDTOs = getPhysicalModelAmortization(queryParameterBDTO);

		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnPhysicalModelAmortizationRDTO.setReturnRDTO(returnRDTO);
		returnPhysicalModelAmortizationRDTO.setPhysicalModelAmortizationRDTOs(physicalModelAmortizationRDTOs);
		logger.info(LogsConstants.LOG_END);
		return returnPhysicalModelAmortizationRDTO;
	}

	public List<PhysicalModelMassiveRDTO> getPhysicalModelMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		List<PhysicalModelMassiveRDTO> mclMassiveRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<McfGap> mclGaps = myBatisTemplateGap.execute(PhysicalModelGapDao.class,
					new MyBatisDaoCallbackGap<List<McfGap>>() {
						@Override
						public List<McfGap> execute(MyBatisDaoGap dao) {
							return ((PhysicalModelGapDao) dao).selectPhysicalModelMassive(map);
						}
					});

			if (mclGaps != null && !mclGaps.isEmpty()) {
				mclMassiveRDTOs = PhysicalModelMassiveGapConvert.object2rdto(mclGaps);
			}
		} catch (Exception e) {			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return mclMassiveRDTOs;
	}

	public PhysicalModelDetailedRDTO getPhysicalModelDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		PhysicalModelDetailedRDTO physicalModelDetailedRDTO = new PhysicalModelDetailedRDTO();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<McfGap> mclDetailedGaps = myBatisTemplateGap.execute(PhysicalModelGapDao.class,
					new MyBatisDaoCallbackGap<List<McfGap>>() {
						@Override
						public List<McfGap> execute(MyBatisDaoGap dao) {
							return ((PhysicalModelGapDao) dao).selectPhysicalModel(map);
						}
					});
	
			if (mclDetailedGaps != null && !mclDetailedGaps.isEmpty()) {
				physicalModelDetailedRDTO = PhysicalModelDetailedGapConvert.object2rdto(mclDetailedGaps.get(0));
				physicalModelDetailedRDTO.setCodeUser(queryParameterBDTO.getCodeUser());
				
				List<PhysicalModel> physicalModels = myBatisTemplate.execute(PhysicalModelDao.class,
						new MyBatisDaoCallback<List<PhysicalModel>>() {
							@Override
							public List<PhysicalModel> execute(MyBatisDao dao) {
								return ((PhysicalModelDao) dao).selectPhysicalModel(map);
							}
						});
				
				if(physicalModels != null && !physicalModels.isEmpty()) {
					PhysicalModel physicalModel = physicalModels.get(0);
					physicalModelDetailedRDTO.setReasonDeployment(physicalModel.getReasonChange());
				}

				List<SensorBDTO> sensorsBDTO = sensorService.selectByCodeMCF(map);
				physicalModelDetailedRDTO.setSensorRDTOs(SensorConvert.bdto2rdto(sensorsBDTO));
				
				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}else {
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
						MessageContansts.MSG_ENTITY_PHYSICAL_MODEL, queryParameterBDTO.getCode()));
			}
		} catch (Exception e) {			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return physicalModelDetailedRDTO;
	}

	public List<PhysicalModelAmortizationRDTO> getPhysicalModelAmortization(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		List<PhysicalModelAmortizationRDTO> physicalModelAmortizationRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<PhysicalModelAmortizationGap> physicalModelAmortizationGaps = myBatisTemplateGap.execute(PhysicalModelGapDao.class,
					new MyBatisDaoCallbackGap<List<PhysicalModelAmortizationGap>>() {
						@Override
						public List<PhysicalModelAmortizationGap> execute(MyBatisDaoGap dao) {
							return ((PhysicalModelGapDao) dao).selectPhysicalModelAmortization(map);
						}
					});
	
			if (physicalModelAmortizationGaps != null && !physicalModelAmortizationGaps.isEmpty()) {
				physicalModelAmortizationRDTOs = PhysicalModelAmortizationGapConvert.object2rdto(physicalModelAmortizationGaps);
				
				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}else {
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
						MessageContansts.MSG_ENTITY_PHYSICAL_MODEL, queryParameterBDTO.getCode()));
			}
		} catch (Exception e) {			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return physicalModelAmortizationRDTOs;
	}

}
