package es.bcn.imi.framework.vigia.inventari.test.web.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import es.bcn.imi.framework.vigia.inventari.business.MaterialResourceService;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.imi.framework.vigia.inventari.web.rest.controller.MaterialResourceController;
import es.bcn.vigia.fmw.libcommons.business.dto.MaterialResourceBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.MaterialResourceBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnMassiveRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnMaterialResourceDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnMaterialResourceVehicleDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.MaterialResourceRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public class MaterialResourceControllerTest extends RestServerParentTest{
	
	private MockMvc mockMvc;
	
	@Mock
	private MaterialResourceService service;
	
	@InjectMocks
	private MaterialResourceController controller;
	
	private ReturnRDTO returnRDTO;
		
	private ReturnMassiveRDTO returnMassiveRDTO;
	
	private ReturnMaterialResourceDetailedRDTO returnMaterialResourceDetailedRDTO;
	
	private ReturnMaterialResourceVehicleDetailedRDTO returnMaterialResourceVehicleDetailedRDTO;
	
	private MaterialResourceBDTO materialResourceBDTO;
	
	private MaterialResourceRDTO materialResourceRDTO;
	
	private QueryParameterBDTO queryParameterBDTO;
	
	
	@Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        returnRDTO = ReturnRDTOStub.getSuccessMessage();
        returnMassiveRDTO = ReturnMassiveRDTOStub.getSuccessMessage();
        returnMaterialResourceDetailedRDTO = ReturnMaterialResourceDetailedRDTOStub.getSuccessMessage();
        returnMaterialResourceVehicleDetailedRDTO = ReturnMaterialResourceVehicleDetailedRDTOStub.getSuccessMessage();
        queryParameterBDTO = QueryParameterBDTOStub.defaultOne();
        materialResourceBDTO = MaterialResourceBDTOStub.defaultOne();
        materialResourceRDTO = MaterialResourceRDTOStub.defaultOne();
      
    }
	
	@Test
    public void caseOkInsert() throws Exception{
    	Mockito.when(service.insert(materialResourceBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(post("/recursmaterial")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"	\"codi\":\""+materialResourceRDTO.getCode()+"\",\r\n" + 
        		"	\"codiContracta\":\""+materialResourceRDTO.getCodeContract()+"\",\r\n" + 
        		"	\"codiEstat\":\"A\",\r\n" + 
        		"	\"codiInstallacio\":\"CAAA\",\r\n" + 
        		"	\"matricula\":\"WEBSERVICE\",\r\n" + 
        		"	\"dataProperaItv\":\"10-12-2020 12:00:00\",\r\n" + 
        		"	\"calca\":\"227\",\r\n" + 
        		"	\"codiTipus\":\"C01\",\r\n" + 
        		"	\"codiTipusTitularitat\":\"M\",\r\n" + 
        		"	\"motiuCanvi\": \"creacio de nou mcf\",\r\n" + 
        		"	\"sensors\":[\r\n" + 
        		"		{\"codi\":\"SENSOR1\"}\r\n" + 
        		"	],\r\n" + 
        		"	\"usuariInsercio\": \"jrojast\"\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseKoInsert() throws Exception{
    	Mockito.doThrow(ImiException.class).when(service).insert(Mockito.any(materialResourceBDTO.getClass()));	
    	mockMvc.perform(post("/recursmaterial")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"	\"codi\":\""+materialResourceRDTO.getCode()+"\",\r\n" + 
        		"	\"codiContracta\":\""+materialResourceRDTO.getCodeContract()+"\",\r\n" + 
        		"	\"codiEstat\":\"A\",\r\n" + 
        		"	\"codiInstallacio\":\"CAAA\",\r\n" + 
        		"	\"matricula\":\"WEBSERVICE\",\r\n" + 
        		"	\"dataProperaItv\":\"10-12-2020 12:00:00\",\r\n" + 
        		"	\"calca\":\"227\",\r\n" + 
        		"	\"codiTipus\":\"C01\",\r\n" + 
        		"	\"codiTipusTitularitat\":\"M\",\r\n" + 
        		"	\"motiuCanvi\": \"creacio de nou mcf\",\r\n" + 
        		"	\"sensors\":[\r\n" + 
        		"		{\"codi\":\"SENSOR1\"}\r\n" + 
        		"	],\r\n" + 
        		"	\"usuariInsercio\": \"jrojast\"\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }
	
	@Test
    public void caseOkUpdate() throws Exception{
    	Mockito.when(service.update(materialResourceBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/recursmaterial/"+materialResourceRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.content("{\r\n" + 
        		"	\"codi\":\""+materialResourceRDTO.getCode()+"\",\r\n" + 
        		"	\"codiContracta\":\""+materialResourceRDTO.getCodeContract()+"\",\r\n" + 
        		"	\"codiEstat\":\"A\",\r\n" + 
        		"	\"codiInstallacio\":\"CAAA\",\r\n" + 
        		"	\"matricula\":\"WEBSERVICE\",\r\n" + 
        		"	\"dataProperaItv\":\"10-12-2020 12:00:00\",\r\n" + 
        		"	\"calca\":\"227\",\r\n" + 
        		"	\"codiTipus\":\"C01\",\r\n" + 
        		"	\"codiTipusTitularitat\":\"M\",\r\n" + 
        		"	\"motiuCanvi\": \"creacio de nou mcf\",\r\n" + 
        		"	\"sensors\":[\r\n" + 
        		"		{\"codi\":\"SENSOR1\"}\r\n" + 
        		"	],\r\n" + 
        		"	\"usuariInsercio\": \"jrojast\"\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseKoUpdate() throws Exception{
    	Mockito.doThrow(ImiException.class).when(service).update(Mockito.any(materialResourceBDTO.getClass()));
    	mockMvc.perform(put("/recursmaterial/"+materialResourceRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.content("{\r\n" + 
        		"	\"codi\":\""+materialResourceRDTO.getCode()+"\",\r\n" + 
        		"	\"codiContracta\":\""+materialResourceRDTO.getCodeContract()+"\",\r\n" + 
        		"	\"codiEstat\":\"A\",\r\n" + 
        		"	\"codiInstallacio\":\"CAAA\",\r\n" + 
        		"	\"matricula\":\"WEBSERVICE\",\r\n" + 
        		"	\"dataProperaItv\":\"10-12-2020 12:00:00\",\r\n" + 
        		"	\"calca\":\"227\",\r\n" + 
        		"	\"codiTipus\":\"C01\",\r\n" + 
        		"	\"codiTipusTitularitat\":\"M\",\r\n" + 
        		"	\"motiuCanvi\": \"creacio de nou mcf\",\r\n" + 
        		"	\"sensors\":[\r\n" + 
        		"		{\"codi\":\"SENSOR1\"}\r\n" + 
        		"	],\r\n" + 
        		"	\"usuariInsercio\": \"jrojast\"\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }
	
	@Test
    public void caseOkDelete() throws Exception{
    	Mockito.when(service.delete(materialResourceBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(delete("/recursmaterial/"+materialResourceRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"	\"motiuCanvi\": \"delete mcf\",\r\n" + 
        		"	\"usuariEliminacio\": \"tgutierrez\"\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseKoDelete() throws Exception{
    	Mockito.doThrow(ImiException.class).when(service).delete(Mockito.any(materialResourceBDTO.getClass()));
    	mockMvc.perform(delete("/recursmaterial/"+materialResourceRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"	\"motiuCanvi\": \"delete mcf\",\r\n" + 
        		"	\"usuariEliminacio\": \"tgutierrez\"\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }
	@Test
    public void caseOkSelectMassive() throws Exception{
    	Mockito.when(service.selectMassive(queryParameterBDTO)).thenReturn(returnMassiveRDTO);
    	
    	mockMvc.perform(get("/recursmaterial/contracta/"+queryParameterBDTO.getCodeContract()+"?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void caseKoSelectMassive() throws Exception{
    	Mockito.doThrow(ImiException.class).when(service).selectMassive(Mockito.any(queryParameterBDTO.getClass()));
    	
    	mockMvc.perform(get("/recursmaterial/contracta/"+queryParameterBDTO.getCodeContract()+"?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
    public void caseOkSelectDetailed() throws Exception{
    	Mockito.when(service.selectDetailed(queryParameterBDTO)).thenReturn(returnMaterialResourceDetailedRDTO);
    	
    	mockMvc.perform(get("/recursmaterial/"+queryParameterBDTO.getCode()+"/contracta/"+queryParameterBDTO.getCodeContract()+"/detall?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void caseKoSelectDetailed() throws Exception{
    	Mockito.doThrow(ImiException.class).when(service).selectDetailed(Mockito.any(queryParameterBDTO.getClass()));
    	mockMvc.perform(get("/recursmaterial/"+queryParameterBDTO.getCode()+"/contracta/"+queryParameterBDTO.getCodeContract()+"/detall?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
    public void caseOkSelectVehicleMassive() throws Exception{
    	Mockito.when(service.selectVehicleMassive(queryParameterBDTO)).thenReturn(returnMassiveRDTO);
    	
    	mockMvc.perform(get("/recursmaterial/vehicle/contracta/"+queryParameterBDTO.getCodeContract()+"?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
    public void caseKoSelectVehicleMassive() throws Exception{
    	
    	Mockito.doThrow(ImiException.class).when(service).selectVehicleMassive(Mockito.any(queryParameterBDTO.getClass()));
    	mockMvc.perform(get("/recursmaterial/vehicle/contracta/"+queryParameterBDTO.getCodeContract()+"?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
    public void caseOkSelectVehicleDetailed() throws Exception{
    	Mockito.when(service.selectVehicleDetailed(queryParameterBDTO)).thenReturn(returnMaterialResourceVehicleDetailedRDTO);
    	
    	mockMvc.perform(get("/recursmaterial/vehicle/"+queryParameterBDTO.getCode()+"/contracta/"+queryParameterBDTO.getCodeContract()+"/detall?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void caseKoSelectVehicleDetailed() throws Exception{
    	Mockito.doThrow(ImiException.class).when(service).selectVehicleDetailed(Mockito.any(queryParameterBDTO.getClass()));
    	mockMvc.perform(get("/recursmaterial/vehicle/"+queryParameterBDTO.getCode()+"/contracta/"+queryParameterBDTO.getCodeContract()+"/detall?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
    public void caseOkInsertExpense() throws Exception{
    	Mockito.when(service.insertExpense(materialResourceBDTO)).thenReturn(returnRDTO);
    	 
    	mockMvc.perform(post("/recursmaterial/despesa")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codi\":\"C104\",\r\n" + 
        		"   \"despese\":{\r\n" + 
        		"   		\"codiSubtipus\":\"LLO\",\r\n" + 
        		"   		\"codiZona\":\"C\",\r\n" + 
        		"	   	\"concepte\":\"FMW VIGIA\",\r\n" + 
        		"   		\"importAnual\":120,\r\n" + 
        		"   		\"mesosPagats\":6,\r\n" + 
        		"   		\"periode\":12,\r\n" + 
        		"   		\"preuUnitari\":5,\r\n" + 
        		"   		\"unitats\":15\r\n" + 
        		"   }\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseKoInsertExpense() throws Exception{
    	Mockito.doThrow(ImiException.class).when(service).insertExpense(Mockito.any(materialResourceBDTO.getClass()));
    	mockMvc.perform(post("/recursmaterial/despesa")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codi\":\"C104\",\r\n" + 
        		"   \"despese\":{\r\n" + 
        		"   		\"codiSubtipus\":\"LLO\",\r\n" + 
        		"   		\"codiZona\":\"C\",\r\n" + 
        		"	   	\"concepte\":\"FMW VIGIA\",\r\n" + 
        		"   		\"importAnual\":120,\r\n" + 
        		"   		\"mesosPagats\":6,\r\n" + 
        		"   		\"periode\":12,\r\n" + 
        		"   		\"preuUnitari\":5,\r\n" + 
        		"   		\"unitats\":15\r\n" + 
        		"   }\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }
	@Test
    public void caseOkInsertAmortizationBase() throws Exception{
    	Mockito.when(service.insertAmortizationBase(materialResourceBDTO)).thenReturn(returnRDTO);
    	 
    	mockMvc.perform(post("/recursmaterial/amortitzacio")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codi\":\"C103\",\r\n" + 
        		"   \"amortitzacio\":{\r\n" + 
        		"   		\"concepte\":\"TEST FMW-VIGIA JROJAS\",\r\n" + 
        		"   		\"import\":99,\r\n" + 
        		"   		\"importRestant\":11,\r\n" + 
        		"   		\"interessos\":10,\r\n" + 
        		"   		\"percentatgeFinancament\":10,\r\n" + 
        		"   		\"valor\":120,\r\n" + 
        		"   		\"mesosPagats\":1,\r\n" + 
        		"   		\"periode\":10\r\n" + 
        		"   }\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseKoInsertAmortizationBase() throws Exception{
    	Mockito.doThrow(ImiException.class).when(service).insertAmortizationBase(Mockito.any(materialResourceBDTO.getClass()));
        	 
    	mockMvc.perform(post("/recursmaterial/amortitzacio")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codi\":\"C103\",\r\n" + 
        		"   \"amortitzacio\":{\r\n" + 
        		"   		\"concepte\":\"TEST FMW-VIGIA JROJAS\",\r\n" + 
        		"   		\"import\":99,\r\n" + 
        		"   		\"importRestant\":11,\r\n" + 
        		"   		\"interessos\":10,\r\n" + 
        		"   		\"percentatgeFinancament\":10,\r\n" + 
        		"   		\"valor\":120,\r\n" + 
        		"   		\"mesosPagats\":1,\r\n" + 
        		"   		\"periode\":10\r\n" + 
        		"   }\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }
	
	@Test
    public void caseOkInsertApportionment() throws Exception{
    	Mockito.when(service.insertApportionment(materialResourceBDTO)).thenReturn(returnRDTO);
    	 
    	mockMvc.perform(post("/recursmaterial/prorrateig")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codi\":\"C103\",\r\n" + 
        		"   \"prorrateig\":[\r\n" + 
        		"   		{	\"codiServei\":\"N\",\r\n" + 
        		"   			\"percentatgeUS\":50\r\n" + 
        		"   		},\r\n" + 
        		"   		{	\"codiServei\":\"R\",\r\n" + 
        		"   			\"percentatgeUS\":50\r\n" + 
        		"   		}\r\n" + 
        		"   		]\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }

	@Test
    public void caseKoInsertApportionment() throws Exception{
    	Mockito.doThrow(ImiException.class).when(service).insertApportionment(Mockito.any(materialResourceBDTO.getClass()));
    	mockMvc.perform(post("/recursmaterial/prorrateig")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codi\":\"C103\",\r\n" + 
        		"   \"prorrateig\":[\r\n" + 
        		"   		{	\"codiServei\":\"N\",\r\n" + 
        		"   			\"percentatgeUS\":50\r\n" + 
        		"   		},\r\n" + 
        		"   		{	\"codiServei\":\"R\",\r\n" + 
        		"   			\"percentatgeUS\":50\r\n" + 
        		"   		}\r\n" + 
        		"   		]\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }
	
	
	
}
