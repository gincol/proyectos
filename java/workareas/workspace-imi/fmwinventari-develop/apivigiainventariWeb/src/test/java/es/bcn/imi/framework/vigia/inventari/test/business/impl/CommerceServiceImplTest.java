package es.bcn.imi.framework.vigia.inventari.test.business.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import es.bcn.imi.framework.vigia.inventari.business.impl.CommerceServiceImpl;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisTemplateGap;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.integration.gap.inventary.comerc.service.IntegrationGapCommerceService;
import es.bcn.vigia.fmw.integration.gap.inventary.test.commerce.to.response.stub.CreateComercResponseStub;
import es.bcn.vigia.fmw.integration.gap.inventary.test.commerce.to.response.stub.UpdateComercResponseStub;
import es.bcn.vigia.fmw.libcommons.business.dto.CommerceBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.CommerceBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public class CommerceServiceImplTest extends RestServerParentTest {

	@Mock
	private IntegrationGapCommerceService service;
	
	@Mock
	private MyBatisTemplateGap myBatisTemplateGap;

	@Mock
	private ReturnRDTO returnRDTO;

	@InjectMocks
	private CommerceServiceImpl serviceCommerce;

	private CommerceBDTO commerceBDTO;
	
	private QueryParameterBDTO queryParameterBDTO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		commerceBDTO = CommerceBDTOStub.defaultOne();
		queryParameterBDTO = QueryParameterBDTOStub.defaultOne();
	}

	@Test
	public void caseOkInsert() throws Exception {
		Mockito.when(service.create(commerceBDTO)).thenReturn(CreateComercResponseStub.getMessageSuccess());
		serviceCommerce.insert(commerceBDTO);
	}

	@Test
	public void caseKoInsertCodeError() throws Exception {
		Mockito.when(service.create(commerceBDTO)).thenReturn(CreateComercResponseStub.getMessageError());
		serviceCommerce.insert(commerceBDTO);
	}

	@Test
	public void caseKoInsertEmpty() throws Exception {
		Mockito.when(service.create(commerceBDTO)).thenReturn(CreateComercResponseStub.empty());
		serviceCommerce.insert(commerceBDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsert() throws Exception {
		serviceCommerce.insert(commerceBDTO);
	}

	@Test
	public void caseOkUpdate() throws Exception {
		Mockito.when(service.update(commerceBDTO)).thenReturn(UpdateComercResponseStub.getMessageSuccess());
		serviceCommerce.update(commerceBDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoUpdate() throws Exception {
		serviceCommerce.update(commerceBDTO);
	}

	@Test
	public void caseOkUpdateCodeError() throws Exception {
		Mockito.when(service.update(commerceBDTO)).thenReturn(UpdateComercResponseStub.getMessageError());
		serviceCommerce.update(commerceBDTO);
	}

	@Test
	public void caseOkUpdateEmpty() throws Exception {
		Mockito.when(service.update(commerceBDTO)).thenReturn(UpdateComercResponseStub.empty());
		serviceCommerce.update(commerceBDTO);
	}

	@Test
	public void caseOkDelete() throws Exception {
		Mockito.when(service.update(commerceBDTO)).thenReturn(UpdateComercResponseStub.getMessageSuccess());
		serviceCommerce.delete(commerceBDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoDelete() throws Exception {
		serviceCommerce.delete(commerceBDTO);
	}

	@Test
	public void caseKoDeleteCodeError() throws Exception {
		Mockito.when(service.update(commerceBDTO)).thenReturn(UpdateComercResponseStub.getMessageError());
		serviceCommerce.delete(commerceBDTO);
	} 

	@Test
	public void caseKoDeleteEmpty() throws Exception {
		Mockito.when(service.update(commerceBDTO)).thenReturn(UpdateComercResponseStub.empty());
		serviceCommerce.delete(commerceBDTO);
	}
	
	@Test
	public void caseOkInsertElements() throws Exception {
		Mockito.when(service.createElements(commerceBDTO)).thenReturn(UpdateComercResponseStub.getMessageSuccess());
		serviceCommerce.insertElements(commerceBDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertElements() throws Exception {
		serviceCommerce.insertElements(commerceBDTO);
	}

	@Test
	public void caseOkInsertElementsCodeError() throws Exception {
		Mockito.when(service.createElements(commerceBDTO)).thenReturn(UpdateComercResponseStub.getMessageError());
		serviceCommerce.insertElements(commerceBDTO);
	}
	
	@Test
	public void caseKoInsertElementsEmpty() throws Exception {
		Mockito.when(service.createElements(commerceBDTO)).thenReturn(UpdateComercResponseStub.empty());
		serviceCommerce.insertElements(commerceBDTO);
	}

	@Test
	public void caseOkSelectMassive() throws Exception {
		serviceCommerce.selectMassive(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectMassive() throws Exception {
		serviceCommerce.selectMassive(null);
	}

	@Test
	public void caseOkSelectDetailed() throws Exception {
		serviceCommerce.selectDetailed(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectDetailed() throws Exception {
		serviceCommerce.selectDetailed(null);
	}

	@Test
	public void caseOkSelectElements() throws Exception {
		serviceCommerce.selectElements(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectElements() throws Exception {
		serviceCommerce.selectElements(null);
	}

	@Test
	public void caseOkGetCommerceMassive() throws Exception {
		serviceCommerce.getCommerceMassive(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetCommerceMassive() throws Exception {
		serviceCommerce.getCommerceMassive(null);
	}

	@Test
	public void caseOkGetCommerceDetailed() throws Exception {
		serviceCommerce.getCommerceDetailed(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetCommerceDetailed() throws Exception {
		serviceCommerce.getCommerceDetailed(null);
	}

	@Test
	public void caseOkGetCommerceElements() throws Exception {
		serviceCommerce.getCommerceElements(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetCommerceElements() throws Exception {
		serviceCommerce.getCommerceElements(null);
	}
}
