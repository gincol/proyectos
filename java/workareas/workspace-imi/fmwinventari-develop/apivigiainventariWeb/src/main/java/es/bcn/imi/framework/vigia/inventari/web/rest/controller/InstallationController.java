package es.bcn.imi.framework.vigia.inventari.web.rest.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.bcn.imi.framework.vigia.inventari.business.InstallationService;
import es.bcn.vigia.fmw.libcommons.business.dto.InstallationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationPeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.convert.InstallationConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/installacio")
@Lazy(true)
@Api("INVENTARI API")
public class InstallationController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INSTALLATION)
	private InstallationService installationService;

	private ReturnRDTO returnRDTO;	

	private QueryParameterBDTO queryParameterBDTO;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta d'una Instal·lació", tags = { "installacio" })
	public ResponseEntity<ReturnRDTO> save(@RequestBody InstallationRDTO installationRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			InstallationBDTO installationBDTO = InstallationConvert.rdto2bto(installationRDTO);

			returnRDTO = installationService.insert(installationBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, installationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de modificació d'una Instal·lació", tags = { "installacio" })
	public ResponseEntity<ReturnRDTO> update(@PathVariable(value = "codi", required = true) String code,
			@RequestBody InstallationRDTO installationRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			installationRDTO.setCode(code);

			InstallationBDTO installationBDTO = InstallationConvert.rdto2bto(installationRDTO);

			returnRDTO = installationService.update(installationBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, installationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}", method = RequestMethod.DELETE)
	@ResponseBody
	@ApiOperation(value = "Interfície de baixa d'una Instal·lació", tags = { "installacio" })
	public ResponseEntity<ReturnRDTO> delete(@PathVariable(value = "codi", required = true) String code,
			@RequestBody InstallationRDTO installationRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			installationRDTO.setCode(code);

			InstallationBDTO installationBDTO = InstallationConvert.rdto2bto(installationRDTO);

			returnRDTO = installationService.delete(installationBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, installationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/despesa", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta d'una Despesa d'una Instal·lació", tags = { "installacio" })
	public ResponseEntity<ReturnRDTO> saveExpense(@RequestBody InstallationRDTO installationRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			InstallationBDTO installationBDTO = InstallationConvert.rdto2bto(installationRDTO);

			returnRDTO = installationService.insertExpense(installationBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, installationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/amortitzacio", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta d'una Despesa d'una Instal·lació", tags = { "installacio" })
	public ResponseEntity<ReturnRDTO> saveAmortizationBase(@RequestBody InstallationRDTO installationRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			InstallationBDTO installationBDTO = InstallationConvert.rdto2bto(installationRDTO);

			returnRDTO = installationService.insertAmortizationBase(installationBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, installationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/prorrateig", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta d'una Despesa d'una Instal·lació", tags = { "installacio" })
	public ResponseEntity<ReturnRDTO> saveApportionment(@RequestBody InstallationRDTO installationRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			InstallationBDTO installationBDTO = InstallationConvert.rdto2bto(installationRDTO);

			returnRDTO = installationService.insertApportionment(installationBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, installationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada d'Instal·lació", tags = { "installacio" })
	public ResponseEntity<ReturnInstallationDetailedRDTO> selectDetailed(
			@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiContracta", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnInstallationDetailedRDTO returnInstallationDetailedRDTO = new ReturnInstallationDetailedRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCode(code);
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setDateReference(dateReference);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnInstallationDetailedRDTO = installationService.selectDetailed(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnInstallationDetailedRDTO), ex);
			return new ResponseEntity<>(returnInstallationDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnInstallationDetailedRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva d'Instal·lació", tags = { "installacio" })
	public ResponseEntity<ReturnMassiveRDTO> selectMassive(
			@PathVariable(value = "codi", required = true) String code,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCodeContract(code);
			queryParameterBDTO.setDateReference(dateReference);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnMassiveRDTO = installationService.selectMassive(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMassiveRDTO), ex);
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}/contracta/{codiContracta}/amortitzacio", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta Desglossament d'amortització d'Instal·lació", tags = { "installacio" })
	public ResponseEntity<ReturnBreakdownAmortizationRDTO> selectAmortization(
			@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiContracta", required = true) String codeContract,
			@RequestParam(value = "codiUsuari", required = true) String codeUser,
			@RequestParam(required = true) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnBreakdownAmortizationRDTO returnBreakdownAmortizationRDTO = new ReturnBreakdownAmortizationRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCode(code);
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnBreakdownAmortizationRDTO = installationService.selectAmortization(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnBreakdownAmortizationRDTO), ex);
			return new ResponseEntity<>(returnBreakdownAmortizationRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnBreakdownAmortizationRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}/contracta/{codiContracta}/despesa", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta Desglossament de Despeses d'Instal·lació", tags = { "installacio" })
	public ResponseEntity<ReturnInstallationExpensesRDTO> selectExpenses(
			@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiContracta", required = true) String codeContract,
			@RequestParam(value = "codiUsuari", required = true) String codeUser,
			@RequestParam(required = true) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnInstallationExpensesRDTO returnInstallationExpensesRDTO = new ReturnInstallationExpensesRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCode(code);
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnInstallationExpensesRDTO = installationService.selectExpenses(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnInstallationExpensesRDTO), ex);
			return new ResponseEntity<>(returnInstallationExpensesRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnInstallationExpensesRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}/contracta/{codiContracta}/periode/{periode}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta d'Instal·lació per un període de certificació", tags = { "installacio" })
	public ResponseEntity<ReturnInstallationPeriodRDTO> selectPeriod(
			@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiContracta", required = true) String codeContract,
			@PathVariable(value = "periode", required = true) String period,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnInstallationPeriodRDTO returnInstallationPeriodRDTO = new ReturnInstallationPeriodRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCode(code);
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setCodeUser(codeUser);
			queryParameterBDTO.setPeriod(period);
			
			returnInstallationPeriodRDTO = installationService.selectPeriod(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnInstallationPeriodRDTO), ex);
			return new ResponseEntity<>(returnInstallationPeriodRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnInstallationPeriodRDTO, HttpStatus.OK);
	}
}
