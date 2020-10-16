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

import es.bcn.imi.framework.vigia.inventari.business.InstallationService;
import es.bcn.imi.framework.vigia.inventari.integration.orm.dao.gap.InstallationGapDao;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.integration.gap.inventary.installation.service.IntegrationGapInstallationService;
import es.bcn.vigia.fmw.integration.gap.inventary.installation.to.response.CreateInstalacioResponse;
import es.bcn.vigia.fmw.integration.gap.inventary.installation.to.response.UpdateInstalacioResponse;
import es.bcn.vigia.fmw.libcommons.business.dto.InstallationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.MessageContansts;
import es.bcn.vigia.fmw.libcommons.constants.ReturnGapConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.AmortizationBaseGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ApportionmentGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.DetailCertificationGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ExpenseGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.InstallationGap;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AmortizationBaseRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ApportionmentRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DetailCertificationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ExpenseRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.BreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.InstallationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.InstallationExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.InstallationPeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.MassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationPeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.convert.AmortizationBaseGapConvert;
import es.bcn.vigia.fmw.libutils.convert.ApportionmentGapConvert;
import es.bcn.vigia.fmw.libutils.convert.DetailCertificationGapConvert;
import es.bcn.vigia.fmw.libutils.convert.ExpenseGapConvert;
import es.bcn.vigia.fmw.libutils.convert.InstallationDetailedGapConvert;
import es.bcn.vigia.fmw.libutils.convert.InstallationMassiveGapConvert;
import es.bcn.vigia.fmw.libutils.convert.InstallationPeriodGapConvert;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy
@Service(ServicesConstants.SRV_INSTALLATION)
public class InstallationServiceImpl implements InstallationService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INT_GAP_INSTALLATION)
	IntegrationGapInstallationService service;

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	private ReturnRDTO returnRDTO;

	private String isCode;

	private String isMessage;

	@Override
	public ReturnRDTO insert(InstallationBDTO installationBDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			CreateInstalacioResponse response = service.create(installationBDTO);

			if (!response.getCreateInstalacioResults().isEmpty()) {

				isCode = response.getCreateInstalacioResults().get(0).getResult().getResultCode();
				isMessage = response.getCreateInstalacioResults().get(0).getResult().getResultMessage();

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

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, installationBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, installationBDTO.toString()), e);
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO update(InstallationBDTO installationBDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			UpdateInstalacioResponse response = service.update(installationBDTO);

			if (!response.getUpdateInstalacioResults().isEmpty()) {

				isCode = response.getUpdateInstalacioResults().get(0).getResult().getResultCode();
				isMessage = response.getUpdateInstalacioResults().get(0).getResult().getResultMessage();
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

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, installationBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, installationBDTO.toString()), e);
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);

		return returnRDTO;

	}

	@Override
	public ReturnRDTO delete(InstallationBDTO installationBDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			UpdateInstalacioResponse response = service.update(installationBDTO);

			if (!response.getUpdateInstalacioResults().isEmpty()) {

				isCode = response.getUpdateInstalacioResults().get(0).getResult().getResultCode();
				isMessage = response.getUpdateInstalacioResults().get(0).getResult().getResultMessage();

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

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, installationBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, installationBDTO.toString()), e);
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertExpense(InstallationBDTO installationBDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			UpdateInstalacioResponse response = service.createExpense(installationBDTO);

			if (!response.getUpdateInstalacioResults().isEmpty()) {

				isCode = response.getUpdateInstalacioResults().get(0).getResult().getResultCode();
				isMessage = response.getUpdateInstalacioResults().get(0).getResult().getResultMessage();

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

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, installationBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, installationBDTO.toString()), e);
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertAmortizationBase(InstallationBDTO installationBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			UpdateInstalacioResponse response = service.createAmortizationBase(installationBDTO);

			if (!response.getUpdateInstalacioResults().isEmpty()) {

				isCode = response.getUpdateInstalacioResults().get(0).getResult().getResultCode();
				isMessage = response.getUpdateInstalacioResults().get(0).getResult().getResultMessage();

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
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, installationBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, installationBDTO.toString()), e);
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertApportionment(InstallationBDTO installationBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			UpdateInstalacioResponse response = service.createApportionment(installationBDTO);

			if (!response.getUpdateInstalacioResults().isEmpty()) {

				isCode = response.getUpdateInstalacioResults().get(0).getResult().getResultCode();
				isMessage = response.getUpdateInstalacioResults().get(0).getResult().getResultMessage();

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
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, installationBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, installationBDTO.toString()), e);
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}

	@Override
	public ReturnInstallationDetailedRDTO selectDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnInstallationDetailedRDTO returnInstallationDetailedRDTO = new ReturnInstallationDetailedRDTO();
		InstallationDetailedRDTO installationDetailedRDTO = new InstallationDetailedRDTO();
		returnRDTO = new ReturnRDTO();

		try {

			installationDetailedRDTO = getInstallationDetailed(queryParameterBDTO);

		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnInstallationDetailedRDTO.setReturnRDTO(returnRDTO);
		returnInstallationDetailedRDTO.setInstallationDetailedRDTO(installationDetailedRDTO);
		logger.info(LogsConstants.LOG_END);
		return returnInstallationDetailedRDTO;
	}

	@Override
	public ReturnMassiveRDTO selectMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		List<MassiveRDTO> massiveRDTOs = new ArrayList<>();
		returnRDTO = new ReturnRDTO();

		try {

			massiveRDTOs = getInstallationMassive(queryParameterBDTO);

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
	public ReturnBreakdownAmortizationRDTO selectAmortization(QueryParameterBDTO queryParameterBDTO)
			throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnBreakdownAmortizationRDTO returnBreakdownAmortizationRDTO = new ReturnBreakdownAmortizationRDTO();
		BreakdownAmortizationRDTO breakdownAmortizationRDTO = new BreakdownAmortizationRDTO();
		returnRDTO = new ReturnRDTO();

		try {

			breakdownAmortizationRDTO = getInstallationAmortization(queryParameterBDTO);

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
	public ReturnInstallationExpensesRDTO selectExpenses(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnInstallationExpensesRDTO returnInstallationExpensesRDTO = new ReturnInstallationExpensesRDTO();
		InstallationExpensesRDTO installationExpensesRDTO = new InstallationExpensesRDTO();
		returnRDTO = new ReturnRDTO();

		try {

			installationExpensesRDTO = getInstallationExpenses(queryParameterBDTO);

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnInstallationExpensesRDTO.setReturnRDTO(returnRDTO);
		returnInstallationExpensesRDTO.setInstallationExpensesRDTO(installationExpensesRDTO);
		logger.info(LogsConstants.LOG_END);
		return returnInstallationExpensesRDTO;
	}

	@Override
	public ReturnInstallationPeriodRDTO selectPeriod(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnInstallationPeriodRDTO returnInstallationPeriodRDTO = new ReturnInstallationPeriodRDTO();
		InstallationPeriodRDTO installationPeriodRDTO = new InstallationPeriodRDTO();
		returnRDTO = new ReturnRDTO();

		try {

			installationPeriodRDTO = getPeriod(queryParameterBDTO);

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnInstallationPeriodRDTO.setReturnRDTO(returnRDTO);
		returnInstallationPeriodRDTO.setInstallationPeriodRDTO(installationPeriodRDTO);
		logger.info(LogsConstants.LOG_END);
		return returnInstallationPeriodRDTO;
	}

	public InstallationPeriodRDTO getPeriod(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		InstallationPeriodRDTO installationPeriodRDTO = new InstallationPeriodRDTO();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<InstallationGap> installationGaps = myBatisTemplateGap.execute(InstallationGapDao.class,
					new MyBatisDaoCallbackGap<List<InstallationGap>>() {
						@Override
						public List<InstallationGap> execute(MyBatisDaoGap dao) {
							return ((InstallationGapDao) dao).selectInstallation(map);
						}
					});
	
			if (installationGaps != null && !installationGaps.isEmpty()) {
				installationPeriodRDTO = InstallationPeriodGapConvert.object2rdto(installationGaps.get(0));
				
				List<ApportionmentRDTO> apportionmentsRDTO = getApportionments(queryParameterBDTO);
	
				installationPeriodRDTO.setApportionmentsRDTO(apportionmentsRDTO);
				installationPeriodRDTO.setExpenseCertificatedRDTOs(null);
				installationPeriodRDTO.setAmortizationCertificatedRDTOs(null);
	
			}
		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return installationPeriodRDTO;
	}

	public BreakdownAmortizationRDTO getInstallationAmortization(QueryParameterBDTO queryParameterBDTO)
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

	public InstallationExpensesRDTO getInstallationExpenses(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		InstallationExpensesRDTO installationExpensesRDTO = new InstallationExpensesRDTO();

		List<ExpenseRDTO> expenseRDTOs = getExpenses(queryParameterBDTO);
		List<DetailCertificationRDTO> certificationRDTOs = getDetailCertificationExpenses(queryParameterBDTO);
		if (!expenseRDTOs.isEmpty()) {
			installationExpensesRDTO.setExpenseRDTOs(expenseRDTOs);
		}
		if (!certificationRDTOs.isEmpty()) {
			installationExpensesRDTO.setDetailCertificationRDTOs(certificationRDTOs);
		}
		return installationExpensesRDTO;
	}

	public InstallationDetailedRDTO getInstallationDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		InstallationDetailedRDTO installationDetailedRDTO = new InstallationDetailedRDTO();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<InstallationGap> installationGaps = myBatisTemplateGap.execute(InstallationGapDao.class,
					new MyBatisDaoCallbackGap<List<InstallationGap>>() {
						@Override
						public List<InstallationGap> execute(MyBatisDaoGap dao) {
							return ((InstallationGapDao) dao).selectInstallation(map);
						}
					});
	
			if (installationGaps != null && !installationGaps.isEmpty()) {
				InstallationGap installationGap = installationGaps.get(0);
	
				installationDetailedRDTO = InstallationDetailedGapConvert.object2rdto(installationGap);
				installationDetailedRDTO.setCodeUser(queryParameterBDTO.getCodeUser());
	
				List<AmortizationBaseRDTO> amortizationBasesRDTO = getAmortizationBases(queryParameterBDTO);
				List<ExpenseRDTO> expensesRDTO = getExpenses(queryParameterBDTO);
				List<ApportionmentRDTO> apportionmentsRDTO = getApportionments(queryParameterBDTO);
	
				installationDetailedRDTO.setAmortizationBasesRDTO(amortizationBasesRDTO);
				installationDetailedRDTO.setExpensesRDTO(expensesRDTO);
				installationDetailedRDTO.setApportionmentsRDTO(apportionmentsRDTO);
				
				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());	
			}else {
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
						MessageContansts.MSG_ENTITY_INSTALLATION, queryParameterBDTO.getCode()));
			}
		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return installationDetailedRDTO;
	}

	public List<MassiveRDTO> getInstallationMassive(QueryParameterBDTO queryParameterBDTO)
			throws ImiException {
		List<MassiveRDTO> massiveRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<InstallationGap> installationGaps = myBatisTemplateGap.execute(InstallationGapDao.class,
					new MyBatisDaoCallbackGap<List<InstallationGap>>() {
						@Override
						public List<InstallationGap> execute(MyBatisDaoGap dao) {
							return ((InstallationGapDao) dao).selectInstallation(map);
						}
					});
	
			if (installationGaps != null && !installationGaps.isEmpty()) {
				massiveRDTOs = InstallationMassiveGapConvert.object2rdto(installationGaps);
	
			}
		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterBDTO.toString()), e);
			throw new ImiException(e);
		}
		return massiveRDTOs;
	}

	public List<AmortizationBaseRDTO> getAmortizationBases(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		List<AmortizationBaseRDTO> amortizationBaseRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<AmortizationBaseGap> amortizationBaseGaps = myBatisTemplateGap.execute(InstallationGapDao.class,
					new MyBatisDaoCallbackGap<List<AmortizationBaseGap>>() {
						@Override
						public List<AmortizationBaseGap> execute(MyBatisDaoGap dao) {
							return ((InstallationGapDao) dao).selectAmortizationBasesByInstallation(map);
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

	public List<ApportionmentRDTO> getApportionments(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		List<ApportionmentRDTO> apportionmentRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<ApportionmentGap> apportionmentGaps = myBatisTemplateGap.execute(InstallationGapDao.class,
					new MyBatisDaoCallbackGap<List<ApportionmentGap>>() {
						@Override
						public List<ApportionmentGap> execute(MyBatisDaoGap dao) {
							return ((InstallationGapDao) dao).selectApportionmentsByInstallation(map);
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

	public List<ExpenseRDTO> getExpenses(QueryParameterBDTO queryParameterBDTO) throws ImiException {
		List<ExpenseRDTO> expenseRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<ExpenseGap> expenseGaps = myBatisTemplateGap.execute(InstallationGapDao.class,
					new MyBatisDaoCallbackGap<List<ExpenseGap>>() {
						@Override
						public List<ExpenseGap> execute(MyBatisDaoGap dao) {
							return ((InstallationGapDao) dao).selectExpensesByInstallation(map);
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

	public List<DetailCertificationRDTO> getDetailCertificationAmortization(QueryParameterBDTO queryParameterBDTO)
			throws ImiException {
		List<DetailCertificationRDTO> certificationRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterBDTO.getMap();
			List<DetailCertificationGap> certificationGaps = myBatisTemplateGap.execute(InstallationGapDao.class,
					new MyBatisDaoCallbackGap<List<DetailCertificationGap>>() {
						@Override
						public List<DetailCertificationGap> execute(MyBatisDaoGap dao) {
							return ((InstallationGapDao) dao).selectDetailCertificationAmortizationByInstallation(map);
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
			List<DetailCertificationGap> certificationGaps = myBatisTemplateGap.execute(InstallationGapDao.class,
					new MyBatisDaoCallbackGap<List<DetailCertificationGap>>() {
						@Override
						public List<DetailCertificationGap> execute(MyBatisDaoGap dao) {
							return ((InstallationGapDao) dao).selectDetailCertificationExpensesByInstallation(map);
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
	
}