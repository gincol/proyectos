package es.bcn.imi.framework.vigia.inventari.test.business.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import es.bcn.imi.framework.vigia.inventari.business.impl.DocumentarySupportServiceImpl;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.integration.document.output.stub.AddDocumentsDefOutputStub;
import es.bcn.vigia.fmw.integration.document.output.stub.DeleteDocumentsOutputStub;
import es.bcn.vigia.fmw.integration.document.service.IntegrationDocumentService;
import es.bcn.vigia.fmw.libcommons.business.dto.DocumentarySupportBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.DocumentarySupportExpenseBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.DocumentarySupportFileBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.GdAddDocumentDefBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.GdDeleteDocumentBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterDocumentarySupportBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.DocumentarySupportBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.DocumentarySupportExpenseBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterDocumentarySupportBDTOStub;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.DocumentarySupport;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

public class DocumentarySupportServiceImplTest extends RestServerParentTest{
	
	@Mock
	private MyBatisTemplate myBatisTemplate;

	@Mock
	private IntegrationDocumentService service;
	
	@Mock
	private ReturnRDTO returnRDTO;
	
	@InjectMocks
	private DocumentarySupportServiceImpl serviceDocumentarySupport;
	
	private QueryParameterDocumentarySupportBDTO queryParameterDocumentarySupportBDTO;
	
	private DocumentarySupportBDTO documentarySupportBDTO;

	private DocumentarySupportExpenseBDTO documentarySupportExpenseBDTO;

	private DocumentarySupport documentarySupport;
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		queryParameterDocumentarySupportBDTO = QueryParameterDocumentarySupportBDTOStub.defaultOne();
		documentarySupportBDTO = DocumentarySupportBDTOStub.defaultOne();
		documentarySupportExpenseBDTO = DocumentarySupportExpenseBDTOStub.defaultOne();
		documentarySupport = new DocumentarySupport();
		documentarySupport.setCodeMunicipalDocument("1");
	}

	@Test
	public void caseOkSelectDocumentarySupport() throws Exception {
		serviceDocumentarySupport.selectDocumentarySupport(queryParameterDocumentarySupportBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectDocumentarySupport() throws Exception {
		serviceDocumentarySupport.selectDocumentarySupport(null);
	}

	@Test
	public void caseOkGetDocumentarySupport() throws Exception {
		serviceDocumentarySupport.getDocumentarySupport(queryParameterDocumentarySupportBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetDocumentarySupport() throws Exception {
		serviceDocumentarySupport.getDocumentarySupport(null);
	}

	@Test
	public void caseOkInsert() throws Exception {
		Mockito.when(service.createDocument(Mockito.any(GdAddDocumentDefBDTO.class))).thenReturn(AddDocumentsDefOutputStub.getMessageSuccess());		
		serviceDocumentarySupport.insert(documentarySupportBDTO);
	}
	
	@Test
	public void caseKOInsert() throws Exception {
		Mockito.when(service.createDocument(Mockito.any(GdAddDocumentDefBDTO.class))).thenReturn(AddDocumentsDefOutputStub.getMessageError());		
		serviceDocumentarySupport.insert(documentarySupportBDTO);
	}
	
	@Test
	public void caseExceptionInsert() throws Exception {
				
		serviceDocumentarySupport.insert(documentarySupportBDTO);
	}
	
	@Test
	public void caseExceptionDocumentumInsertNotEmptyDocumentList() throws Exception {
		documentarySupportBDTO.setDocumentarySupportFileBDTO(new DocumentarySupportFileBDTO());
		Mockito.doThrow(ImiException.class).when(service).createDocument(Mockito.any(GdAddDocumentDefBDTO.class));		
		serviceDocumentarySupport.insert(documentarySupportBDTO);
	}
	
	@Test
	public void caseOkUpdate() throws Exception {
		Mockito.when(myBatisTemplate.execute(Mockito.any(), Mockito.any())).thenReturn(documentarySupport);
		Mockito.when(service.deleteDocument(Mockito.any(GdDeleteDocumentBDTO.class))).thenReturn(DeleteDocumentsOutputStub.getMessageSuccess());				
		serviceDocumentarySupport.update(documentarySupportBDTO);
	}
	
	@Test
	public void caseExceptionDocumentumUpdate() throws Exception {
		Mockito.when(myBatisTemplate.execute(Mockito.any(), Mockito.any())).thenReturn(documentarySupport);
		Mockito.doThrow(ImiException.class).when(service).deleteDocument(Mockito.any(GdDeleteDocumentBDTO.class));
		serviceDocumentarySupport.update(documentarySupportBDTO);
	}
	
	@Test
	public void caseKoDocumentumUpdate() throws Exception {
		Mockito.when(myBatisTemplate.execute(Mockito.any(), Mockito.any())).thenReturn(documentarySupport);
		Mockito.when(service.deleteDocument(Mockito.any(GdDeleteDocumentBDTO.class))).thenReturn(DeleteDocumentsOutputStub.getMessageError());				
		serviceDocumentarySupport.update(documentarySupportBDTO);
	}
	@Test
	public void caseOkInsertExpense() throws Exception {
		Mockito.when(service.createDocument(Mockito.any(GdAddDocumentDefBDTO.class))).thenReturn(AddDocumentsDefOutputStub.getMessageSuccess());		
		serviceDocumentarySupport.insertExpense(documentarySupportExpenseBDTO);
	}
	
	@Test
	public void caseKOInsertExpense() throws Exception {
		Mockito.when(service.createDocument(Mockito.any(GdAddDocumentDefBDTO.class))).thenReturn(AddDocumentsDefOutputStub.getMessageError());		
		serviceDocumentarySupport.insertExpense(documentarySupportExpenseBDTO);
	}
	
	@Test
	public void caseExceptionInsertExpense() throws Exception {
				
		serviceDocumentarySupport.insertExpense(documentarySupportExpenseBDTO);
	}
	
	@Test
	public void caseExceptionDocumentumInsertExpenseNotEmptyDocumentList() throws Exception {
		documentarySupportExpenseBDTO.setDocumentarySupportFileBDTO(new DocumentarySupportFileBDTO());
		Mockito.doThrow(ImiException.class).when(service).createDocument(Mockito.any(GdAddDocumentDefBDTO.class));		
		serviceDocumentarySupport.insertExpense(documentarySupportExpenseBDTO);
	}
	
	@Test
	public void caseOkUpdateExpense() throws Exception {
		Mockito.when(myBatisTemplate.execute(Mockito.any(), Mockito.any())).thenReturn(documentarySupport);
		Mockito.when(service.deleteDocument(Mockito.any(GdDeleteDocumentBDTO.class))).thenReturn(DeleteDocumentsOutputStub.getMessageSuccess());				
		serviceDocumentarySupport.updateExpense(documentarySupportExpenseBDTO);
	}
	
	@Test
	public void caseExceptionDocumentumUpdateExpense() throws Exception {
		Mockito.when(myBatisTemplate.execute(Mockito.any(), Mockito.any())).thenReturn(documentarySupport);
		Mockito.doThrow(ImiException.class).when(service).deleteDocument(Mockito.any(GdDeleteDocumentBDTO.class));
		serviceDocumentarySupport.updateExpense(documentarySupportExpenseBDTO);
	}
	
	@Test
	public void caseKoDocumentumUpdateExpense() throws Exception {
		Mockito.when(myBatisTemplate.execute(Mockito.any(), Mockito.any())).thenReturn(documentarySupport);
		Mockito.when(service.deleteDocument(Mockito.any(GdDeleteDocumentBDTO.class))).thenReturn(DeleteDocumentsOutputStub.getMessageError());				
		serviceDocumentarySupport.updateExpense(documentarySupportExpenseBDTO);
	}
	
	
}
