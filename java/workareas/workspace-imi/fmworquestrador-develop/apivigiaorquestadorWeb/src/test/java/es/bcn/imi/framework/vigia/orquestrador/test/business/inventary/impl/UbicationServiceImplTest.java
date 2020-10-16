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

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.impl.UbicationServiceImpl;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class UbicationServiceImplTest{
	
	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.ubications}")
	private String pathUbication;

	@Value("${url.path.ubications.getMassive}")
	private String pathUbicationGetMassive;

	@Value("${url.path.ubications.getDetailed}")
	private String pathUbicationGetDetailed;
	
	@Value("${url.path.ubications.put}")
	private String pathUbicationPut;
	
	@Value("${url.path.ubications.delete}")
	private String pathUbicationDelete;

	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;
	
	@InjectMocks
	private UbicationServiceImpl service;
	
	private ReturnRDTO returnRDTO;
	
	private UbicationRDTO ubicationRDTO;
	
	private QueryParameterRDTO queryParameterRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = new ReturnRDTO();
        returnRDTO.setCode("OK");
        returnRDTO.setMessage("Se añadió");
        
        ubicationRDTO = new UbicationRDTO();
        ubicationRDTO.setCode("codi");
		queryParameterRDTO = QueryParameterRDTOStub.defaultOne();
	}

	@Test
	public void caseOkInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathUbication", pathUbication);
		String url = urlApiInventary + pathUbication;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, ubicationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(ubicationRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathUbication", pathUbication);
		String url = urlApiInventary + pathUbication;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, ubicationRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(ubicationRDTO);
	}

	@Test/* (expected = ImiException.class)*/
	public void caseOkUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathUbication", pathUbication);
		ReflectionTestUtils.setField(service, "pathUbicationPut", pathUbicationPut);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiInventary + pathUbication + pathUbicationPut;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", ubicationRDTO.getCode());
		
		Mockito.when(restCall.executePUT(url, urlParams, ubicationRDTO)).thenReturn(ss);
		service.update(ubicationRDTO);
	}

	@Test (expected = ImiException.class)
	public void caseKoUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathUbication", pathUbication);
		ReflectionTestUtils.setField(service, "pathUbicationPut", pathUbicationPut);
		
		String url = urlApiInventary + pathUbication + pathUbicationPut;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", ubicationRDTO.getCode());
		
		Mockito.doThrow(ImiException.class).when(restCall).executePUT(url, urlParams, ubicationRDTO);
		service.update(ubicationRDTO);
	}

	@Test
	public void caseOkDelete() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathUbication", pathUbication);
		ReflectionTestUtils.setField(service, "pathUbicationDelete", pathUbicationDelete);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiInventary + pathUbication + pathUbicationDelete;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", ubicationRDTO.getCode());
		
		Mockito.when(restCall.executeDELETE(url, urlParams, ubicationRDTO)).thenReturn(ss);
		service.delete(ubicationRDTO);
	}

	@Test (expected = ImiException.class)
	public void caseKoDelete() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathUbication", pathUbication);
		ReflectionTestUtils.setField(service, "pathUbicationDelete", pathUbicationDelete);
		
		String url = urlApiInventary + pathUbication + pathUbicationDelete;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", ubicationRDTO.getCode());
		
		Mockito.doThrow(ImiException.class).when(restCall).executeDELETE(url, urlParams, ubicationRDTO);
		service.delete(ubicationRDTO);
	}
	
	@Test
	public void caseOkSelectMassive() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathUbication", pathUbication);
		ReflectionTestUtils.setField(service, "pathUbicationGetMassive", pathUbicationGetMassive);
		String url = urlApiInventary + pathUbication + pathUbicationGetMassive;
		
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
		ReflectionTestUtils.setField(service, "pathUbication", pathUbication);
		ReflectionTestUtils.setField(service, "pathUbicationGetDetailed", pathUbicationGetDetailed);
		String url = urlApiInventary + pathUbication + pathUbicationGetDetailed;
		
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
	
}
