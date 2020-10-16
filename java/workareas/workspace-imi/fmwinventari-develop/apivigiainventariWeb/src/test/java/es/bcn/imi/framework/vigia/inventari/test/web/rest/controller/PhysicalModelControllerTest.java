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

import es.bcn.imi.framework.vigia.inventari.business.PhysicalModelService;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.imi.framework.vigia.inventari.web.rest.controller.PhysicalModelController;
import es.bcn.vigia.fmw.libcommons.business.dto.AggregateAmortizationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.PhysicalModelBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.AggregateAmortizationBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.PhysicalModelBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AggregateAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnPhysicalModelAmortizationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnPhysicalModelDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnPhysicalModelMassiveRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnPhysicalModelRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.AggregateAmortizationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.PhysicalModelRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
@SuppressWarnings("unused")
public class PhysicalModelControllerTest extends RestServerParentTest{
	
	private MockMvc mockMvc;
	
	@Mock
	private PhysicalModelService service;
	
	@InjectMocks
	private PhysicalModelController controller;
	
	private ReturnRDTO returnRDTO;
	
	
	private ReturnPhysicalModelRDTO returnPhysicalModelRDTO;
	
	private ReturnPhysicalModelMassiveRDTO returnPhysicalModelMassiveRDTO;
	
	private ReturnPhysicalModelDetailedRDTO returnPhysicalModelDetailedRDTO;
	
	private ReturnPhysicalModelAmortizationRDTO returnPhysicalModelAmortizationRDTO;
	
	private PhysicalModelBDTO physicalModelBDTO;
	
	private PhysicalModelRDTO physicalModelRDTO;
	
	private AggregateAmortizationBDTO aggregateAmortizationBDTO;
	
	private AggregateAmortizationRDTO aggregateAmortizationRDTO;
	
	private QueryParameterBDTO queryParameterBDTO;
	
	@Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        returnRDTO = ReturnRDTOStub.getSuccessMessage();
        returnPhysicalModelRDTO = ReturnPhysicalModelRDTOStub.getSuccessMessage();
        returnPhysicalModelMassiveRDTO = ReturnPhysicalModelMassiveRDTOStub.getSuccessMessage();
        returnPhysicalModelDetailedRDTO = ReturnPhysicalModelDetailedRDTOStub.getSuccessMessage();
        returnPhysicalModelAmortizationRDTO = ReturnPhysicalModelAmortizationRDTOStub.getSuccessMessage();
        physicalModelBDTO = PhysicalModelBDTOStub.defaultOne();
        physicalModelRDTO = PhysicalModelRDTOStub.defaultOne();
        aggregateAmortizationBDTO = AggregateAmortizationBDTOStub.defaultOne();
        aggregateAmortizationRDTO = AggregateAmortizationRDTOStub.defaultOne();
        queryParameterBDTO = QueryParameterBDTOStub.defaultOne();
    }
    
	@Test
	public void caseKoSelectWrongInput() throws Exception {
		mockMvc.perform(get("/mobiliarifisicError/"+physicalModelRDTO.getCode())).andDo(print()).andExpect(status().isNotFound());
	}
	
	@Test
    public void caseOkInsert() throws Exception{
    	Mockito.when(service.insert(physicalModelBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(post("/mobiliarifisic")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"	\"codi\":\""+physicalModelRDTO.getCode()+"\",\r\n" + 
        		"	\"codiContracta\":\""+physicalModelRDTO.getCodeContract()+"\",\r\n" + 
        		"	\"codiMCL\":\"CL213\",\r\n" + 
        		"	\"codiUbicacio\":\"C40001\",\r\n" + 
        		"	\"codiRFID\":\"C124\",\r\n" + 
        		"	\"codiEstat\":\"SE\",\r\n" + 
        		"	\"codiMarca\":\"A\",\r\n" + 
        		"	\"codiTipus\":\"C01\",\r\n" + 
        		"	\"codiTipusFraccio\":\"FO\",\r\n" + 
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
    	Mockito.doThrow(ImiException.class).when(service).insert(Mockito.any(physicalModelBDTO.getClass()));
    	mockMvc.perform(post("/mobiliarifisic")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"	\"codi\":\""+physicalModelRDTO.getCode()+"\",\r\n" + 
        		"	\"codiContracta\":\""+physicalModelRDTO.getCodeContract()+"\",\r\n" + 
        		"	\"codiMCL\":\"CL213\",\r\n" + 
        		"	\"codiUbicacio\":\"C40001\",\r\n" + 
        		"	\"codiRFID\":\"C124\",\r\n" + 
        		"	\"codiEstat\":\"SE\",\r\n" + 
        		"	\"codiMarca\":\"A\",\r\n" + 
        		"	\"codiTipus\":\"C01\",\r\n" + 
        		"	\"codiTipusFraccio\":\"FO\",\r\n" + 
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
    	Mockito.when(service.update(physicalModelBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/mobiliarifisic/"+physicalModelRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"	\"sensors\":[{\r\n" + 
        		"				\"code\":\"SEN1\"\r\n" + 
        		"	}\r\n" + 
        		"	],\r\n" + 
        		"	\"codiContracta\":\""+physicalModelBDTO.getCodeContract()+"\"\r\n" +
        		"	},\r\n" + 
        		"	\"codiEstat\":\"SE\",\r\n" + 
        		"	\"codiInstallacio\":\"ZZA\",\r\n" + 
        		"	\"codiMarca\":\"A\",\r\n" + 
        		"	\"codiRFID\":\"124\",\r\n" + 
        		"	\"codiTipusFraccio\":\"FO\",\r\n" + 
        		"	\"codiTipus\":\"C01\",\r\n" + 
        		"	\"motiuCanvi\": \"update de nou mcf\",\r\n" + 
        		"	\"usuariActualitzacio\": \"tgutierrez\",\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseKoUpdate() throws Exception{
    	
    	Mockito.doThrow(ImiException.class).when(service).update(Mockito.any(physicalModelBDTO.getClass()));
        	
    	mockMvc.perform(put("/mobiliarifisic/"+physicalModelRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"	\"sensors\":[{\r\n" + 
        		"				\"code\":\"SEN1\"\r\n" + 
        		"	}\r\n" + 
        		"	],\r\n" + 
        		"	\"codiContracta\":\""+physicalModelBDTO.getCodeContract()+"\"\r\n" +
        		"	},\r\n" + 
        		"	\"codiEstat\":\"SE\",\r\n" + 
        		"	\"codiInstallacio\":\"ZZA\",\r\n" + 
        		"	\"codiMarca\":\"A\",\r\n" + 
        		"	\"codiRFID\":\"124\",\r\n" + 
        		"	\"codiTipusFraccio\":\"FO\",\r\n" + 
        		"	\"codiTipus\":\"C01\",\r\n" + 
        		"	\"motiuCanvi\": \"update de nou mcf\",\r\n" + 
        		"	\"usuariActualitzacio\": \"tgutierrez\",\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }
	
	@Test
    public void caseOkDelete() throws Exception{
    	Mockito.when(service.delete(physicalModelBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(delete("/mobiliarifisic/"+physicalModelRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"	\"motiuCanvi\": \"delete mcf\",\r\n" + 
        		"	\"usuariEliminacio\": \"tgutierrez\"\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseKoDelete() throws Exception{
    	Mockito.doThrow(ImiException.class).when(service).delete(Mockito.any(physicalModelBDTO.getClass()));
    	mockMvc.perform(delete("/mobiliarifisic/"+physicalModelRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"	\"motiuCanvi\": \"delete mcf\",\r\n" + 
        		"	\"usuariEliminacio\": \"tgutierrez\"\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }
	
	@Test
    public void caseOkInsertAggregateAmortization() throws Exception{
    	Mockito.when(service.insert(aggregateAmortizationBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(post("/mobiliarifisic/tipus/amortitzacio/agregada")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"	\"codiContracta\":\""+aggregateAmortizationRDTO.getCodeContract()+"\",\r\n" + 
        		"	\"codiFraccio\":\"FO\",\r\n" + 
        		"	\"codiMarcaMobiliari\":\"A\",\r\n" + 
        		"	\"codiTerritori\":\"2\",\r\n" + 
        		"	\"codiTipusMobiliari\":\"C02\",\r\n" + 
        		"	\"codiTipusServei\":\"NNBEME\",\r\n" + 
        		"	\"concepte\":\"WEBSERVICE JROJAST \",\r\n" + 
        		"	\"interessos\":\"10\",\r\n" + 
        		"	\"numElements\":\"10\",\r\n" + 
        		"	\"percentFinancament\":\"10\",\r\n" + 
        		"	\"periodeAmortitzacio\":\"12\",\r\n" + 
        		"	\"valor\":\"100\",\r\n" + 
        		"	\"valorAmortitzacio\":\"110\"\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseKoInsertAggregateAmortization() throws Exception{
    	Mockito.doThrow(ImiException.class).when(service).insert(Mockito.any(aggregateAmortizationBDTO.getClass()));
    	mockMvc.perform(post("/mobiliarifisic/tipus/amortitzacio/agregada")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"	\"codiContracta\":\""+aggregateAmortizationRDTO.getCodeContract()+"\",\r\n" + 
        		"	\"codiFraccio\":\"FO\",\r\n" + 
        		"	\"codiMarcaMobiliari\":\"A\",\r\n" + 
        		"	\"codiTerritori\":\"2\",\r\n" + 
        		"	\"codiTipusMobiliari\":\"C02\",\r\n" + 
        		"	\"codiTipusServei\":\"NNBEME\",\r\n" + 
        		"	\"concepte\":\"WEBSERVICE JROJAST \",\r\n" + 
        		"	\"interessos\":\"10\",\r\n" + 
        		"	\"numElements\":\"10\",\r\n" + 
        		"	\"percentFinancament\":\"10\",\r\n" + 
        		"	\"periodeAmortitzacio\":\"12\",\r\n" + 
        		"	\"valor\":\"100\",\r\n" + 
        		"	\"valorAmortitzacio\":\"110\"\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }
	
	@Test
    public void caseOkSelectMassive() throws Exception{
    	Mockito.when(service.selectMassive(queryParameterBDTO)).thenReturn(returnPhysicalModelMassiveRDTO);
    	
    	mockMvc.perform(get("/mobiliarifisic/contracta/"+queryParameterBDTO.getCodeContract()+"?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void caseKoSelectMassive() throws Exception{
    	Mockito.doThrow(ImiException.class).when(service).selectMassive(Mockito.any(queryParameterBDTO.getClass()));
        	
    	mockMvc.perform(get("/mobiliarifisic/contracta/"+queryParameterBDTO.getCodeContract()+"?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
    public void caseOkSelectDetailed() throws Exception{
    	Mockito.when(service.selectDetailed(queryParameterBDTO)).thenReturn(returnPhysicalModelDetailedRDTO);
    	
    	mockMvc.perform(get("/mobiliarifisic/"+queryParameterBDTO.getCode()+"/contracta/"+queryParameterBDTO.getCodeContract()+"/detall?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void caseKoSelectDetailed() throws Exception{
    	Mockito.doThrow(ImiException.class).when(service).selectDetailed(Mockito.any(queryParameterBDTO.getClass()));
    	mockMvc.perform(get("/mobiliarifisic/"+queryParameterBDTO.getCode()+"/contracta/"+queryParameterBDTO.getCodeContract()+"/detall?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
    public void caseOkSelectAmortization() throws Exception{
    	Mockito.when(service.selectAmortization(queryParameterBDTO)).thenReturn(returnPhysicalModelAmortizationRDTO);
    	
    	mockMvc.perform(get("/mobiliarifisic/contracta/"+queryParameterBDTO.getCodeContract()+
    			"/fraccio/"+queryParameterBDTO.getCodeFraction()+
    			"/territori/"+queryParameterBDTO.getCodeTerritory()+
    			"/tipus/"+queryParameterBDTO.getCodeType()+
    			"/grup/"+queryParameterBDTO.getCodeGroup()+
    			"?codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}

	@Test
    public void caseKoSelectAmortization() throws Exception{
    	Mockito.doThrow(ImiException.class).when(service).selectAmortization(Mockito.any(queryParameterBDTO.getClass()));
    	
    	mockMvc.perform(get("/mobiliarifisic/contracta/"+queryParameterBDTO.getCodeContract()+
    			"/fraccio/"+queryParameterBDTO.getCodeFraction()+
    			"/territori/"+queryParameterBDTO.getCodeTerritory()+
    			"/tipus/"+queryParameterBDTO.getCodeType()+
    			"/grup/"+queryParameterBDTO.getCodeGroup()+
    			"?codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isInternalServerError());
	}
}
