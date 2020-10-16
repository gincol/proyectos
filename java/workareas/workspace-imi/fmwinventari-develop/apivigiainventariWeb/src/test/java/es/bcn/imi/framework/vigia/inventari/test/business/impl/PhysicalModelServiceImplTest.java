package es.bcn.imi.framework.vigia.inventari.test.business.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import es.bcn.imi.framework.vigia.inventari.business.LogicalModelService;
import es.bcn.imi.framework.vigia.inventari.business.impl.PhysicalModelServiceImpl;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisTemplateGap;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.integration.gap.inventary.aggregateamortization.service.IntegrationGapAggregateAmortizationService;
import es.bcn.vigia.fmw.integration.gap.inventary.physicalmodel.service.impl.IntegrationGapPhysicalModelServiceImpl;
import es.bcn.vigia.fmw.integration.gap.inventary.test.aggregateamortizacion.to.response.stub.CreateAmortAgregadaResponseStub;
import es.bcn.vigia.fmw.integration.gap.inventary.test.physicalmodel.to.response.stub.CreateMobiliariFisicResponseStub;
import es.bcn.vigia.fmw.integration.gap.inventary.test.physicalmodel.to.response.stub.DeleteMobiliariFisicResponseStub;
import es.bcn.vigia.fmw.integration.gap.inventary.test.physicalmodel.to.response.stub.UpdateMobiliariFisicResponseStub;
import es.bcn.vigia.fmw.libcommons.business.dto.AggregateAmortizationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.PhysicalModelBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.AggregateAmortizationBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.PhysicalModelBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public class PhysicalModelServiceImplTest extends RestServerParentTest{

	@Mock
	private IntegrationGapPhysicalModelServiceImpl service;
	
	@Mock
	private LogicalModelService logicalModelService;

	@Mock
	private IntegrationGapAggregateAmortizationService aggregateAmortizationservice;
	
	@Mock
	private MyBatisTemplateGap myBatisTemplateGap;

	@Mock
	private ReturnRDTO returnRDTO;
	
	@InjectMocks
	private PhysicalModelServiceImpl servicePhysicalModel;

	private PhysicalModelBDTO physicalModelBDTO;

	private AggregateAmortizationBDTO aggregateAmortizationBDTO;
	
	private QueryParameterBDTO queryParameterBDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		physicalModelBDTO = PhysicalModelBDTOStub.defaultOne();
		aggregateAmortizationBDTO = AggregateAmortizationBDTOStub.defaultOne();
		queryParameterBDTO = QueryParameterBDTOStub.defaultOne();
	}
	
	@Test
	public void caseOkInsert() throws Exception {
		Mockito.when(service.create(physicalModelBDTO)).thenReturn(CreateMobiliariFisicResponseStub.getMessageSuccess());
		servicePhysicalModel.insert(physicalModelBDTO);
	}
	
	@Test
	public void caseOkInsertCodeError() throws Exception {
		Mockito.when(service.create(physicalModelBDTO)).thenReturn(CreateMobiliariFisicResponseStub.empty());
		servicePhysicalModel.insert(physicalModelBDTO);
	}
	
	@Test
	public void caseOkUpdate() throws Exception {
		Mockito.when(service.update(physicalModelBDTO)).thenReturn(UpdateMobiliariFisicResponseStub.getMessageSuccess());
		servicePhysicalModel.update(physicalModelBDTO);
	}
	
	@Test
	public void caseOkUpdateCodeError() throws Exception {
		Mockito.when(service.update(physicalModelBDTO)).thenReturn(UpdateMobiliariFisicResponseStub.empty());
		servicePhysicalModel.update(physicalModelBDTO);
	}
	
	@Test
	public void caseOkDelete() throws Exception {
		Mockito.when(service.delete(physicalModelBDTO.getCode()))
				.thenReturn(DeleteMobiliariFisicResponseStub.getMessageSuccess());
		servicePhysicalModel.delete(physicalModelBDTO);
	}
	
	@Test
	public void caseKoDelete() throws Exception {
		servicePhysicalModel.delete(physicalModelBDTO);
	}

	@Test
	public void caseOkDeleteCodeError() throws Exception {
		Mockito.when(service.delete(physicalModelBDTO.getCode()))
		.thenReturn(DeleteMobiliariFisicResponseStub.empty());
		servicePhysicalModel.delete(physicalModelBDTO);
	}
	
	@Test
	public void caseOkInsertAmortitzation() throws Exception {
		Mockito.when(aggregateAmortizationservice.create(aggregateAmortizationBDTO)).thenReturn(CreateAmortAgregadaResponseStub.getMessageSuccess());
		servicePhysicalModel.insert(aggregateAmortizationBDTO);
	}

	@Test
	public void caseEmptyInsertAmortitzation() throws Exception {
		Mockito.when(aggregateAmortizationservice.create(aggregateAmortizationBDTO)).thenReturn(CreateAmortAgregadaResponseStub.empty());
		servicePhysicalModel.insert(aggregateAmortizationBDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoInsertAmortitzation() throws Exception {
		Mockito.when(aggregateAmortizationservice.create(aggregateAmortizationBDTO)).thenReturn(null);
		servicePhysicalModel.insert(aggregateAmortizationBDTO);
	}

	@Test
	public void caseOkSelectMassive() throws Exception {
		servicePhysicalModel.selectMassive(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectMassive() throws Exception {
		queryParameterBDTO.setPeriod(" ");
		servicePhysicalModel.selectMassive(queryParameterBDTO);
	}

	@Test
	public void caseOkSelectDetailed() throws Exception {
		servicePhysicalModel.selectDetailed(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectDetailed() throws Exception {
		queryParameterBDTO.setPeriod(" ");
		servicePhysicalModel.selectDetailed(queryParameterBDTO);
	}

	@Test
	public void caseOkGetPhysicalModelMassive() throws Exception {
		servicePhysicalModel.getPhysicalModelMassive(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetPhysicalModelMassive() throws Exception {
		queryParameterBDTO.setPeriod(" ");
		servicePhysicalModel.getPhysicalModelMassive(queryParameterBDTO);
	}

	@Test
	public void caseOkGetPhysicalModelDetailed() throws Exception {
		servicePhysicalModel.getPhysicalModelDetailed(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetPhysicalModelDetailed() throws Exception {
		queryParameterBDTO.setPeriod(" ");
		servicePhysicalModel.getPhysicalModelDetailed(queryParameterBDTO);
	}

	@Test
	public void caseOkGetPhysicalModelAmortization() throws Exception {
		servicePhysicalModel.getPhysicalModelAmortization(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetPhysicalModelAmortization() throws Exception {
		queryParameterBDTO.setPeriod(" ");
		servicePhysicalModel.getPhysicalModelAmortization(queryParameterBDTO);
	}

}
