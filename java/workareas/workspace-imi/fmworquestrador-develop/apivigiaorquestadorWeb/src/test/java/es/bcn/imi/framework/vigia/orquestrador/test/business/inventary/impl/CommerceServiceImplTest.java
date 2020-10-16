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

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.impl.CommerceServiceImpl;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.CommerceRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class CommerceServiceImplTest{
	
	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.commerce}")
	private String pathCommerce;

	@Value("${url.path.commerce.getMassive}")
	private String pathCommerceGetMassive;

	@Value("${url.path.commerce.getDetailed}")
	private String pathCommerceGetDetailed;

	@Value("${url.path.commerce.getElements}")
	private String pathCommerceGetElements;
		
	@Value("${url.path.commerce.put}")
	private String pathCommercePut;
	
	@Value("${url.path.commerce.delete}")
	private String pathCommerceDelete;
	
	@Value("${url.path.commerce.elements}")
	private String pathCommerceElements;
	
	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;
	
	@InjectMocks
	private CommerceServiceImpl service;
	
	private ReturnRDTO returnRDTO;
	
	private CommerceRDTO commerceRDTO;
	
	private QueryParameterRDTO queryParameterRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		commerceRDTO = CommerceRDTOStub.defaultOne();
		queryParameterRDTO = QueryParameterRDTOStub.defaultOne();
	}

	@Test
	public void caseOkInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathCommerce", pathCommerce);
		String url = urlApiInventary + pathCommerce;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, commerceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(commerceRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathCommerce", pathCommerce);
		String url = urlApiInventary + pathCommerce;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, commerceRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(commerceRDTO);
	}
	
	

	@Test
	public void caseOkUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathCommerce", pathCommerce);
		ReflectionTestUtils.setField(service, "pathCommercePut", pathCommercePut);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiInventary + pathCommerce + pathCommercePut;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", commerceRDTO.getCode());
		
		Mockito.when(restCall.executePUT(url, urlParams, commerceRDTO)).thenReturn(ss);
		service.update(commerceRDTO);
	}

	@Test (expected = ImiException.class)
	public void caseKoUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathCommerce", pathCommerce);
		ReflectionTestUtils.setField(service, "pathCommercePut", pathCommercePut);
		
		String url = urlApiInventary + pathCommerce + pathCommercePut;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", commerceRDTO.getCode());
		
		Mockito.doThrow(ImiException.class).when(restCall).executePUT(url, urlParams, commerceRDTO);
		service.update(commerceRDTO);
	}

	@Test
	public void caseOkDelete() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathCommerce", pathCommerce);
		ReflectionTestUtils.setField(service, "pathCommerceDelete", pathCommerceDelete);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiInventary + pathCommerce + pathCommerceDelete;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", commerceRDTO.getCode());
		
		Mockito.when(restCall.executeDELETE(url, urlParams, commerceRDTO)).thenReturn(ss);
		service.delete(commerceRDTO);
	}

	@Test (expected = ImiException.class)
	public void caseKoDelete() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathCommerce", pathCommerce);
		ReflectionTestUtils.setField(service, "pathCommerceDelete", pathCommerceDelete);
		
		String url = urlApiInventary + pathCommerce + pathCommerceDelete;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", commerceRDTO.getCode());
		
		Mockito.doThrow(ImiException.class).when(restCall).executeDELETE(url, urlParams, commerceRDTO);
		service.delete(commerceRDTO);
	}
	
	@Test
	public void caseOkInsertElements() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathCommerce", pathCommerce);
		ReflectionTestUtils.setField(service, "pathCommerceElements", pathCommerceElements);

		String url = urlApiInventary + pathCommerce + pathCommerceElements;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, commerceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertElements(commerceRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertExpense() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathCommerce", pathCommerce);
		ReflectionTestUtils.setField(service, "pathCommerceElements", pathCommerceElements);

		String url = urlApiInventary + pathCommerce + pathCommerceElements;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, commerceRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertElements(commerceRDTO);
	}
	
	@Test
	public void caseOkSelectMassive() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathCommerce", pathCommerce);
		ReflectionTestUtils.setField(service, "pathCommerceGetMassive", pathCommerceGetMassive);
		String url = urlApiInventary + pathCommerce + pathCommerceGetMassive;
		
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
		ReflectionTestUtils.setField(service, "pathCommerce", pathCommerce);
		ReflectionTestUtils.setField(service, "pathCommerceGetDetailed", pathCommerceGetDetailed);
		String url = urlApiInventary + pathCommerce + pathCommerceGetDetailed;
		
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
	public void caseOkSelectElements() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathCommerce", pathCommerce);
		ReflectionTestUtils.setField(service, "pathCommerceGetElements", pathCommerceGetElements);
		String url = urlApiInventary + pathCommerce + pathCommerceGetElements;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
		urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
		urlParams.put("trasactionId", queryParameterRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		service.selectElements(queryParameterRDTO);
	}
	
}
