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

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.impl.MaterialResourceServiceImpl;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.MaterialResourceRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = { "classpath:app/config/application.properties" })
@WebAppConfiguration
public class MaterialResourceServiceImplTest {
	
	@Mock
	private RestCall restCall;

	@Mock
	private Utils utils;
	
	@InjectMocks
	private MaterialResourceServiceImpl service;

	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.materialresource}")
	private String pathMaterialResource;

	@Value("${url.path.materialresource.getMassive}")
	private String pathMaterialResourceGetMassive;

	@Value("${url.path.materialresource.getDetailed}")
	private String pathMaterialResourceGetDetailed;

	@Value("${url.path.materialresource.vehicle}")
	private String pathMaterialResourceVehicle;

	@Value("${url.path.materialresource.vehicle.getMassive}")
	private String pathMaterialResourceVehicleGetMassive;

	@Value("${url.path.materialresource.vehicle.getDetailed}")
	private String pathMaterialResourceVehicleGetDetailed;

	@Value("${url.path.materialresource.vehicle.getAmortization}")
	private String pathMaterialResourceVehicleGetAmortization;

	@Value("${url.path.materialresource.vehicle.getExpenses}")
	private String pathMaterialResourceVehicleGetExpenses;

	@Value("${url.path.materialresource.vehicle.getPeriod}")
	private String pathMaterialResourceVehicleGetPeriod;

	@Value("${url.path.materialresource.put}")
	private String pathMaterialResourcePut;

	@Value("${url.path.materialresource.delete}")
	private String pathMaterialResourceDelete;
	
	@Value("${url.path.materialresource.expense}")
	private String pathMaterialResourceExpense;
	
	@Value("${url.path.materialresource.amortization}")
	private String pathMaterialResourceAmortization;
	
	@Value("${url.path.materialresource.apportionment}")
	private String pathMaterialResourceApportionment;

	private MaterialResourceRDTO materialResourceRDTO;
	
	private ReturnRDTO returnRDTO;
	
	private QueryParameterRDTO queryParameterRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		materialResourceRDTO = MaterialResourceRDTOStub.defaultOne();
		queryParameterRDTO = QueryParameterRDTOStub.defaultOne();
	}
	
	@Test
	public void caseOkInsert() throws ImiException {
		service.insert(materialResourceRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);

		String url = urlApiInventary + pathMaterialResource;

		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, materialResourceRDTO);
		service.insert(materialResourceRDTO);
	}
	
	@Test
	public void caseOkUpdate() throws ImiException {
		service.update(materialResourceRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourcePut", pathMaterialResourcePut);

		String url = urlApiInventary + pathMaterialResource + pathMaterialResourcePut;

		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", materialResourceRDTO.getCode());
		
		Mockito.doThrow(ImiException.class).when(restCall).executePUT(url, urlParams, materialResourceRDTO);
		service.update(materialResourceRDTO);
	}

	@Test
	public void caseOkDelete() throws ImiException {
		service.delete(materialResourceRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoDelete() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceDelete", pathMaterialResourceDelete);

		String url = urlApiInventary + pathMaterialResource + pathMaterialResourceDelete;

		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", materialResourceRDTO.getCode());
		
		Mockito.doThrow(ImiException.class).when(restCall).executeDELETE(url, urlParams, materialResourceRDTO);
		service.delete(materialResourceRDTO);
	}
	
	@Test
	public void caseOkInsertExpense() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceExpense", pathMaterialResourceExpense);

		String url = urlApiInventary + pathMaterialResource + pathMaterialResourceExpense;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, materialResourceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertExpense(materialResourceRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertExpense() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceExpense", pathMaterialResourceExpense);

		String url = urlApiInventary + pathMaterialResource + pathMaterialResourceExpense;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, materialResourceRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertExpense(materialResourceRDTO);
	}
	
	@Test
	public void caseOkInsertAmortizationBase() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceAmortization", pathMaterialResourceAmortization);

		String url = urlApiInventary + pathMaterialResource + pathMaterialResourceAmortization;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, materialResourceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertAmortizationBase(materialResourceRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertAmortizationBase() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceAmortization", pathMaterialResourceAmortization);

		String url = urlApiInventary + pathMaterialResource + pathMaterialResourceAmortization;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, materialResourceRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertAmortizationBase(materialResourceRDTO);
	}
	
	@Test
	public void caseOkInsertApportionment() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceApportionment", pathMaterialResourceApportionment);

		String url = urlApiInventary + pathMaterialResource + pathMaterialResourceApportionment;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, materialResourceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertApportionment(materialResourceRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertApportionment() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceApportionment", pathMaterialResourceApportionment);

		String url = urlApiInventary + pathMaterialResource + pathMaterialResourceApportionment;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, materialResourceRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertApportionment(materialResourceRDTO);
	}
	
	@Test
	public void caseOkSelectMassive() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceGetMassive", pathMaterialResourceGetMassive);
		String url = urlApiInventary + pathMaterialResource + pathMaterialResourceGetMassive;
		
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
	public void caseOkSelectDetailed() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceGetDetailed", pathMaterialResourceGetDetailed);
		String url = urlApiInventary + pathMaterialResource + pathMaterialResourceGetDetailed;
		
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
	public void caseOkSelectVehicleMassive() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicle", pathMaterialResourceVehicle);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicleGetMassive", pathMaterialResourceVehicleGetMassive);
		String url = urlApiInventary + pathMaterialResource + pathMaterialResourceVehicle + pathMaterialResourceVehicleGetMassive;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCodeContract());
		urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
		urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
		urlParams.put("trasactionId", queryParameterRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		service.selectVehicleMassive(queryParameterRDTO);
	}
	
	@Test
	public void caseOkSelectVehicleDetailed() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicle", pathMaterialResourceVehicle);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicleGetDetailed", pathMaterialResourceVehicleGetDetailed);
		String url = urlApiInventary + pathMaterialResource + pathMaterialResourceVehicle + pathMaterialResourceVehicleGetDetailed;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
		urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
		urlParams.put("trasactionId", queryParameterRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		service.selectVehicleDetailed(queryParameterRDTO);
	}
	
	@Test
	public void caseOkSelectAmortization() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicle", pathMaterialResourceVehicle);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicleGetAmortization", pathMaterialResourceVehicleGetAmortization);
		String url = urlApiInventary + pathMaterialResource + pathMaterialResourceVehicle + pathMaterialResourceVehicleGetAmortization;
		
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
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicle", pathMaterialResourceVehicle);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicleGetExpenses", pathMaterialResourceVehicleGetExpenses);
		String url = urlApiInventary + pathMaterialResource + pathMaterialResourceVehicle + pathMaterialResourceVehicleGetExpenses;
		
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
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicle", pathMaterialResourceVehicle);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicleGetPeriod", pathMaterialResourceVehicleGetPeriod);
		String url = urlApiInventary + pathMaterialResource + pathMaterialResourceVehicle + pathMaterialResourceVehicleGetPeriod;
		
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
