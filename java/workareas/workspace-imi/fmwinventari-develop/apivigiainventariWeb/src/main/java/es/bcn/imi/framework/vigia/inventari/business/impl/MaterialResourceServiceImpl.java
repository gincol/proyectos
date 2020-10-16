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

import es.bcn.imi.framework.vigia.inventari.business.MaterialResourceService;
import es.bcn.imi.framework.vigia.inventari.business.SensorService;
import es.bcn.imi.framework.vigia.inventari.integration.orm.dao.MaterialResourceDao;
import es.bcn.imi.framework.vigia.inventari.integration.orm.dao.MaterialResourceVehiclesDao;
import es.bcn.imi.framework.vigia.inventari.integration.orm.dao.gap.MaterialResourceGapDao;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.integration.gap.inventary.materialresource.to.response.CreateRecursMaterialResponse;
import es.bcn.vigia.fmw.integration.gap.inventary.materialresource.to.response.UpdateRecursMaterialResponse;
import es.bcn.vigia.fmw.integration.gap.inventary.materialresource.to.service.IntegrationGapMaterialResourceService;
import es.bcn.vigia.fmw.libcommons.business.dto.MaterialResourceBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.SensorBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.MessageContansts;
import es.bcn.vigia.fmw.libcommons.constants.ReturnGapConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.MaterialResource;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.MaterialResourceVehicles;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.AmortizationBaseGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ApportionmentGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.DetailCertificationGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ExpenseGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.MaterialResourceGap;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AmortizationBaseRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ApportionmentRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DetailCertificationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ExpenseRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.BreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.MassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.MaterialResourceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.MaterialResourceVehicleDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.MaterialResourceVehicleExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.MaterialResourceVehiclePeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehiclePeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.convert.AmortizationBaseGapConvert;
import es.bcn.vigia.fmw.libutils.convert.ApportionmentGapConvert;
import es.bcn.vigia.fmw.libutils.convert.DetailCertificationGapConvert;
import es.bcn.vigia.fmw.libutils.convert.ExpenseGapConvert;
import es.bcn.vigia.fmw.libutils.convert.MaterialResourceConvert;
import es.bcn.vigia.fmw.libutils.convert.MaterialResourceDetailedConvert;
import es.bcn.vigia.fmw.libutils.convert.MaterialResourceMassiveConvert;
import es.bcn.vigia.fmw.libutils.convert.MaterialResourceVehicleDetailedConvert;
import es.bcn.vigia.fmw.libutils.convert.MaterialResourceVehicleMassiveConvert;
import es.bcn.vigia.fmw.libutils.convert.MaterialResourceVehiclePeriodConvert;
import es.bcn.vigia.fmw.libutils.convert.SensorConvert;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_MATERIAL_RESOURCE)
public class MaterialResourceServiceImpl implements MaterialResourceService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INT_GAP_MATERIAL_RESOURCE)
	IntegrationGapMaterialResourceService service;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_SENSOR)
	private SensorService sensorService;

	private ReturnRDTO returnRDTO;

	private String isCode;

	private String isMessage;

	@Override
	public ReturnRDTO insert(MaterialResourceBDTO materialResourceBDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			if (materialResourceBDTO.getRegistration() != null) {

				CreateRecursMaterialResponse response = service.create(materialResourceBDTO);

				if (!response.getCreateRecursMaterialResults().isEmpty()) {

					isCode = response.getCreateRecursMaterialResults().get(0).getResult().getResultCode();
					isMessage = response.getCreateRecursMaterialResults().get(0).getResult().getResultMessage();

				} else {

					isCode = ReturnEnum.CODE_ERROR_GAP_REGISTERED.getCodeDescription();
					isMessage = ReturnEnum.CODE_ERROR_GAP_REGISTERED.getMessage();

				}

			}

			if (materialResourceBDTO.getRegistration() == null || isCode.endsWith(ReturnGapConstants.CREATE_SUCCESS)) {

				try {
					final MaterialResource materialResource = MaterialResourceConvert.bdto2object(materialResourceBDTO);
					myBatisTemplate.execute(MaterialResourceDao.class, new MyBatisDaoCallback<Void>() {
	
						@Override
						public Void execute(MyBatisDao dao) {
							((MaterialResourceDao) dao).insert(materialResource);
							((MaterialResourceDao) dao).insertSensors(materialResource);
							return null;
						}
					});
	
					if (materialResourceBDTO.getRegistration() != null) {
						createVehicle(materialResourceBDTO);
					}
	
					isCode = ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription();
					isMessage = ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage();
				}catch (Exception e) {
					logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, materialResourceBDTO), e);			
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getCodeDescription());
					returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_FMW));
					return returnRDTO;
				}

			}

			if (!isCode.equals(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription())
					&& !isCode.endsWith(ReturnGapConstants.CREATE_SUCCESS)) {

				if (!isCode.equals(ReturnEnum.CODE_ERROR_GAP_REGISTERED.getCodeDescription())) {

					isCode = ReturnEnum.CODE_ERROR_GAP_GENERIC_VALIDATE.getCodeDescription();
				}
			}

		} catch (Exception e) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, materialResourceBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, materialResourceBDTO.toString()),
					e);

		}

		logger.info(LogsConstants.LOG_END);

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO update(MaterialResourceBDTO materialResourceBDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			if (materialResourceBDTO.getRegistration() != null) {

				UpdateRecursMaterialResponse response = service.update(materialResourceBDTO);

				if (!response.getUpdateRecursMaterialResults().isEmpty()) {

					isCode = response.getUpdateRecursMaterialResults().get(0).getResult().getResultCode();
					isMessage = response.getUpdateRecursMaterialResults().get(0).getResult().getResultMessage();

				} else {

					isCode = ReturnEnum.CODE_ERROR_GAP_UPDATED.getCodeDescription();
					isMessage = ReturnEnum.CODE_ERROR_GAP_UPDATED.getMessage();
				}
			}

			if (materialResourceBDTO.getRegistration() == null || isCode.endsWith(ReturnGapConstants.UPDATE_SUCCESS)) {

				try {
					final MaterialResource materialResource = MaterialResourceConvert.bdto2object(materialResourceBDTO);
					myBatisTemplate.execute(MaterialResourceDao.class, new MyBatisDaoCallback<Void>() {
	
						@Override
						public Void execute(MyBatisDao dao) {
							((MaterialResourceDao) dao).update(materialResource);
							((MaterialResourceDao) dao).insert(materialResource);
							((MaterialResourceDao) dao).insertSensors(materialResource);
							return null;
						}
					});
	
					if (materialResourceBDTO.getRegistration() != null) {
						updateVehicle(materialResourceBDTO);
					}
	
					isCode = ReturnEnum.CODE_SUCCESS_UPDATED.getCodeDescription();
					isMessage = ReturnEnum.CODE_SUCCESS_UPDATED.getMessage();
					
				}catch (Exception e) {
					logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, materialResourceBDTO), e);			
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_UPDATE_NOT_COMPLETE.getCodeDescription());
					returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_UPDATE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_FMW));
					return returnRDTO;
				}

			}

			if (!isCode.equals(ReturnEnum.CODE_SUCCESS_UPDATED.getCodeDescription())
					&& !isCode.endsWith(ReturnGapConstants.UPDATE_SUCCESS)) {

				if (!isCode.equals(ReturnEnum.CODE_ERROR_GAP_UPDATED.getCodeDescription())) {

					isCode = ReturnEnum.CODE_ERROR_GAP_GENERIC_VALIDATE.getCodeDescription();
				}
			}

		} catch (Exception e) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, materialResourceBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, materialResourceBDTO.toString()),
					e);

		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO delete(MaterialResourceBDTO materialResourceBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {

			if (materialResourceBDTO.getRegistration() != null) {

				UpdateRecursMaterialResponse response = service.update(materialResourceBDTO);

				if (!response.getUpdateRecursMaterialResults().isEmpty()) {

					isCode = response.getUpdateRecursMaterialResults().get(0).getResult().getResultCode();
					isMessage = response.getUpdateRecursMaterialResults().get(0).getResult().getResultMessage();

				} else {

					isCode = ReturnEnum.CODE_ERROR_GAP_REMOVED.getCodeDescription();
					isMessage = ReturnEnum.CODE_ERROR_GAP_REMOVED.getMessage();
				}
			}

			if (materialResourceBDTO.getRegistration() == null || isCode.endsWith(ReturnGapConstants.UPDATE_SUCCESS)) {

				try {
				
					final MaterialResource materialResource = MaterialResourceConvert.bdto2object(materialResourceBDTO);
					MaterialResourceVehicles materialResourceVehicles = new MaterialResourceVehicles();
	
					BeanUtils.copyProperties(materialResourceBDTO, materialResourceVehicles);
					myBatisTemplate.execute(MaterialResourceDao.class, new MyBatisDaoCallback<Void>() {
	
						@Override
						public Void execute(MyBatisDao dao) {
							((MaterialResourceDao) dao).delete(materialResource);
							((MaterialResourceVehiclesDao) dao).delete(materialResourceVehicles);
							return null;
						}
					});
	
					isCode = ReturnEnum.CODE_SUCCESS_REMOVED.getCodeDescription();
					isMessage = ReturnEnum.CODE_SUCCESS_REMOVED.getMessage();
					
				}catch (Exception e) {
					logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, materialResourceBDTO), e);			
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_DELETE_NOT_COMPLETE.getCodeDescription());
					returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_DELETE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_FMW));
					return returnRDTO;
				}

			}

			if (!isCode.equals(ReturnEnum.CODE_SUCCESS_REMOVED.getCodeDescription())
					&& !isCode.endsWith(ReturnGapConstants.UPDATE_SUCCESS)) {

				if (!isCode.equals(ReturnEnum.CODE_ERROR_GAP_REMOVED.getCodeDescription())) {

					isCode = ReturnEnum.CODE_ERROR_GAP_GENERIC_VALIDATE.getCodeDescription();
				}
			}

		} catch (Exception e) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, materialResourceBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, materialResourceBDTO.toString()),
					e);
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}

	private void createVehicle(MaterialResourceBDTO materialResourceBDTO) {

		MaterialResourceVehicles materialResourceVehicles = new MaterialResourceVehicles();

		BeanUtils.copyProperties(materialResourceBDTO, materialResourceVehicles);
		myBatisTemplate.execute(MaterialResourceVehiclesDao.class, new MyBatisDaoCallback<Void>() {

			@Override
			public Void execute(MyBatisDao dao) {
				((MaterialResourceVehiclesDao) dao).insert(materialResourceVehicles);
				return null;
			}
		});

	}

	private void updateVehicle(MaterialResourceBDTO materialResourceBDTO) {

		MaterialResourceVehicles materialResourceVehicles = new MaterialResourceVehicles();

		BeanUtils.copyProperties(materialResourceBDTO, materialResourceVehicles);
		myBatisTemplate.execute(MaterialResourceVehiclesDao.class, new MyBatisDaoCallback<Void>() {

			@Override
			public Void execute(MyBatisDao dao) {
				((MaterialResourceVehiclesDao) dao).update(materialResourceVehicles);
				((MaterialResourceVehiclesDao) dao).insert(materialResourceVehicles);
				return null;
			}
		});
	}

	@Override
	public ReturnMassiveRDTO selectMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		List<MassiveRDTO> massiveRDTOs = new ArrayList<>();
		returnRDTO = new ReturnRDTO();

		try {

			massiveRDTOs = getMaterialResourceMassive(queryParameterBDTO);

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
	
	public ReturnMaterialResourceDetailedRDTO selectDetailed(QueryParameterBDTO queryParameterBDTO)
			throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnMaterialResourceDetailedRDTO returnMaterialResourceDetailedRDTO = new ReturnMaterialResourceDetailedRDTO();
		MaterialResourceDetailedRDTO materialResourceDetailedRDTO = new MaterialResourceDetailedRDTO();
		returnRDTO = new ReturnRDTO();

		try {

			materialResourceDetailedRDTO = getMaterialResourceDetailed(queryParameterBDTO);
		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnMaterialResourceDetailedRDTO.setReturnRDTO(returnRDTO);
		returnMaterialResourceDetailedRDTO.setMaterialResourceDetailedRDTO(materialResourceDetailedRDTO);
		logger.info(LogsConstants.LOG_END);
		return returnMaterialResourceDetailedRDTO;
	}

	@Override
	public ReturnMassiveRDTO selectVehicleMassive(QueryParameterBDTO queryParameterBDTO)
			throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		List<MassiveRDTO> massiveRDTOs = new ArrayList<>();
		returnRDTO = new ReturnRDTO();

		try {

			massiveRDTOs = getMaterialResourceVehicleMassive(queryParameterBDTO);

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
	public ReturnMaterialResourceVehicleDetailedRDTO selectVehicleDetailed(QueryParameterBDTO queryParameterBDTO)
			throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnMaterialResourceVehicleDetailedRDTO returnMaterialResourceVehicleDetailedRDTO = new ReturnMaterialResourceVehicleDetailedRDTO();
		MaterialResourceVehicleDetailedRDTO materialResourceDetailedRDTO = new MaterialResourceVehicleDetailedRDTO();
		returnRDTO = new ReturnRDTO();

		try {

			materialResourceDetailedRDTO = getMaterialResourceVehicleDetailed(queryParameterBDTO);

		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnMaterialResourceVehicleDetailedRDTO.setReturnRDTO(returnRDTO);
		returnMaterialResourceVehicleDetailedRDTO.setMaterialResourceVehicleDetailedRDTO(materialResourceDetailedRDTO);
		logger.info(LogsConstants.LOG_END);
		return returnMaterialResourceVehicleDetailedRDTO;
	}

	public List<MassiveRDTO> getMaterialResourceMassive(QueryParameterBDTO queryParameterBDTO)
			throws ImiException {
		List<MassiveRDTO> massiveRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<MaterialResource> materialResources = myBatisTemplate.execute(MaterialResourceDao.class,
					new MyBatisDaoCallback<List<MaterialResource>>() {
						@Override
						public List<MaterialResource> execute(MyBatisDao dao) {
							return ((MaterialResourceDao) dao).selectMaterialResource(map);
						}
					});
	
			if (materialResources != null && !materialResources.isEmpty()) {
				massiveRDTOs = MaterialResourceMassiveConvert.object2rdto(materialResources);
	
			}
		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return massiveRDTOs;
	}

	public MaterialResourceDetailedRDTO getMaterialResourceDetailed(QueryParameterBDTO queryParameterBDTO)
			throws ImiException {
		MaterialResourceDetailedRDTO materialResourceDetailedRDTO = new MaterialResourceDetailedRDTO();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<MaterialResource> materialResourceDetaileds = myBatisTemplate.execute(MaterialResourceDao.class,
					new MyBatisDaoCallback<List<MaterialResource>>() {
						@Override
						public List<MaterialResource> execute(MyBatisDao dao) {
							return ((MaterialResourceDao) dao).selectMaterialResource(map);
						}
					});
	
			if (materialResourceDetaileds != null && !materialResourceDetaileds.isEmpty()) {
				materialResourceDetailedRDTO = MaterialResourceDetailedConvert
						.object2rdto(materialResourceDetaileds.get(0));
				List<SensorBDTO> sensorsBDTO = sensorService.selectByCodeRRMM(map);
				materialResourceDetailedRDTO.setSensorsRDTO(SensorConvert.bdto2rdto(sensorsBDTO));
				
				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());	
			}else {
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
						MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE_NO_VEHICLE, queryParameterBDTO.getCode()));
			}
		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return materialResourceDetailedRDTO;
	}

	public List<MassiveRDTO> getMaterialResourceVehicleMassive(QueryParameterBDTO queryParameterBDTO)
			throws ImiException {
		List<MassiveRDTO> massiveRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<MaterialResourceGap> materialResourceGaps = myBatisTemplateGap.execute(MaterialResourceGapDao.class,
					new MyBatisDaoCallbackGap<List<MaterialResourceGap>>() {
						@Override
						public List<MaterialResourceGap> execute(MyBatisDaoGap dao) {
							return ((MaterialResourceGapDao) dao).selectMaterialResourceVehicleMassive(map);
						}
					});
	
			if (materialResourceGaps != null && !materialResourceGaps.isEmpty()) {
				massiveRDTOs = MaterialResourceVehicleMassiveConvert.object2rdto(materialResourceGaps);
			}
		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return massiveRDTOs;
	}

	public MaterialResourceVehicleDetailedRDTO getMaterialResourceVehicleDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		MaterialResourceVehicleDetailedRDTO materialResourceVehicleDetailedRDTO = null;
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<MaterialResourceGap> materialResourceGaps = myBatisTemplateGap.execute(MaterialResourceGapDao.class,
					new MyBatisDaoCallbackGap<List<MaterialResourceGap>>() {
						@Override
						public List<MaterialResourceGap> execute(MyBatisDaoGap dao) {
							return ((MaterialResourceGapDao) dao).selectMaterialResourceVehicle(map);
						}
					});
	
			if (materialResourceGaps != null && !materialResourceGaps.isEmpty()) {
				materialResourceVehicleDetailedRDTO = MaterialResourceVehicleDetailedConvert.object2rdto(materialResourceGaps.get(0));
				materialResourceVehicleDetailedRDTO.setCodeUser(queryParameterBDTO.getCodeUser());
				
				List<MaterialResourceVehicles> materialResourceVehicles = myBatisTemplate.execute(MaterialResourceDao.class,
						new MyBatisDaoCallback<List<MaterialResourceVehicles>>() {
							@Override
							public List<MaterialResourceVehicles> execute(MyBatisDao dao) {
								return ((MaterialResourceDao) dao).selectMaterialResourceVehicle(map);
							}
						});
				
				if(materialResourceVehicles != null && !materialResourceVehicles.isEmpty()) {
					MaterialResourceVehicles materialResourceVehicle = materialResourceVehicles.get(0);
					materialResourceVehicleDetailedRDTO.setEuroStandard(materialResourceVehicle.getEuroStandard());
					materialResourceVehicleDetailedRDTO.setTypeEmissions(materialResourceVehicle.getTypeEmissions());
					materialResourceVehicleDetailedRDTO.setLoadingTechnology(materialResourceVehicle.getLoadingTechnology());
				}
				
				List<SensorBDTO> sensorsBDTO = sensorService.selectByCodeRRMM(map);
				List<ApportionmentRDTO> apportionmentRDTOs = getApportionments(queryParameterBDTO);
				List<DetailCertificationRDTO> expenseCertificatedRDTOs = getDetailCertificationExpenses(queryParameterBDTO);
				List<DetailCertificationRDTO> amortizationCertificatedRDTOs = getDetailCertificationAmortization(queryParameterBDTO);
				materialResourceVehicleDetailedRDTO.setSensorsRDTO(SensorConvert.bdto2rdto(sensorsBDTO));
				materialResourceVehicleDetailedRDTO.setPerformanceRDTOs(null);
				materialResourceVehicleDetailedRDTO.setDocumentarySupportRDTOs(null);
				materialResourceVehicleDetailedRDTO.setValuesVehicleRDTOs(null);
				materialResourceVehicleDetailedRDTO.setApportionmentRDTOs(apportionmentRDTOs);
				materialResourceVehicleDetailedRDTO.setExpenseCertificatedRDTOs(expenseCertificatedRDTOs);
				materialResourceVehicleDetailedRDTO.setAmortizationCertificatedRDTOs(amortizationCertificatedRDTOs);
				
				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}else {
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
						MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE, queryParameterBDTO.getCode()));
			}
		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return materialResourceVehicleDetailedRDTO;
	}

	public List<ApportionmentRDTO> getApportionments(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		List<ApportionmentRDTO> apportionmentRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<ApportionmentGap> apportionmentGaps = myBatisTemplateGap.execute(MaterialResourceGapDao.class,
					new MyBatisDaoCallbackGap<List<ApportionmentGap>>() {
						@Override
						public List<ApportionmentGap> execute(MyBatisDaoGap dao) {
							return ((MaterialResourceGapDao) dao).selectApportionmentsByRRMM(map);
						}
					});
	
			if (apportionmentGaps != null && !apportionmentGaps.isEmpty()) {
				apportionmentRDTOs = ApportionmentGapConvert.object2rdto(apportionmentGaps);
			}
		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return apportionmentRDTOs;
	}

	public List<DetailCertificationRDTO> getDetailCertificationAmortization(QueryParameterBDTO queryParameterBDTO)
			throws ImiException {
		List<DetailCertificationRDTO> certificationRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<DetailCertificationGap> certificationGaps = myBatisTemplateGap.execute(MaterialResourceGapDao.class,
					new MyBatisDaoCallbackGap<List<DetailCertificationGap>>() {
						@Override
						public List<DetailCertificationGap> execute(MyBatisDaoGap dao) {
							return ((MaterialResourceGapDao) dao).selectDetailCertificationAmortizationByRRMM(map);
						}
					});
	
			if (certificationGaps != null && !certificationGaps.isEmpty()) {
				certificationRDTOs = DetailCertificationGapConvert.object2rdto(certificationGaps);
			}
		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return certificationRDTOs;

	}

	public List<DetailCertificationRDTO> getDetailCertificationExpenses(QueryParameterBDTO queryParameterBDTO)
			throws ImiException {
		List<DetailCertificationRDTO> certificationRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<DetailCertificationGap> certificationGaps = myBatisTemplateGap.execute(MaterialResourceGapDao.class,
					new MyBatisDaoCallbackGap<List<DetailCertificationGap>>() {
						@Override
						public List<DetailCertificationGap> execute(MyBatisDaoGap dao) {
							return ((MaterialResourceGapDao) dao).selectDetailCertificationExpensesByRRMM(map);
						}
					});
	
			if (certificationGaps != null && !certificationGaps.isEmpty()) {
				certificationRDTOs = DetailCertificationGapConvert.object2rdto(certificationGaps);
			}
		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return certificationRDTOs;

	}

	@Override
	public ReturnRDTO insertExpense(MaterialResourceBDTO materialResourceBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		try {

				UpdateRecursMaterialResponse response = service.createExpense(materialResourceBDTO);

				if (!response.getUpdateRecursMaterialResults().isEmpty()) {
					
					isCode 		= response.getUpdateRecursMaterialResults().get(0).getResult().getResultCode();
					isMessage 	= response.getUpdateRecursMaterialResults().get(0).getResult().getResultMessage();

				} else {
					
					isCode 		= ReturnEnum.CODE_ERROR_GAP_UPDATED.getCodeDescription();
					isMessage 	= ReturnEnum.CODE_ERROR_GAP_UPDATED.getMessage();
				}
				
				if(isCode.endsWith(ReturnGapConstants.UPDATE_SUCCESS)) {
					
					isCode 		= ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription();
					isMessage 	= ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage();

				} else {

					if (!isCode.equals(ReturnEnum.CODE_ERROR_GAP_UPDATED.getCodeDescription())) {

						isCode 	= ReturnEnum.CODE_ERROR_GAP_GENERIC_VALIDATE.getCodeDescription();
					}
				}

		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, materialResourceBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, materialResourceBDTO.toString()),e);

		}
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);
		
		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertAmortizationBase(MaterialResourceBDTO materialResourceBDTO) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		try {

				UpdateRecursMaterialResponse response = service.createAmortizationBase(materialResourceBDTO);

				if (!response.getUpdateRecursMaterialResults().isEmpty()) {
					
					isCode 		= response.getUpdateRecursMaterialResults().get(0).getResult().getResultCode();
					isMessage 	= response.getUpdateRecursMaterialResults().get(0).getResult().getResultMessage();

				} else {
					
					isCode 		= ReturnEnum.CODE_ERROR_GAP_UPDATED.getCodeDescription();
					isMessage 	= ReturnEnum.CODE_ERROR_GAP_UPDATED.getMessage();
				}
				
				if(isCode.endsWith(ReturnGapConstants.UPDATE_SUCCESS)) {
					
					isCode 		= ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription();
					isMessage 	= ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage();

				} else {

					if (!isCode.equals(ReturnEnum.CODE_ERROR_GAP_UPDATED.getCodeDescription())) {

						isCode 	= ReturnEnum.CODE_ERROR_GAP_GENERIC_VALIDATE.getCodeDescription();
					}
				}

		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, materialResourceBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, materialResourceBDTO.toString()),e);

		}
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);
		
		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertApportionment(MaterialResourceBDTO materialResourceBDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		try {

				UpdateRecursMaterialResponse response = service.createApportionment(materialResourceBDTO);

				if (!response.getUpdateRecursMaterialResults().isEmpty()) {
					
					isCode 		= response.getUpdateRecursMaterialResults().get(0).getResult().getResultCode();
					isMessage 	= response.getUpdateRecursMaterialResults().get(0).getResult().getResultMessage();

				} else {
					
					isCode 		= ReturnEnum.CODE_ERROR_GAP_UPDATED.getCodeDescription();
					isMessage 	= ReturnEnum.CODE_ERROR_GAP_UPDATED.getMessage();
				}
				
				if(isCode.endsWith(ReturnGapConstants.UPDATE_SUCCESS)) {
					
					isCode 		= ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription();
					isMessage 	= ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage();

				} else {

					if (!isCode.equals(ReturnEnum.CODE_ERROR_GAP_UPDATED.getCodeDescription())) {

						isCode 	= ReturnEnum.CODE_ERROR_GAP_GENERIC_VALIDATE.getCodeDescription();
					}
				}

		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, materialResourceBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, materialResourceBDTO.toString()),e);

		}
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);
		
		return returnRDTO;
	}

	@Override
	public ReturnBreakdownAmortizationRDTO selectAmortization(QueryParameterBDTO queryParameterBDTO)
			throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnBreakdownAmortizationRDTO returnBreakdownAmortizationRDTO = new ReturnBreakdownAmortizationRDTO();
		BreakdownAmortizationRDTO breakdownAmortizationRDTO = new BreakdownAmortizationRDTO();
		returnRDTO = new ReturnRDTO();

		try {

			breakdownAmortizationRDTO = getMaterialResourceVehicleAmortization(queryParameterBDTO);

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnBreakdownAmortizationRDTO.setReturnRDTO(returnRDTO);
		returnBreakdownAmortizationRDTO.setBreakdownAmortizationRDTO(breakdownAmortizationRDTO);
		logger.info(LogsConstants.LOG_END);
		return returnBreakdownAmortizationRDTO;
	}

	@Override
	public ReturnMaterialResourceVehicleExpensesRDTO selectExpenses(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnMaterialResourceVehicleExpensesRDTO returnMaterialResourceVehicleExpensesRDTO = new ReturnMaterialResourceVehicleExpensesRDTO();
		MaterialResourceVehicleExpensesRDTO materialResourceVehicleExpensesRDTO = new MaterialResourceVehicleExpensesRDTO();
		returnRDTO = new ReturnRDTO();

		try {

			materialResourceVehicleExpensesRDTO = getMaterialResourceVehicleExpenses(queryParameterBDTO);

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnMaterialResourceVehicleExpensesRDTO.setReturnRDTO(returnRDTO);
		returnMaterialResourceVehicleExpensesRDTO.setMaterialResourceVehicleExpensesRDTO(materialResourceVehicleExpensesRDTO);
		logger.info(LogsConstants.LOG_END);
		return returnMaterialResourceVehicleExpensesRDTO;
	}

	@Override
	public ReturnMaterialResourceVehiclePeriodRDTO selectPeriod(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnMaterialResourceVehiclePeriodRDTO returnMaterialResourceVehiclePeriodRDTO = new ReturnMaterialResourceVehiclePeriodRDTO();
		MaterialResourceVehiclePeriodRDTO materialResourceVehiclePeriodRDTO = new MaterialResourceVehiclePeriodRDTO();
		returnRDTO = new ReturnRDTO();

		try {

			materialResourceVehiclePeriodRDTO = getPeriod(queryParameterBDTO);

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnMaterialResourceVehiclePeriodRDTO.setReturnRDTO(returnRDTO);
		returnMaterialResourceVehiclePeriodRDTO.setMaterialResourceVehiclePeriodRDTO(materialResourceVehiclePeriodRDTO);
		logger.info(LogsConstants.LOG_END);
		return returnMaterialResourceVehiclePeriodRDTO;
	}

	public MaterialResourceVehiclePeriodRDTO getPeriod(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		MaterialResourceVehiclePeriodRDTO materialResourceVehiclePeriodRDTO = new MaterialResourceVehiclePeriodRDTO();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<MaterialResourceGap> materialResourceVehicleGaps = myBatisTemplateGap.execute(MaterialResourceGapDao.class,
					new MyBatisDaoCallbackGap<List<MaterialResourceGap>>() {
						@Override
						public List<MaterialResourceGap> execute(MyBatisDaoGap dao) {
							return ((MaterialResourceGapDao) dao).selectMaterialResourceVehicle(map);
						}
					});
	
			if (materialResourceVehicleGaps != null && !materialResourceVehicleGaps.isEmpty()) {
				MaterialResourceGap materialResourceVehicleGap = materialResourceVehicleGaps.get(0);
	
				materialResourceVehiclePeriodRDTO = MaterialResourceVehiclePeriodConvert.object2rdto(materialResourceVehicleGap);
	
				List<ApportionmentRDTO> apportionmentsRDTO = getApportionments(queryParameterBDTO);
				List<DetailCertificationRDTO> certificationAmortizationRDTOs = getDetailCertificationAmortization(queryParameterBDTO);
				List<DetailCertificationRDTO> certificationExpensesRDTOs = getDetailCertificationExpenses(queryParameterBDTO);
	
				materialResourceVehiclePeriodRDTO.setApportionmentsRDTO(apportionmentsRDTO);
				materialResourceVehiclePeriodRDTO.setExpenseCertificatedRDTOs(certificationExpensesRDTOs);
				materialResourceVehiclePeriodRDTO.setAmortizationCertificatedRDTOs(certificationAmortizationRDTOs);
	
			}
		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return materialResourceVehiclePeriodRDTO;
	}
	


	public BreakdownAmortizationRDTO getMaterialResourceVehicleAmortization(QueryParameterBDTO queryParameterBDTO)
			throws ImiException {
		BreakdownAmortizationRDTO breakdownAmortizationRDTO = new BreakdownAmortizationRDTO();

		List<AmortizationBaseRDTO> amortizationBasesRDTO = getAmortizationBases(queryParameterBDTO);
		List<DetailCertificationRDTO> certificationRDTOs = getDetailCertificationAmortization(queryParameterBDTO);
		List<ApportionmentRDTO> apportionmentsRDTO = getApportionments(queryParameterBDTO);
		if (!apportionmentsRDTO.isEmpty()) {
			breakdownAmortizationRDTO.setApportionmentRDTOs(apportionmentsRDTO);
		}
		if (!certificationRDTOs.isEmpty()) {
			breakdownAmortizationRDTO.setCertificationRDTOs(certificationRDTOs);
		}
		breakdownAmortizationRDTO.setAmortizationBasesRDTO(amortizationBasesRDTO);

		return breakdownAmortizationRDTO;
	}

	public MaterialResourceVehicleExpensesRDTO getMaterialResourceVehicleExpenses(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		MaterialResourceVehicleExpensesRDTO materialResourceVehicleExpensesRDTO = new MaterialResourceVehicleExpensesRDTO();

		List<ExpenseRDTO> expenseRDTOs = getExpenses(queryParameterBDTO);
		List<DetailCertificationRDTO> certificationRDTOs = getDetailCertificationExpenses(queryParameterBDTO);
		if (!expenseRDTOs.isEmpty()) {
			materialResourceVehicleExpensesRDTO.setExpenseRDTOs(expenseRDTOs);
		}
		if (!certificationRDTOs.isEmpty()) {
			materialResourceVehicleExpensesRDTO.setDetailCertificationRDTOs(certificationRDTOs);
		}
		return materialResourceVehicleExpensesRDTO;
	}


	public List<ExpenseRDTO> getExpenses(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		List<ExpenseRDTO> expenseRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<ExpenseGap> expenseGaps = myBatisTemplateGap.execute(MaterialResourceGapDao.class,
					new MyBatisDaoCallbackGap<List<ExpenseGap>>() {
						@Override
						public List<ExpenseGap> execute(MyBatisDaoGap dao) {
							return ((MaterialResourceGapDao) dao).selectExpensesByRRMM(map);
						}
					});
			if (expenseGaps != null && !expenseGaps.isEmpty()) {
				expenseRDTOs = ExpenseGapConvert.object2rdto(expenseGaps);
			}
		} catch (Exception e) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return expenseRDTOs;
	}

	public List<AmortizationBaseRDTO> getAmortizationBases(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		List<AmortizationBaseRDTO> amortizationBaseRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<AmortizationBaseGap> amortizationBaseGaps = myBatisTemplateGap.execute(MaterialResourceGapDao.class,
					new MyBatisDaoCallbackGap<List<AmortizationBaseGap>>() {
						@Override
						public List<AmortizationBaseGap> execute(MyBatisDaoGap dao) {
							return ((MaterialResourceGapDao) dao).selectAmortizationBasesByRRMM(map);
						}
					});
	
			if (amortizationBaseGaps != null && !amortizationBaseGaps.isEmpty()) {
				amortizationBaseRDTOs = AmortizationBaseGapConvert.object2rdto(amortizationBaseGaps);
			}
		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);		
		}
		return amortizationBaseRDTOs;

	}
	

}
