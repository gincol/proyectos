package es.bcn.imi.framework.vigia.inventari.test.business.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.bcn.imi.framework.vigia.inventari.business.impl.SensorServiceImpl;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.libcommons.business.dto.MaterialResourceBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.PhysicalModelBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.SensorBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterBDTOStub;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;
@SuppressWarnings("unused")
public class SensorServiceImplTest extends RestServerParentTest{

	
	@Mock
	private MyBatisTemplate myBatisTemplate;
	
	@InjectMocks
	private SensorServiceImpl sensorService;

	private SensorBDTO sensorBDTO;
	private MaterialResourceBDTO materialResourceBDTO;
	private PhysicalModelBDTO physicalModelBDTO;	
	private QueryParameterBDTO queryParameterBDTO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		sensorBDTO = new SensorBDTO();
		materialResourceBDTO = new MaterialResourceBDTO();
		physicalModelBDTO = new PhysicalModelBDTO();
		queryParameterBDTO = QueryParameterBDTOStub.defaultOne();
	}
	
	@Test
	public void caseOkInsert() throws Exception{
		sensorService.insert(sensorBDTO);	
	}

	@Test
	public void caseOkUpdate() throws Exception{
		sensorService.update(sensorBDTO);	
	}
	@Test
	public void caseOkSelectByCodeMCFString() throws Exception{
		sensorService.selectByCodeMCF(queryParameterBDTO.getMap());	
	}
	@Test
	public void caseOkSelectByCodeRRMMString() throws Exception{
		sensorService.selectByCodeRRMM(queryParameterBDTO.getMap());	
	}
	
}
