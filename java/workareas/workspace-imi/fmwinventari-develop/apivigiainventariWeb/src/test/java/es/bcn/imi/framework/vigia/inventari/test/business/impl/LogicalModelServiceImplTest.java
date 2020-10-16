package es.bcn.imi.framework.vigia.inventari.test.business.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import es.bcn.imi.framework.vigia.inventari.business.PhysicalModelService;
import es.bcn.imi.framework.vigia.inventari.business.impl.LogicalModelServiceImpl;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisTemplateGap;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.integration.gap.inventary.logicalmodel.service.impl.IntegrationGapLogicalModelServiceImpl;
import es.bcn.vigia.fmw.integration.gap.inventary.test.logicalmodel.to.response.stub.CreateMobiliariLogicResponseStub;
import es.bcn.vigia.fmw.integration.gap.inventary.test.logicalmodel.to.response.stub.DeleteMobiliariLogicResponseStub;
import es.bcn.vigia.fmw.integration.gap.inventary.test.logicalmodel.to.response.stub.ReadMobiliariLogicResponseStub;
import es.bcn.vigia.fmw.integration.gap.inventary.test.logicalmodel.to.response.stub.UpdateMobiliariLogicResponseStub;
import es.bcn.vigia.fmw.libcommons.business.dto.LogicalModelBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.LogicalModelBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;

public class LogicalModelServiceImplTest extends RestServerParentTest {

	@Mock
	private IntegrationGapLogicalModelServiceImpl service;

	@Mock
	private PhysicalModelService servicePhysicalModel;

	@Mock
	private ReturnRDTO returnRDTO;
	
	@Mock
	private MyBatisTemplateGap myBatisTemplateGap;

	@InjectMocks
	private LogicalModelServiceImpl logicalModelService;

	private LogicalModelBDTO logicalModelBDTO;
	
	private QueryParameterBDTO queryParameterBDTO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		logicalModelBDTO = LogicalModelBDTOStub.defaultOne();
		queryParameterBDTO = QueryParameterBDTOStub.defaultOne();
	}

	@Test
	public void caseOkSelect() throws Exception {
		Mockito.when(service.read(logicalModelBDTO.getCode()))
				.thenReturn(ReadMobiliariLogicResponseStub.getMessageSuccess());
		logicalModelService.select(logicalModelBDTO);
	}

	@Test
	public void caseKoSelectNull() throws Exception {
		Mockito.when(service.read(logicalModelBDTO.getCode()))
				.thenReturn(ReadMobiliariLogicResponseStub.resultsUbicacioNull());
		logicalModelService.select(logicalModelBDTO);
	}

	@Test
	public void caseKoSelectIsEmpty() throws Exception {
		Mockito.when(service.read(logicalModelBDTO.getCode()))
				.thenReturn(ReadMobiliariLogicResponseStub.resultsEmpty());
		logicalModelService.select(logicalModelBDTO);
	}

	@Test
	public void caseOkInsert() throws Exception {
		Mockito.when(service.create(logicalModelBDTO)).thenReturn(CreateMobiliariLogicResponseStub.getMessageSuccess());
		logicalModelService.insert(logicalModelBDTO);
	}

	@Test
	public void caseOkInsertCodeError() throws Exception {
		Mockito.when(service.create(logicalModelBDTO)).thenReturn(CreateMobiliariLogicResponseStub.empty());
		logicalModelService.insert(logicalModelBDTO);
	}

	@Test
	public void caseOkUpdate() throws Exception {
		Mockito.when(service.update(logicalModelBDTO)).thenReturn(UpdateMobiliariLogicResponseStub.getMessageSuccess());
		logicalModelService.update(logicalModelBDTO);
	}

	@Test
	public void caseOkUpdateCodeError() throws Exception {
		Mockito.when(service.update(logicalModelBDTO)).thenReturn(UpdateMobiliariLogicResponseStub.empty());
		logicalModelService.update(logicalModelBDTO);
	}

	@Test
	public void caseOkDelete() throws Exception {
		Mockito.when(service.delete(logicalModelBDTO.getCode()))
				.thenReturn(DeleteMobiliariLogicResponseStub.getMessageSuccess());
		logicalModelService.delete(logicalModelBDTO);
	}

	@Test
	public void caseKoDelete() throws Exception {
		logicalModelService.delete(logicalModelBDTO);
	}

	@Test
	public void caseOkDeleteCodeError() throws Exception {
		Mockito.when(service.delete(logicalModelBDTO.getCode())).thenReturn(DeleteMobiliariLogicResponseStub.empty());
		logicalModelService.delete(logicalModelBDTO);
	}

	@Test
	public void caseOkSelectMassive() throws Exception {
		logicalModelService.selectMassive(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectMassive() throws Exception {
		queryParameterBDTO.setPeriod(" ");
		logicalModelService.selectMassive(queryParameterBDTO);
	}

	@Test
	public void caseOkSelectDetailed() throws Exception {
		logicalModelService.selectDetailed(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectDetailed() throws Exception {
		queryParameterBDTO.setPeriod(" ");
		logicalModelService.selectDetailed(queryParameterBDTO);
	}

	@Test
	public void caseOkGetMclMassive() throws Exception {
		logicalModelService.getMclMassive(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetMclMassive() throws Exception {
		queryParameterBDTO.setPeriod(" ");
		logicalModelService.getMclMassive(queryParameterBDTO);
	}

	@Test
	public void caseOkGetMclDetailed() throws Exception {
		logicalModelService.getMclDetailed(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetMclDetailed() throws Exception {
		queryParameterBDTO.setPeriod(" ");
		logicalModelService.getMclDetailed(queryParameterBDTO);
	}
}
