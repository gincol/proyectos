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

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.impl.LogicalModelServiceImpl;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.LogicalModelRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = { "classpath:app/config/application.properties" })
@WebAppConfiguration
public class LogicalModelServiceImplTest {

	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.logicmodel}")
	private String pathLogicModel;

	@Value("${url.path.logicmodel.get}")
	private String pathLogicModelGet;

	@Value("${url.path.logicmodel.getMassive}")
	private String pathLogicModelGetMassive;

	@Value("${url.path.logicmodel.getDetailed}")
	private String pathLogicModelGetDetailed;

	@Value("${url.path.logicmodel.put}")
	private String pathLogicModelPut;

	@Value("${url.path.logicmodel.delete}")
	private String pathLogicModelDelete;

	@Mock
	private RestCall restCall;

	@Mock
	private Utils utils;

	@InjectMocks
	private LogicalModelServiceImpl service;

	private ReturnRDTO returnRDTO;

	private LogicalModelRDTO logicalModelRDTO;
	
	private QueryParameterRDTO queryParameterRDTO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		logicalModelRDTO = LogicalModelRDTOStub.defaultOne();
		queryParameterRDTO = QueryParameterRDTOStub.defaultOne();
	}

	@Test
	public void caseOkInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathLogicModel", pathLogicModel);
		String url = urlApiInventary + pathLogicModel;

		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);

		Mockito.when(restCall.executePOST(url, logicalModelRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(logicalModelRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathLogicModel", pathLogicModel);
		String url = urlApiInventary + pathLogicModel;

		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);

		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, logicalModelRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(logicalModelRDTO);
	}

	@Test
	public void caseOkSelect() throws ImiException {
		service.select(logicalModelRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSelect() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathLogicModel", pathLogicModel);
		ReflectionTestUtils.setField(service, "pathLogicModelGet", pathLogicModelGet);

		String url = urlApiInventary + pathLogicModel + pathLogicModelGet;

		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", logicalModelRDTO.getCode());
		
		Mockito.doThrow(ImiException.class).when(restCall).executeGET(url, urlParams, logicalModelRDTO);
		service.select(logicalModelRDTO);
	}

	@Test
	public void caseOkUpdate() throws ImiException {
		service.update(logicalModelRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathLogicModel", pathLogicModel);
		ReflectionTestUtils.setField(service, "pathLogicModelPut", pathLogicModelPut);

		String url = urlApiInventary + pathLogicModel + pathLogicModelPut;

		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", logicalModelRDTO.getCode());
		
		Mockito.doThrow(ImiException.class).when(restCall).executePUT(url, urlParams, logicalModelRDTO);
		service.update(logicalModelRDTO);
	}

	@Test
	public void caseOkDelete() throws ImiException {
		service.delete(logicalModelRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoDelete() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathLogicModel", pathLogicModel);
		ReflectionTestUtils.setField(service, "pathLogicModelDelete", pathLogicModelDelete);

		String url = urlApiInventary + pathLogicModel + pathLogicModelDelete;

		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", logicalModelRDTO.getCode());
		
		Mockito.doThrow(ImiException.class).when(restCall).executeDELETE(url, urlParams, logicalModelRDTO);
		service.delete(logicalModelRDTO);
	}
	
	@Test
	public void caseOkSelectMassive() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathLogicModel", pathLogicModel);
		ReflectionTestUtils.setField(service, "pathLogicModelGetMassive", pathLogicModelGetMassive);
		String url = urlApiInventary + pathLogicModel + pathLogicModelGetMassive;
		
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
		ReflectionTestUtils.setField(service, "pathLogicModel", pathLogicModel);
		ReflectionTestUtils.setField(service, "pathLogicModelGetDetailed", pathLogicModelGetDetailed);
		String url = urlApiInventary + pathLogicModel + pathLogicModelGetDetailed;
		
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
