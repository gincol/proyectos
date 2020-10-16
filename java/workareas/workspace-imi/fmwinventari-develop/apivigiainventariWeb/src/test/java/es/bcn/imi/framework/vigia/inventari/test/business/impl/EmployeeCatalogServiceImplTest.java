package es.bcn.imi.framework.vigia.inventari.test.business.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.bcn.imi.framework.vigia.inventari.business.impl.EmployeeCatalogServiceImpl;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.libcommons.business.dto.EmployeeCatalogBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.EmployeeCatalogBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

public class EmployeeCatalogServiceImplTest extends RestServerParentTest {
	
	@Mock
	private MyBatisTemplate myBatisTemplate;

	@Mock
	private ReturnRDTO returnRDTO;

	@InjectMocks
	private EmployeeCatalogServiceImpl service;

	private EmployeeCatalogBDTO employeeCatalogBDTO;
	
	private QueryParameterBDTO queryParameterBDTO;
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		employeeCatalogBDTO = EmployeeCatalogBDTOStub.defaultOne();
		queryParameterBDTO = QueryParameterBDTOStub.defaultOne();
	}

	@Test
	public void caseOkInsert() throws Exception {
		service.insert(employeeCatalogBDTO);
	}

	@Test(expected = NullPointerException.class)
	public void caseKoInsertNull() throws Exception {
		service.insert(null);
	}

	@Test
	public void caseOkSelectMassive() throws Exception {
		service.selectMassive(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectMassive() throws Exception {
		service.selectMassive(null);
	}

	@Test
	public void caseOkSelectDetailed() throws Exception {
		service.selectDetailed(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectDetailed() throws Exception {
		service.selectDetailed(null);
	}

	@Test
	public void caseOkGetEmployeeCatalogMassive() throws Exception {
		service.getEmployeeCatalogMassive(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetEmployeeCatalogMassive() throws Exception {
		service.getEmployeeCatalogMassive(null);
	}

	@Test
	public void caseOkGetEmployeeCatalogDetailed() throws Exception {
		service.getEmployeeCatalogDetailed(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetEmployeeCatalogDetailed() throws Exception {
		service.getEmployeeCatalogDetailed(null);
	}
}
