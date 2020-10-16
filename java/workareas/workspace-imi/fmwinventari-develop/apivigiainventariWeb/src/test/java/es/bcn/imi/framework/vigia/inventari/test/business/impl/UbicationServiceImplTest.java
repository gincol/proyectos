package es.bcn.imi.framework.vigia.inventari.test.business.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import es.bcn.imi.framework.vigia.inventari.business.impl.UbicationServiceImpl;
import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisTemplateGap;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.integration.gap.inventary.test.ubication.to.response.stub.CreateUbicacioResponseStub;
import es.bcn.vigia.fmw.integration.gap.inventary.test.ubication.to.response.stub.UpdateUbicacioResponseStub;
import es.bcn.vigia.fmw.integration.gap.inventary.ubication.service.IntegrationGapUbicationService;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.UbicationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.UbicationBDTOStub;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.UbicationGap;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

public class UbicationServiceImplTest extends RestServerParentTest {
	
	@Mock
	private IntegrationGapUbicationService service;
	
	@Mock
	private MyBatisTemplate myBatisTemplate;
	
	@Mock
	private MyBatisTemplateGap myBatisTemplateGap;

	@Mock
	private ReturnRDTO returnRDTO;

	@InjectMocks
	private UbicationServiceImpl serviceUbication;

	private UbicationBDTO ubicationBDTO;
	
	private QueryParameterBDTO queryParameterBDTO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ubicationBDTO = UbicationBDTOStub.defaultOne();
		queryParameterBDTO = QueryParameterBDTOStub.defaultOne();
	}

	@Test
	public void caseOkInsert() throws Exception {
		Mockito.when(service.create(ubicationBDTO)).thenReturn(CreateUbicacioResponseStub.getMessageSuccess());
		serviceUbication.insert(ubicationBDTO);
	}

	@Test
	public void caseOkInsertCodeError() throws Exception {
		Mockito.when(service.create(ubicationBDTO)).thenReturn(CreateUbicacioResponseStub.empty());
		serviceUbication.insert(ubicationBDTO);
	}
	
	@Test
	public void caseOkUpdate() throws Exception {
		Mockito.when(service.update(ubicationBDTO)).thenReturn(UpdateUbicacioResponseStub.getMessageSuccess());
		serviceUbication.update(ubicationBDTO);
	}

	@Test
	public void caseOkUpdateCodeError() throws Exception {
		Mockito.when(service.update(ubicationBDTO)).thenReturn(UpdateUbicacioResponseStub.empty());
		serviceUbication.update(ubicationBDTO);
	}

	@Test
	public void caseOkDelete() throws Exception {
		Mockito.when(service.update(ubicationBDTO)).thenReturn(UpdateUbicacioResponseStub.getMessageSuccess());
		serviceUbication.delete(ubicationBDTO);
	}
	
	@Test (expected = ImiException.class)
	public void caseKoDelete() throws Exception {
		serviceUbication.delete(ubicationBDTO);
	}

	@Test
	public void caseOkDeleteCodeError() throws Exception {
		Mockito.when(service.update(ubicationBDTO)).thenReturn(UpdateUbicacioResponseStub.empty());
		serviceUbication.delete(ubicationBDTO);
	}

	@Test
	public void caseOkSelectMassive() throws Exception {
		serviceUbication.selectMassive(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectMassive() throws Exception {
		queryParameterBDTO.setPeriod(" ");
		serviceUbication.selectMassive(queryParameterBDTO);
	}

	@Test
	public void caseOkSelectDetailed() throws Exception {
		serviceUbication.selectDetailed(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectDetailed() throws Exception {
		queryParameterBDTO.setPeriod(" ");
		serviceUbication.selectDetailed(queryParameterBDTO);
	}

	@Test
	public void caseOkGetUbicationMassive() throws Exception {
		serviceUbication.getUbicationMassive(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetUbicationMassive() throws Exception {
		queryParameterBDTO.setPeriod(" ");
		serviceUbication.getUbicationMassive(queryParameterBDTO);
	}

	@Test
	public void caseOkGetUbicationDetailed() throws Exception {
		serviceUbication.getUbicationDetailed(queryParameterBDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetUbicationDetailed() throws Exception {
		queryParameterBDTO.setPeriod(" ");
		serviceUbication.getUbicationDetailed(queryParameterBDTO);
	}

	@Test
	public void caseOkGetLabelsRDTO() throws Exception {
		UbicationGap ubicationGap = new UbicationGap();
		serviceUbication.getLabelsRDTO(ubicationGap);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetLabelsRDTO() throws Exception {
		UbicationGap ubicationGap = null;
		serviceUbication.getLabelsRDTO(ubicationGap);
	}


}
