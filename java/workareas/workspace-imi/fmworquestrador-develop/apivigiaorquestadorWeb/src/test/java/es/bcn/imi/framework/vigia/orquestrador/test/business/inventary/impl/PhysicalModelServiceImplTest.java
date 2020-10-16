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

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.impl.PhysicalModelServiceImpl;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AggregateAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.AggregateAmortizationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.PhysicalModelRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class PhysicalModelServiceImplTest {
	
	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.physicalmodel}")
	private String pathPhysicalModel;

	@Value("${url.path.physicalmodel.get}")
	private String pathPhysicalModelGet;

	@Value("${url.path.physicalmodel.getMassive}")
	private String pathPhysicalModelGetMassive;

	@Value("${url.path.physicalmodel.getDetailed}")
	private String pathPhysicalModelGetDetailed;

	@Value("${url.path.physicalmodel.getAmortization}")
	private String pathPhysicalModelGetAmortization;

	@Value("${url.path.physicalmodel.put}")
	private String pathPhysicalModelPut;

	@Value("${url.path.physicalmodel.delete}")
	private String pathPhysicalModelDelete;
	
	@Value("${url.path.physicalmodel.type}")
	private String pathPhysicalModelType;
	
	@Value("${url.path.physicalmodel.aggregate.amortization}")
	private String pathPhysicalModelAggregateAmortization;
	
	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;
	
	@InjectMocks
	private PhysicalModelServiceImpl service;
	
	private ReturnRDTO returnRDTO;
	
	private PhysicalModelRDTO physicalModelRDTO;
	
	private AggregateAmortizationRDTO aggregateAmortizationRDTO;
	
	private QueryParameterRDTO queryParameterRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
        physicalModelRDTO = PhysicalModelRDTOStub.defaultOne();
        aggregateAmortizationRDTO = AggregateAmortizationRDTOStub.defaultOne();
		queryParameterRDTO = QueryParameterRDTOStub.defaultOne();
	}
	
	@Test
	public void caseOkInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		String url = urlApiInventary + pathPhysicalModel;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, physicalModelRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(physicalModelRDTO);
	}
		
	@Test (expected = ImiException.class)
	public void caseKoInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		String url = urlApiInventary + pathPhysicalModel;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, physicalModelRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(physicalModelRDTO);
	}
	
	@Test 
	public void caseOkSelect() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelGet", pathPhysicalModelGet);
		String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelGet;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", physicalModelRDTO.getCode());
		Mockito.when(restCall.executeGET(url, urlParams, physicalModelRDTO)).thenReturn(ss);
		service.select(physicalModelRDTO);
	}


	@Test (expected = ImiException.class)
	public void caseKoSelect() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelGet", pathPhysicalModelGet);
		String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelGet;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", physicalModelRDTO.getCode());
		Mockito.doThrow(ImiException.class).when(restCall).executeGET(url, urlParams, physicalModelRDTO);
		service.select(physicalModelRDTO);
	}
	
	@Test 
	public void caseOkUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelPut", pathPhysicalModelPut);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelPut;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", physicalModelRDTO.getCode());
		
		Mockito.when(restCall.executePUT(url, urlParams, physicalModelRDTO)).thenReturn(ss);
		service.update(physicalModelRDTO);
	}
	
	@Test (expected = ImiException.class)
	public void caseKoUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelPut", pathPhysicalModelPut);
		
		String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelPut;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", physicalModelRDTO.getCode());
		
		Mockito.doThrow(ImiException.class).when(restCall).executePUT(url, urlParams, physicalModelRDTO);
		service.update(physicalModelRDTO);
	}
	
	@Test 
	public void caseOkDelete() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelDelete", pathPhysicalModelDelete);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelDelete;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", physicalModelRDTO.getCode());
		urlParams.put("codeContract", physicalModelRDTO.getCodeContract());
		
		Mockito.when(restCall.executeDELETE(url, urlParams, physicalModelRDTO)).thenReturn(ss);
		service.delete(physicalModelRDTO);
	}
	
	@Test (expected = ImiException.class)
	public void caseKoDelete() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelDelete", pathPhysicalModelDelete);
		
		String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelDelete;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", physicalModelRDTO.getCode());
		
		Mockito.doThrow(ImiException.class).when(restCall).executeDELETE(url, urlParams, physicalModelRDTO);
		service.delete(physicalModelRDTO);
	}
	
	@Test
	public void caseOkAggregateAmortizationInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelType", pathPhysicalModelType);
		ReflectionTestUtils.setField(service, "pathPhysicalModelAggregateAmortization", pathPhysicalModelAggregateAmortization);
		
		String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelType + pathPhysicalModelAggregateAmortization;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, aggregateAmortizationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(aggregateAmortizationRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoAggregateAmortizationInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelType", pathPhysicalModelType);
		ReflectionTestUtils.setField(service, "pathPhysicalModelAggregateAmortization", pathPhysicalModelAggregateAmortization);
		
		String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelType + pathPhysicalModelAggregateAmortization;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, aggregateAmortizationRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
	
		service.insert(aggregateAmortizationRDTO);
	}
	
	@Test
	public void caseOkSelectMassive() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelGetMassive", pathPhysicalModelGetMassive);
		String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelGetMassive;
		
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
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelGetDetailed", pathPhysicalModelGetDetailed);
		String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelGetDetailed;
		
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
	public void caseOkSelectAmortization() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelGetAmortization", pathPhysicalModelGetAmortization);
		String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelGetAmortization;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
		urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
		urlParams.put("trasactionId", queryParameterRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		service.selectAmortization(queryParameterRDTO);
	}
}
