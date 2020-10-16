package es.bcn.imi.framework.vigia.orquestrador.test.business.inventary.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.impl.InstallationServiceImpl;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.InstallationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class InstallationServiceImplTest{
	
	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.installation}")
	private String pathInstallation;
	
	@Value("${url.path.installation.getDetailed}")
	private String pathInstallationGetDetailed;
	
	@Value("${url.path.installation.getMassive}")
	private String pathInstallationGetMassive;
	
	@Value("${url.path.installation.getAmortization}")
	private String pathInstallationGetAmortization;
	
	@Value("${url.path.installation.getExpenses}")
	private String pathInstallationGetExpenses;
	
	@Value("${url.path.installation.getPeriod}")
	private String pathInstallationGetPeriod;
	
	@Value("${url.path.installation.put}")
	private String pathInstallationPut;
	
	@Value("${url.path.installation.delete}")
	private String pathInstallationDelete;
	
	@Value("${url.path.installation.expense}")
	private String pathInstallationExpense;
	
	@Value("${url.path.installation.amortization}")
	private String pathInstallationAmortization;
	
	@Value("${url.path.installation.apportionment}")
	private String pathInstallationApportionment;

	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;
	
	@InjectMocks
	private InstallationServiceImpl service;
	
	private ReturnRDTO returnRDTO;
	
	private InstallationRDTO installationRDTO;
	
	private QueryParameterRDTO queryParameterRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		installationRDTO = InstallationRDTOStub.defaultOne();
		queryParameterRDTO = QueryParameterRDTOStub.defaultOne();
	}

	@Test
	public void caseOkInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		String url = urlApiInventary + pathInstallation;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, installationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(installationRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		String url = urlApiInventary + pathInstallation;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, installationRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(installationRDTO);
	}
	
	

	@Test
	public void caseOkUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationPut", pathInstallationPut);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiInventary + pathInstallation + pathInstallationPut;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", installationRDTO.getCode());
		
		Mockito.when(restCall.executePUT(url, urlParams, installationRDTO)).thenReturn(ss);
		service.update(installationRDTO);
	}

	@Test (expected = ImiException.class)
	public void caseKoUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationPut", pathInstallationPut);
		
		String url = urlApiInventary + pathInstallation + pathInstallationPut;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", installationRDTO.getCode());
		
		Mockito.doThrow(ImiException.class).when(restCall).executePUT(url, urlParams, installationRDTO);
		service.update(installationRDTO);
	}

	@Test
	public void caseOkDelete() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationDelete", pathInstallationDelete);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiInventary + pathInstallation + pathInstallationDelete;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", installationRDTO.getCode());
		
		Mockito.when(restCall.executeDELETE(url, urlParams, installationRDTO)).thenReturn(ss);
		service.delete(installationRDTO);
	}

	@Test (expected = ImiException.class)
	public void caseKoDelete() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationDelete", pathInstallationDelete);
		
		String url = urlApiInventary + pathInstallation + pathInstallationDelete;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", installationRDTO.getCode());
		
		Mockito.doThrow(ImiException.class).when(restCall).executeDELETE(url, urlParams, installationRDTO);
		service.delete(installationRDTO);
	}
	
	@Test
	public void caseOkInsertExpense() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationExpense", pathInstallationExpense);

		String url = urlApiInventary + pathInstallation + pathInstallationExpense;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, installationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertExpense(installationRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertExpense() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationExpense", pathInstallationExpense);

		String url = urlApiInventary + pathInstallation + pathInstallationExpense;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, installationRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertExpense(installationRDTO);
	}
	
	@Test
	public void caseOkInsertAmortizationBase() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationAmortization", pathInstallationAmortization);

		String url = urlApiInventary + pathInstallation + pathInstallationAmortization;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, installationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertAmortizationBase(installationRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertAmortizationBase() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationAmortization", pathInstallationAmortization);

		String url = urlApiInventary + pathInstallation + pathInstallationAmortization;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, installationRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertAmortizationBase(installationRDTO);
	}
	
	@Test
	public void caseOkInsertApportionment() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationApportionment", pathInstallationApportionment);

		String url = urlApiInventary + pathInstallation + pathInstallationApportionment;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, installationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertApportionment(installationRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertApportionment() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationApportionment", pathInstallationApportionment);

		String url = urlApiInventary + pathInstallation + pathInstallationApportionment;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, installationRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertApportionment(installationRDTO);
	}
	
	@Test
	public void caseOkSelectDetailed() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationGetDetailed", pathInstallationGetDetailed);
		String url = urlApiInventary + pathInstallation + pathInstallationGetDetailed;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
		urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
		urlParams.put("trasactionId", queryParameterRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		service.selectDetailed(queryParameterRDTO);
	}
	
	@Test
	public void caseOkSelectMassive() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationGetMassive", pathInstallationGetMassive);
		String url = urlApiInventary + pathInstallation + pathInstallationGetMassive;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCodeContract());
		urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
		urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
		urlParams.put("trasactionId", queryParameterRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		service.selectMassive(queryParameterRDTO);
	}
	
	@Test
	public void caseOkSelectAmortization() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationGetAmortization", pathInstallationGetAmortization);
		String url = urlApiInventary + pathInstallation + pathInstallationGetAmortization;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
		urlParams.put("trasactionId", queryParameterRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		service.selectAmortization(queryParameterRDTO);
	}
	
	@Test
	public void caseOkSelectExpenses() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationGetExpenses", pathInstallationGetExpenses);
		String url = urlApiInventary + pathInstallation + pathInstallationGetExpenses;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
		urlParams.put("trasactionId", queryParameterRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		service.selectExpenses(queryParameterRDTO);
	}
	
	@Test
	public void caseOkSelectPeriod() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationGetPeriod", pathInstallationGetPeriod);
		String url = urlApiInventary + pathInstallation + pathInstallationGetPeriod;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		urlParams.put("periode", queryParameterRDTO.getPeriod());
		urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
		urlParams.put("trasactionId", queryParameterRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		service.selectPeriod(queryParameterRDTO);
	}
}
