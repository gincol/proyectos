package es.bcn.imi.framework.vigia.frontal.test.web.rest.controller.masters.sync;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import es.bcn.imi.framework.vigia.frontal.business.masters.MastersService;
import es.bcn.imi.framework.vigia.frontal.web.rest.controller.masters.sync.MastersController;
import es.bcn.imi.framework.vigia.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterMastersRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBranchModelsVehicleRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCompatibilityMclUbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCompatibilitySensorMcfRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCompatibilitySensorRRMMRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventClassificationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnHolidayCalendarRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPricesTableRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnServiceContractRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnServiceHierarchyRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnServiceSubContractRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnWasteManagementPlantDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnWasteManagementPlantMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnWorkRegimeRelationshipRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnBranchModelsVehicleRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnCompatibilityMclUbicationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnCompatibilitySensorMcfRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnCompatibilitySensorRRMMRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnEventClassificationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnHolidayCalendarRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnPricesTableRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnServiceContractRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnServiceHierarchyRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnServiceSubContractRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnWasteManagementPlantDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnWasteManagementPlantMassiveRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnWorkRegimeRelationshipRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterMastersRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;


public class MastersControllerTest extends RestServerParentTest {

	private MockMvc mockMvc;

	@Mock
	private MastersService mastersService;
	
	@InjectMocks
	private MastersController controller;
	
	private QueryParameterMastersRDTO queryParameterMastersRDTO;
		
	private ReturnServiceHierarchyRDTO returnServiceHierarchyRDTO;
	
	private ReturnServiceContractRDTO returnServiceContractRDTO;
	
	private ReturnServiceSubContractRDTO returnServiceSubContractRDTO;
	
	private ReturnWasteManagementPlantMassiveRDTO returnWasteManagementPlantMassiveRDTO;
	
	private ReturnWasteManagementPlantDetailedRDTO returnWasteManagementPlantDetailedRDTO;
	
	private ReturnPricesTableRDTO returnPricesTableRDTO;
	
	private ReturnEventClassificationRDTO returnEventClassificationRDTO;
	
	private ReturnBranchModelsVehicleRDTO returnBranchModelsVehicleRDTO;
	
	private ReturnHolidayCalendarRDTO returnHolidayCalendarRDTO;
	
	private ReturnWorkRegimeRelationshipRDTO returnWorkRegimeRelationshipRDTO;
	
	private ReturnCompatibilityMclUbicationRDTO returnCompatibilityMclUbicationRDTO;
	
	private ReturnCompatibilitySensorRRMMRDTO returnCompatibilitySensorRRMMRDTO;
	
	private ReturnCompatibilitySensorMcfRDTO returnCompatibilitySensorMcfRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		queryParameterMastersRDTO = QueryParameterMastersRDTOStub.defaultOne();
		returnServiceHierarchyRDTO = ReturnServiceHierarchyRDTOStub.getSuccessMessage();
		returnServiceContractRDTO = ReturnServiceContractRDTOStub.getSuccessMessage();
		returnServiceSubContractRDTO = ReturnServiceSubContractRDTOStub.getSuccessMessage();
		returnWasteManagementPlantMassiveRDTO = ReturnWasteManagementPlantMassiveRDTOStub.getSuccessMessage();
		returnWasteManagementPlantDetailedRDTO = ReturnWasteManagementPlantDetailedRDTOStub.getSuccessMessage();
		returnPricesTableRDTO = ReturnPricesTableRDTOStub.getSuccessMessage();
		returnEventClassificationRDTO = ReturnEventClassificationRDTOStub.getSuccessMessage();
		returnBranchModelsVehicleRDTO = ReturnBranchModelsVehicleRDTOStub.getSuccessMessage();
		returnHolidayCalendarRDTO = ReturnHolidayCalendarRDTOStub.getSuccessMessage();
		returnWorkRegimeRelationshipRDTO = ReturnWorkRegimeRelationshipRDTOStub.getSuccessMessage();
		returnCompatibilityMclUbicationRDTO = ReturnCompatibilityMclUbicationRDTOStub.getSuccessMessage();
		returnCompatibilitySensorRRMMRDTO = ReturnCompatibilitySensorRRMMRDTOStub.getSuccessMessage();
		returnCompatibilitySensorMcfRDTO = ReturnCompatibilitySensorMcfRDTOStub.getSuccessMessage();
	}
	
	@Test
	public void caseOkSelectServiceHierarchy() throws Exception{		
		Mockito.when(mastersService.selectServiceHierarchy(queryParameterMastersRDTO)).thenReturn(returnServiceHierarchyRDTO);
    	mockMvc.perform(get("/sync/mestres/serveis/jerarquia?dataReferencia"+queryParameterMastersRDTO.getDateReference()))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectDomains() throws Exception{		
		Mockito.doThrow(ImiException.class).when(mastersService).selectServiceHierarchy(Mockito.any(queryParameterMastersRDTO.getClass()));    	
    	mockMvc.perform(get("/sync/mestres/serveis/jerarquia?dataReferencia"+queryParameterMastersRDTO.getDateReference()))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectServiceContract() throws Exception{		
		Mockito.when(mastersService.selectServiceContract(queryParameterMastersRDTO)).thenReturn(returnServiceContractRDTO);
    	mockMvc.perform(get("/sync/mestres/contracta/"+queryParameterMastersRDTO.getCodeContract()+"/serveis?dataReferencia"+queryParameterMastersRDTO.getDateReference()).header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectServiceContract() throws Exception{		
		Mockito.doThrow(ImiException.class).when(mastersService).selectServiceContract(Mockito.any(queryParameterMastersRDTO.getClass()));    	
    	mockMvc.perform(get("/sync/mestres/contracta/"+queryParameterMastersRDTO.getCodeContract()+"/serveis?dataReferencia"+queryParameterMastersRDTO.getDateReference()).header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectServiceSubContract() throws Exception{		
		Mockito.when(mastersService.selectServiceSubContract(queryParameterMastersRDTO)).thenReturn(returnServiceSubContractRDTO);
    	mockMvc.perform(get("/sync/mestres/contracta/"+queryParameterMastersRDTO.getCodeContract()+"/subcontracta/"+queryParameterMastersRDTO.getCodeSubContract()+"?dataReferencia"+queryParameterMastersRDTO.getDateReference()).header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectServiceSubContract() throws Exception{		
		Mockito.doThrow(ImiException.class).when(mastersService).selectServiceSubContract(Mockito.any(queryParameterMastersRDTO.getClass()));    	
    	mockMvc.perform(get("/sync/mestres/contracta/"+queryParameterMastersRDTO.getCodeContract()+"/subcontracta/"+queryParameterMastersRDTO.getCodeSubContract()+"?dataReferencia"+queryParameterMastersRDTO.getDateReference()).header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectWasteManagementPlantMassive() throws Exception{		
		Mockito.when(mastersService.selectWasteManagementPlantMassive(queryParameterMastersRDTO)).thenReturn(returnWasteManagementPlantMassiveRDTO);
    	mockMvc.perform(get("/sync/mestres/plantagestioresidus?dataReferencia"+queryParameterMastersRDTO.getDateReference()))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectWasteManagementPlantMassive() throws Exception{		
		Mockito.doThrow(ImiException.class).when(mastersService).selectWasteManagementPlantMassive(Mockito.any(queryParameterMastersRDTO.getClass()));    	
    	mockMvc.perform(get("/sync/mestres/plantagestioresidus?dataReferencia"+queryParameterMastersRDTO.getDateReference()))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectWasteManagementPlantDetailed() throws Exception{		
		Mockito.when(mastersService.selectWasteManagementPlantDetailed(queryParameterMastersRDTO)).thenReturn(returnWasteManagementPlantDetailedRDTO);
    	mockMvc.perform(get("/sync/mestres/plantagestioresidus/"+queryParameterMastersRDTO.getCode()+"?dataReferencia"+queryParameterMastersRDTO.getDateReference()))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectWasteManagementPlantDetailed() throws Exception{		
		Mockito.doThrow(ImiException.class).when(mastersService).selectWasteManagementPlantDetailed(Mockito.any(queryParameterMastersRDTO.getClass()));    	
    	mockMvc.perform(get("/sync/mestres/plantagestioresidus/"+queryParameterMastersRDTO.getCode()+"?dataReferencia"+queryParameterMastersRDTO.getDateReference()))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectPricesTable() throws Exception{		
		Mockito.when(mastersService.selectPricesTable(queryParameterMastersRDTO)).thenReturn(returnPricesTableRDTO);
    	mockMvc.perform(get("/sync/mestres/contracta/"+queryParameterMastersRDTO.getCodeContract()+"/taulapreus?dataReferencia"+queryParameterMastersRDTO.getDateReference()).header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectPricesTable() throws Exception{		
		Mockito.doThrow(ImiException.class).when(mastersService).selectPricesTable(Mockito.any(queryParameterMastersRDTO.getClass()));    	
    	mockMvc.perform(get("/sync/mestres/contracta/"+queryParameterMastersRDTO.getCodeContract()+"/taulapreus?dataReferencia"+queryParameterMastersRDTO.getDateReference()).header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectEventClassification() throws Exception{		
		Mockito.when(mastersService.selectEventClassification(queryParameterMastersRDTO)).thenReturn(returnEventClassificationRDTO);
    	mockMvc.perform(get("/sync/mestres/classificacioesdeveniments?dataReferencia"+queryParameterMastersRDTO.getDateReference()))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectEventClassification() throws Exception{		
		Mockito.doThrow(ImiException.class).when(mastersService).selectEventClassification(Mockito.any(queryParameterMastersRDTO.getClass()));    	
    	mockMvc.perform(get("/sync/mestres/classificacioesdeveniments?dataReferencia"+queryParameterMastersRDTO.getDateReference()))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectBranchModelsVehicle() throws Exception{		
		Mockito.when(mastersService.selectBranchModelsVehicle(queryParameterMastersRDTO)).thenReturn(returnBranchModelsVehicleRDTO);
    	mockMvc.perform(get("/sync/mestres/marquesmodelsvehicle?dataReferencia"+queryParameterMastersRDTO.getDateReference()))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectBranchModelsVehicle() throws Exception{		
		Mockito.doThrow(ImiException.class).when(mastersService).selectBranchModelsVehicle(Mockito.any(queryParameterMastersRDTO.getClass()));    	
    	mockMvc.perform(get("/sync/mestres/marquesmodelsvehicle?dataReferencia"+queryParameterMastersRDTO.getDateReference()))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectHolidayCalendar() throws Exception{		
		Mockito.when(mastersService.selectHolidayCalendar(queryParameterMastersRDTO)).thenReturn(returnHolidayCalendarRDTO);
    	mockMvc.perform(get("/sync/mestres/festius/"+queryParameterMastersRDTO.getYear()))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectHolidayCalendar() throws Exception{		
		Mockito.doThrow(ImiException.class).when(mastersService).selectHolidayCalendar(Mockito.any(queryParameterMastersRDTO.getClass()));    	
    	mockMvc.perform(get("/sync/mestres/festius/"+queryParameterMastersRDTO.getYear()))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectWorkRegimeRelationship() throws Exception{		
		Mockito.when(mastersService.selectWorkRegimeRelationship(queryParameterMastersRDTO)).thenReturn(returnWorkRegimeRelationshipRDTO);
    	mockMvc.perform(get("/sync/mestres/contracta/"+queryParameterMastersRDTO.getCodeContract()+"/relacioregimtreball?dataReferencia"+queryParameterMastersRDTO.getDateReference()).header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectWorkRegimeRelationship() throws Exception{		
		Mockito.doThrow(ImiException.class).when(mastersService).selectWorkRegimeRelationship(Mockito.any(queryParameterMastersRDTO.getClass()));    	
    	mockMvc.perform(get("/sync/mestres/contracta/"+queryParameterMastersRDTO.getCodeContract()+"/relacioregimtreball?dataReferencia"+queryParameterMastersRDTO.getDateReference()).header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectCompatibilityMclUbication() throws Exception{		
		Mockito.when(mastersService.selectCompatibilityMclUbication()).thenReturn(returnCompatibilityMclUbicationRDTO);
    	mockMvc.perform(get("/sync/mestres/compatibilitat/mobiliarilogic/ubicacio"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectCompatibilityMclUbication() throws Exception{		
		Mockito.doThrow(ImiException.class).when(mastersService).selectCompatibilityMclUbication();    	
    	mockMvc.perform(get("/sync/mestres/compatibilitat/mobiliarilogic/ubicacio"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectCompatibilitySensorRRMM() throws Exception{		
		Mockito.when(mastersService.selectCompatibilitySensorRRMM()).thenReturn(returnCompatibilitySensorRRMMRDTO);
    	mockMvc.perform(get("/sync/mestres/compatibilitat/sensor/recursmaterial"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectCompatibilitySensorRRMM() throws Exception{		
		Mockito.doThrow(ImiException.class).when(mastersService).selectCompatibilitySensorRRMM();    	
    	mockMvc.perform(get("/sync/mestres/compatibilitat/sensor/recursmaterial"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectCompatibilitySensorMcf() throws Exception{		
		Mockito.when(mastersService.selectCompatibilitySensorMcf()).thenReturn(returnCompatibilitySensorMcfRDTO);
    	mockMvc.perform(get("/sync/mestres/compatibilitat/sensor/mobiliarifisic"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectCompatibilitySensorMcf() throws Exception{		
		Mockito.doThrow(ImiException.class).when(mastersService).selectCompatibilitySensorMcf();    	
    	mockMvc.perform(get("/sync/mestres/compatibilitat/sensor/mobiliarifisic"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
}
	