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

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.impl.DocumentarySupportServiceImpl;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.DocumentarySupportRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class DocumentarySupportServiceImplTest{
	
	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.documentary.support}")
	private String pathDocumentarySupport;
	
	@Value("${url.path.documentary.support.delete}")
	private String pathDocumentarySupportDelete;

	@Value("${url.path.documentary.support.get}")
	private String pathGetDocumentarySupports;
	
	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;
	
	@InjectMocks
	private DocumentarySupportServiceImpl service;
	
	private ReturnRDTO returnRDTO;
	
	private DocumentarySupportRDTO documentarySupportRDTO;
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		documentarySupportRDTO = DocumentarySupportRDTOStub.defaultOne();
		
	}

	@Test
	public void caseOkInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathDocumentarySupport", pathDocumentarySupport);
		String url = urlApiInventary + pathDocumentarySupport;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, documentarySupportRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(documentarySupportRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathDocumentarySupport", pathDocumentarySupport);
		String url = urlApiInventary + pathDocumentarySupport;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, documentarySupportRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(documentarySupportRDTO);
	}
	
	

	@Test
	public void caseOkUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathDocumentarySupport", pathDocumentarySupport);
		ReflectionTestUtils.setField(service, "pathDocumentarySupportDelete", pathDocumentarySupportDelete);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiInventary + pathDocumentarySupport + pathDocumentarySupportDelete;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		
		urlParams.put("codi", documentarySupportRDTO.getCodeContractDocument());
		urlParams.put("codiEntitat", documentarySupportRDTO.getCode());


		
		
		Mockito.when(restCall.executePUT(url, urlParams, documentarySupportRDTO)).thenReturn(ss);
		service.delete(documentarySupportRDTO);
	}

	@Test (expected = ImiException.class)
	public void caseKoUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathDocumentarySupport", pathDocumentarySupport);
		ReflectionTestUtils.setField(service, "pathDocumentarySupportDelete", pathDocumentarySupportDelete);

		
		String url = urlApiInventary + pathDocumentarySupport + pathDocumentarySupportDelete;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		
		urlParams.put("codi", documentarySupportRDTO.getCodeContractDocument());
		urlParams.put("codiEntitat", documentarySupportRDTO.getCode());

		
		Mockito.doThrow(ImiException.class).when(restCall).executePUT(url, urlParams, documentarySupportRDTO);
		service.delete(documentarySupportRDTO);
	}

	}
