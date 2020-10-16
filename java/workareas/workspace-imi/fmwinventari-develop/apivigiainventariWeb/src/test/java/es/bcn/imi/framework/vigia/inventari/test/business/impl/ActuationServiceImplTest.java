package es.bcn.imi.framework.vigia.inventari.test.business.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.bcn.imi.framework.vigia.inventari.business.impl.ActuationServiceImpl;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.libcommons.business.dto.ActuationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterActuationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.ActuationBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterActuationBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

public class ActuationServiceImplTest extends RestServerParentTest {

	
	@Mock
	private ReturnRDTO returnRDTO;

	@InjectMocks
	private ActuationServiceImpl actuationService;

	private ActuationBDTO actuationBDTO;
	
	@Mock
	private MyBatisTemplate myBatisTemplate;
	
	private QueryParameterActuationBDTO queryParameterActuationBDTO;

	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		actuationBDTO = ActuationBDTOStub.defaultOne();	
		queryParameterActuationBDTO = QueryParameterActuationBDTOStub.defaultOne();	
	}

	@Test
	public void caseOkInsert() throws Exception {
		
		actuationService.insert(actuationBDTO);
	}

	@Test
	public void caseOkDelete() throws Exception {
		
		actuationService.delete(actuationBDTO);
	}

	@Test
	public void caseOkSelectDocumentarySupport() throws Exception {
		actuationService.selectActuation(queryParameterActuationBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectDocumentarySupport() throws Exception {
		actuationService.selectActuation(null);
	}

	@Test
	public void caseOkGetDocumentarySupport() throws Exception {
		actuationService.getActuation(queryParameterActuationBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetDocumentarySupport() throws Exception {
		actuationService.getActuation(null);
	}
	
}