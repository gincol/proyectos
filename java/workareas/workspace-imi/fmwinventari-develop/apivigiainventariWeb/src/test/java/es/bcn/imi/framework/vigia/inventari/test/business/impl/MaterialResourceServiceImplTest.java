package es.bcn.imi.framework.vigia.inventari.test.business.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import es.bcn.imi.framework.vigia.inventari.business.impl.MaterialResourceServiceImpl;
import es.bcn.imi.framework.vigia.inventari.business.impl.SensorServiceImpl;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisTemplateGap;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.integration.gap.inventary.materialresource.to.service.IntegrationGapMaterialResourceService;
import es.bcn.vigia.fmw.integration.gap.inventary.test.materialresource.to.response.stub.CreateRecursMaterialResponseStub;
import es.bcn.vigia.fmw.integration.gap.inventary.test.materialresource.to.response.stub.DeleteRecursMaterialResponseStub;
import es.bcn.vigia.fmw.integration.gap.inventary.test.materialresource.to.response.stub.UpdateRecursMaterialResponseStub;
import es.bcn.vigia.fmw.libcommons.business.dto.MaterialResourceBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.MaterialResourceBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

public class MaterialResourceServiceImplTest extends RestServerParentTest {

	@Mock
	private IntegrationGapMaterialResourceService service;
	
	@Mock
	private MyBatisTemplate myBatisTemplate;
	
	@Mock
	private MyBatisTemplateGap myBatisTemplateGap;
	
	@Mock
	private SensorServiceImpl sensorService;

	@Mock
	private ReturnRDTO returnRDTO;
	
	@InjectMocks
	private MaterialResourceServiceImpl materialResourceService;

	private MaterialResourceBDTO materialResourceBDTO;
	
	private QueryParameterBDTO queryParameterBDTO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		materialResourceBDTO = MaterialResourceBDTOStub.defaultOne();
		queryParameterBDTO = QueryParameterBDTOStub.defaultOne();
	}

	@Test
	public void caseOkInsert() throws Exception {
		Mockito.when(service.create(materialResourceBDTO))
				.thenReturn(CreateRecursMaterialResponseStub.getMessageSuccess());
		materialResourceService.insert(materialResourceBDTO);
	}

	@Test
	public void caseOkInsertWithRegistration() throws Exception {
		materialResourceBDTO.setRegistration("");
		Mockito.when(service.create(materialResourceBDTO))
				.thenReturn(CreateRecursMaterialResponseStub.getMessageSuccess());
		materialResourceService.insert(materialResourceBDTO);
	}

	@Test
	public void caseOkInsertCodeError() throws Exception {
		Mockito.when(service.create(materialResourceBDTO)).thenReturn(CreateRecursMaterialResponseStub.empty());
		materialResourceService.insert(materialResourceBDTO);
	}

	@Test
	public void caseOkUpdate() throws Exception {
		Mockito.when(service.update(materialResourceBDTO))
				.thenReturn(UpdateRecursMaterialResponseStub.getMessageSuccess());
		materialResourceService.update(materialResourceBDTO);
	}

	@Test
	public void caseOkUpdateCodeError() throws Exception {
		Mockito.when(service.update(materialResourceBDTO)).thenReturn(UpdateRecursMaterialResponseStub.empty());
		materialResourceService.update(materialResourceBDTO);
	}
	
	@Test
	public void caseOkDelete() throws Exception {
		Mockito.when(service.delete(materialResourceBDTO.getCode()))
				.thenReturn(DeleteRecursMaterialResponseStub.getMessageSuccess());
		materialResourceService.delete(materialResourceBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoDelete() throws Exception {
		materialResourceService.delete(null);
	}

	@Test
	public void caseOkSelectMassive() throws Exception {
		materialResourceService.selectMassive(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectMassive() throws Exception {
		materialResourceService.selectMassive(null);
	}

	@Test
	public void caseOkSelectDetailed() throws Exception {
		materialResourceService.selectDetailed(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectDetailed() throws Exception {
		materialResourceService.selectDetailed(null);
	}

	@Test
	public void caseOkSelectVehicleMassive() throws Exception {
		materialResourceService.selectVehicleMassive(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectVehicleMassive() throws Exception {
		materialResourceService.selectVehicleMassive(null);
	}

	@Test
	public void caseOkSelectVehicleDetailed() throws Exception {
		materialResourceService.selectVehicleDetailed(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectVehicleDetailed() throws Exception {
		materialResourceService.selectVehicleDetailed(null);
	}

	@Test
	public void caseOkGetMaterialResourceMassive() throws Exception {
		materialResourceService.getMaterialResourceMassive(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetMaterialResourceMassive() throws Exception {
		materialResourceService.getMaterialResourceMassive(null);
	}

	@Test
	public void caseOkGetMaterialResourceDetailed() throws Exception {
		materialResourceService.getMaterialResourceDetailed(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetMaterialResourceDetailed() throws Exception {
		materialResourceService.getMaterialResourceDetailed(null);
	}

	@Test
	public void caseOkGetMaterialResourceVehicleMassive() throws Exception {
		materialResourceService.getMaterialResourceVehicleMassive(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetMaterialResourceVehicleMassive() throws Exception {
		materialResourceService.getMaterialResourceVehicleMassive(null);
	}

	@Test
	public void caseOkGetMaterialResourceVehicleDetailed() throws Exception {
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		materialResourceService.getMaterialResourceVehicleDetailed(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetMaterialResourceVehicleDetailed() throws Exception {
		materialResourceService.getMaterialResourceVehicleDetailed(null);
	}

	@Test
	public void caseOkGetApportionments() throws Exception {
		materialResourceService.getApportionments(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetApportionments() throws Exception {
		materialResourceService.getApportionments(null);
	}

	@Test
	public void caseOkGetDetailCertificationAmortization() throws Exception {
		materialResourceService.getDetailCertificationAmortization(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetDetailCertificationAmortization() throws Exception {
		materialResourceService.getDetailCertificationAmortization(null);
	}

	@Test
	public void caseOkGetDetailCertificationExpenses() throws Exception {
		materialResourceService.getDetailCertificationExpenses(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetDetailCertificationExpenses() throws Exception {
		materialResourceService.getDetailCertificationExpenses(null);
	}
	
	@Test
	public void caseOkInsertExpense() throws Exception {
		Mockito.when(service.createExpense(materialResourceBDTO)).thenReturn(UpdateRecursMaterialResponseStub.getMessageSuccess());
		materialResourceService.insertExpense(materialResourceBDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertExpense() throws Exception {
		materialResourceService.insertExpense(materialResourceBDTO);
	}

	@Test
	public void caseOkInsertExpenseCodeError() throws Exception {
		Mockito.when(service.createExpense(materialResourceBDTO)).thenReturn(UpdateRecursMaterialResponseStub.getMessageError());
		materialResourceService.insertExpense(materialResourceBDTO);
	}

	@Test
	public void caseOkInsertAmortizationBase() throws Exception {
		Mockito.when(service.createAmortizationBase(materialResourceBDTO)).thenReturn(UpdateRecursMaterialResponseStub.getMessageSuccess());
		materialResourceService.insertAmortizationBase(materialResourceBDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertAmortizationBase() throws Exception {
		materialResourceService.insertAmortizationBase(materialResourceBDTO);
	}

	@Test
	public void caseOkInsertAmortizationBaseCodeError() throws Exception {
		Mockito.when(service.createAmortizationBase(materialResourceBDTO)).thenReturn(UpdateRecursMaterialResponseStub.getMessageError());
		materialResourceService.insertAmortizationBase(materialResourceBDTO);
	}
	
	@Test
	public void caseOkInsertApportionment() throws Exception {
		Mockito.when(service.createApportionment(materialResourceBDTO)).thenReturn(UpdateRecursMaterialResponseStub.getMessageSuccess());
		materialResourceService.insertApportionment(materialResourceBDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertApportionment() throws Exception {
		materialResourceService.insertApportionment(materialResourceBDTO);
	}

	@Test
	public void caseOkInsertApportionmentCodeError() throws Exception {
		Mockito.when(service.createApportionment(materialResourceBDTO)).thenReturn(UpdateRecursMaterialResponseStub.getMessageError());
		materialResourceService.insertApportionment(materialResourceBDTO);
	}

	@Test
	public void caseOkSelectAmortization() throws Exception {
		materialResourceService.selectAmortization(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectAmortization() throws Exception {
		materialResourceService.selectAmortization(null);
	}

	@Test
	public void caseOkSelectExpenses() throws Exception {
		materialResourceService.selectExpenses(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectExpenses() throws Exception {
		materialResourceService.selectExpenses(null);
	}

	@Test
	public void caseOkSelectPeriod() throws Exception {
		materialResourceService.selectPeriod(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectPeriod() throws Exception {
		materialResourceService.selectPeriod(null);
	}

	@Test
	public void caseOkGetAmortizationBases() throws Exception {
		materialResourceService.getAmortizationBases(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetAmortizationBases() throws Exception {
		materialResourceService.getAmortizationBases(null);
	}

	@Test
	public void caseOkGetExpenses() throws Exception {
		materialResourceService.getExpenses(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetExpenses() throws Exception {
		materialResourceService.getExpenses(null);
	}

	@Test
	public void caseOkGetPeriod() throws Exception {
		materialResourceService.getPeriod(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetPeriod() throws Exception {
		materialResourceService.getPeriod(null);
	}
}
