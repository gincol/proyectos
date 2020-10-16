package es.bcn.imi.framework.vigia.inventari.test.business.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import es.bcn.imi.framework.vigia.inventari.business.impl.InstallationServiceImpl;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisTemplateGap;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.integration.gap.inventary.installation.service.IntegrationGapInstallationService;
import es.bcn.vigia.fmw.integration.gap.inventary.test.installation.to.response.stub.CreateInstalacioResponseStub;
import es.bcn.vigia.fmw.integration.gap.inventary.test.installation.to.response.stub.UpdateInstalacioResponseStub;
import es.bcn.vigia.fmw.libcommons.business.dto.InstallationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.InstallationBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public class InstallationServiceImplTest extends RestServerParentTest {

	@Mock
	private IntegrationGapInstallationService service;
	
	@Mock
	private MyBatisTemplateGap myBatisTemplateGap;

	@Mock
	private ReturnRDTO returnRDTO;
	
	@InjectMocks
	private InstallationServiceImpl serviceInstallation;

	private InstallationBDTO installationBDTO;
	
	private QueryParameterBDTO queryParameterBDTO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		installationBDTO = InstallationBDTOStub.defaultOne();		
		queryParameterBDTO = QueryParameterBDTOStub.defaultOne();
	}

	@Test
	public void caseOkInsert() throws Exception {
		Mockito.when(service.create(installationBDTO)).thenReturn(CreateInstalacioResponseStub.getMessageSuccess());
		serviceInstallation.insert(installationBDTO);
	}

	@Test
	public void caseKoInsertCodeError() throws Exception {
		Mockito.when(service.create(installationBDTO)).thenReturn(CreateInstalacioResponseStub.getMessageError());
		serviceInstallation.insert(installationBDTO);
	}

	@Test
	public void caseKoInsertEmpty() throws Exception {
		Mockito.when(service.create(installationBDTO)).thenReturn(CreateInstalacioResponseStub.empty());
		serviceInstallation.insert(installationBDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsert() throws Exception {
		serviceInstallation.insert(installationBDTO);
	}

	@Test
	public void caseOkUpdate() throws Exception {
		Mockito.when(service.update(installationBDTO)).thenReturn(UpdateInstalacioResponseStub.getMessageSuccess());
		serviceInstallation.update(installationBDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoUpdate() throws Exception {
		serviceInstallation.update(installationBDTO);
	}

	@Test
	public void caseOkUpdateCodeError() throws Exception {
		Mockito.when(service.update(installationBDTO)).thenReturn(UpdateInstalacioResponseStub.getMessageError());
		serviceInstallation.update(installationBDTO);
	}

	@Test
	public void caseOkUpdateEmpty() throws Exception {
		Mockito.when(service.update(installationBDTO)).thenReturn(UpdateInstalacioResponseStub.empty());
		serviceInstallation.update(installationBDTO);
	}

	@Test
	public void caseOkDelete() throws Exception {
		Mockito.when(service.update(installationBDTO)).thenReturn(UpdateInstalacioResponseStub.getMessageSuccess());
		serviceInstallation.delete(installationBDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoDelete() throws Exception {
		serviceInstallation.delete(installationBDTO);
	}

	@Test
	public void caseKoDeleteCodeError() throws Exception {
		Mockito.when(service.update(installationBDTO)).thenReturn(UpdateInstalacioResponseStub.getMessageError());
		serviceInstallation.delete(installationBDTO);
	}

	@Test
	public void caseKoDeleteEmpty() throws Exception {
		Mockito.when(service.update(installationBDTO)).thenReturn(UpdateInstalacioResponseStub.empty());
		serviceInstallation.delete(installationBDTO);
	}
	
	@Test
	public void caseOkInsertExpense() throws Exception {
		Mockito.when(service.createExpense(installationBDTO)).thenReturn(UpdateInstalacioResponseStub.getMessageSuccess());
		serviceInstallation.insertExpense(installationBDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertExpense() throws Exception {
		serviceInstallation.insertExpense(installationBDTO);
	}

	@Test
	public void caseOkInsertExpenseCodeError() throws Exception {
		Mockito.when(service.createExpense(installationBDTO)).thenReturn(UpdateInstalacioResponseStub.getMessageError());
		serviceInstallation.insertExpense(installationBDTO);
	}
	
	@Test
	public void caseOkInsertAmortizationBase() throws Exception {
		Mockito.when(service.createAmortizationBase(installationBDTO)).thenReturn(UpdateInstalacioResponseStub.getMessageSuccess());
		serviceInstallation.insertAmortizationBase(installationBDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertAmortizationBase() throws Exception {
		serviceInstallation.insertAmortizationBase(installationBDTO);
	}

	@Test
	public void caseOkInsertAmortizationBaseCodeError() throws Exception {
		Mockito.when(service.createAmortizationBase(installationBDTO)).thenReturn(UpdateInstalacioResponseStub.getMessageError());
		serviceInstallation.insertAmortizationBase(installationBDTO);
	}
	
	@Test
	public void caseOkInsertApportionment() throws Exception {
		Mockito.when(service.createApportionment(installationBDTO)).thenReturn(UpdateInstalacioResponseStub.getMessageSuccess());
		serviceInstallation.insertApportionment(installationBDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertApportionment() throws Exception {
		serviceInstallation.insertApportionment(installationBDTO);
	}

	@Test
	public void caseOkInsertApportionmentCodeError() throws Exception {
		Mockito.when(service.createApportionment(installationBDTO)).thenReturn(UpdateInstalacioResponseStub.getMessageError());
		serviceInstallation.insertApportionment(installationBDTO);
	}

	@Test
	public void caseOkSelectDetailed() throws Exception {
		serviceInstallation.selectDetailed(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectDetailed() throws Exception {
		serviceInstallation.selectDetailed(null);
	}

	@Test
	public void caseOkSelectMassive() throws Exception {
		serviceInstallation.selectMassive(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectMassive() throws Exception {
		serviceInstallation.selectMassive(null);
	}

	@Test
	public void caseOkSelectAmortization() throws Exception {
		serviceInstallation.selectAmortization(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectAmortization() throws Exception {
		serviceInstallation.selectAmortization(null);
	}

	@Test
	public void caseOkSelectExpenses() throws Exception {
		serviceInstallation.selectExpenses(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectExpenses() throws Exception {
		serviceInstallation.selectExpenses(null);
	}

	@Test
	public void caseOkSelectPeriod() throws Exception {
		serviceInstallation.selectPeriod(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectPeriod() throws Exception {
		serviceInstallation.selectPeriod(null);
	}

	@Test
	public void caseOkGetInstallationDetailed() throws Exception {
		serviceInstallation.getInstallationDetailed(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetInstallationDetailed() throws Exception {
		serviceInstallation.getInstallationDetailed(null);
	}

	@Test
	public void caseOkGetAmortizationBases() throws Exception {
		serviceInstallation.getAmortizationBases(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetAmortizationBases() throws Exception {
		serviceInstallation.getAmortizationBases(null);
	}

	@Test
	public void caseOkGetApportionments() throws Exception {
		serviceInstallation.getApportionments(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetApportionments() throws Exception {
		serviceInstallation.getApportionments(null);
	}

	@Test
	public void caseOkGetExpenses() throws Exception {
		serviceInstallation.getExpenses(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetExpenses() throws Exception {
		serviceInstallation.getExpenses(null);
	}

	@Test
	public void caseOkGetDetailCertificationAmortization() throws Exception {
		serviceInstallation.getDetailCertificationAmortization(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetDetailCertificationAmortization() throws Exception {
		serviceInstallation.getDetailCertificationAmortization(null);
	}

	@Test
	public void caseOkGetDetailCertificationExpenses() throws Exception {
		serviceInstallation.getDetailCertificationExpenses(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetDetailCertificationExpenses() throws Exception {
		serviceInstallation.getDetailCertificationExpenses(null);
	}

	@Test
	public void caseOkGetInstallationMassive() throws Exception {
		serviceInstallation.getInstallationMassive(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetInstallationMassive() throws Exception {
		serviceInstallation.getInstallationMassive(null);
	}

	@Test
	public void caseOkGetInstallationAmortization() throws Exception {
		serviceInstallation.getInstallationAmortization(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetInstallationAmortization() throws Exception {
		serviceInstallation.getInstallationAmortization(null);
	}

	@Test
	public void caseOkGetInstallationExpenses() throws Exception {
		serviceInstallation.getInstallationExpenses(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetInstallationExpenses() throws Exception {
		serviceInstallation.getInstallationExpenses(null);
	}

	@Test
	public void caseOkGetInstallationPeriod() throws Exception {
		serviceInstallation.getPeriod(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetInstallationPeriod() throws Exception {
		serviceInstallation.getPeriod(null);
	}

	@Test
	public void caseOkGetPeriod() throws Exception {
		serviceInstallation.getPeriod(queryParameterBDTO);
	}

	@Test(expected = Exception.class)
	public void caseKoGetPeriod() throws Exception {
		serviceInstallation.getPeriod(null);
	}

}
