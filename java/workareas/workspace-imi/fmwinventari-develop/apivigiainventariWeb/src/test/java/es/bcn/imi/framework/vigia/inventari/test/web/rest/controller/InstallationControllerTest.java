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

import es.bcn.imi.framework.vigia.inventari.business.InstallationService;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.imi.framework.vigia.inventari.web.rest.controller.InstallationController;
import es.bcn.vigia.fmw.libcommons.business.dto.InstallationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.InstallationBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationPeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnBreakdownAmortizationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnInstallationDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnInstallationExpensesRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnInstallationPeriodRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnMassiveRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.InstallationRDTOStub;

public class InstallationControllerTest extends RestServerParentTest{
	
	private MockMvc mockMvc;
	
	@Mock
	private InstallationService service;
	
	@InjectMocks
	private InstallationController controller;

	private ReturnRDTO returnRDTO;
	
	private ReturnInstallationDetailedRDTO returnInstallationDetailedRDTO;
	
	private ReturnMassiveRDTO returnMassiveRDTO;
	
	private ReturnBreakdownAmortizationRDTO returnBreakdownAmortizationRDTO;
	
	private ReturnInstallationExpensesRDTO returnInstallationExpensesRDTO;
	
	private ReturnInstallationPeriodRDTO returnInstallationPeriodRDTO;
	
	private InstallationBDTO installationBDTO;
	
	private InstallationRDTO installationRDTO;
	
	private QueryParameterBDTO queryParameterBDTO;
	
	@Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        returnRDTO = ReturnRDTOStub.getSuccessMessage();
        returnInstallationDetailedRDTO = ReturnInstallationDetailedRDTOStub.getSuccessMessage();
        returnMassiveRDTO = ReturnMassiveRDTOStub.getSuccessMessage();
        returnBreakdownAmortizationRDTO = ReturnBreakdownAmortizationRDTOStub.getSuccessMessage();
        returnInstallationExpensesRDTO = ReturnInstallationExpensesRDTOStub.getSuccessMessage();
        returnInstallationPeriodRDTO = ReturnInstallationPeriodRDTOStub.getSuccessMessage();
        queryParameterBDTO = QueryParameterBDTOStub.defaultOne();
        installationBDTO = InstallationBDTOStub.defaultOne();
        installationRDTO = InstallationRDTOStub.defaultOne();
    }

	@Test
    public void caseOkInsert() throws Exception{
    	Mockito.when(service.insert(installationBDTO)).thenReturn(returnRDTO);
    	 
    	mockMvc.perform(post("/installacio")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codiContracta\":\""+ installationRDTO.getCodeContract() +"\",\r\n" + 
        		"   \"codi\":\""+ installationRDTO.getCode() +"\",\r\n" + 
        		"   \"estat\":\"A\",\r\n" + 
        		"   \"descripcio\":\"WEBSERVICE UNIT INSERT\",\r\n" + 
        		"   \"coordenadaX\":\"1000\",\r\n" + 
        		"   \"coordenadaY\":\"1000\",\r\n" + 
        		"   \"superficie\":10,\r\n" + 
        		"   \"tipus\":\"PC\",\r\n" + 
        		"   \"codiTipusTitularitat\":\"M\",\r\n" + 
        		"   \"motiuCanvi\":\"Creaci�\",\r\n" + 
        		"   \"subjecteAmortitzacio\":true,\r\n" + 
        		"   \"localitzacio\":{\r\n" + 
        		"      \"codiBarri\":\"01\",\r\n" + 
        		"      \"codiPostal\":\"08040\",\r\n" + 
        		"      \"codiTerritori\":\"1\",\r\n" + 
        		"      \"codiTipusVia\":\"C\",\r\n" + 
        		"      \"lletraInici\":\"C\",\r\n" + 
        		"      \"lletraFi\":\"\",\r\n" + 
        		"      \"nomVia\":\"Via Favencia\",\r\n" + 
        		"      \"numeroInici\":\"02\",\r\n" + 
        		"      \"numeroFi\":\"01\"\r\n" + 
        		"   }\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseOkUpdate() throws Exception{
    	Mockito.when(service.update(installationBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/installacio/"+ installationRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codiContracta\":\""+ installationRDTO.getCodeContract() +"\",\r\n" + 
        		"   \"estat\":\"A\",\r\n" + 
        		"   \"descripcio\":\"WEBSERVICE UNIT UPDATE\",\r\n" + 
        		"   \"coordenadaX\":\"1000\",\r\n" + 
        		"   \"coordenadaY\":\"1000\",\r\n" + 
        		"   \"superficie\":10,\r\n" + 
        		"   \"tipus\":\"PC\",\r\n" + 
        		"   \"codiTipusTitularitat\":\"M\",\r\n" + 
        		"   \"motiuCanvi\":\"Creaci�\",\r\n" + 
        		"   \"subjecteAmortitzacio\":true,\r\n" + 
        		"   \"localitzacio\":{\r\n" + 
        		"      \"codiBarri\":\"01\",\r\n" + 
        		"      \"codiPostal\":\"08040\",\r\n" + 
        		"      \"codiTerritori\":\"1\",\r\n" + 
        		"      \"codiTipusVia\":\"C\",\r\n" + 
        		"      \"lletraInici\":\"C\",\r\n" + 
        		"      \"lletraFi\":\"\",\r\n" + 
        		"      \"nomVia\":\"Via Favencia\",\r\n" + 
        		"      \"numeroInici\":\"02\",\r\n" + 
        		"      \"numeroFi\":\"01\"\r\n" + 
        		"   }\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseOkDelete() throws Exception{
    	Mockito.when(service.delete(installationBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(delete("/installacio/"+ installationRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codiContracta\":\""+ installationRDTO.getCodeContract() +"\",\r\n" + 
        		"   \"estat\":\"A\",\r\n" + 
        		"   \"descripcio\":\"WEBSERVICE UNIT DELETE\",\r\n" + 
        		"   \"coordenadaX\":\"1000\",\r\n" + 
        		"   \"coordenadaY\":\"1000\",\r\n" + 
        		"   \"superficie\":10,\r\n" + 
        		"   \"tipus\":\"PC\",\r\n" + 
        		"   \"codiTipusTitularitat\":\"M\",\r\n" + 
        		"   \"motiuCanvi\":\"Creaci�\",\r\n" + 
        		"   \"subjecteAmortitzacio\":true,\r\n" + 
        		"   \"localitzacio\":{\r\n" + 
        		"      \"codiBarri\":\"01\",\r\n" + 
        		"      \"codiPostal\":\"08040\",\r\n" + 
        		"      \"codiTerritori\":\"1\",\r\n" + 
        		"      \"codiTipusVia\":\"C\",\r\n" + 
        		"      \"lletraInici\":\"C\",\r\n" + 
        		"      \"lletraFi\":\"\",\r\n" + 
        		"      \"nomVia\":\"Via Favencia\",\r\n" + 
        		"      \"numeroInici\":\"02\",\r\n" + 
        		"      \"numeroFi\":\"01\"\r\n" + 
        		"   }\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseOkInsertExpense() throws Exception{
    	Mockito.when(service.insertExpense(installationBDTO)).thenReturn(returnRDTO);
    	 
    	mockMvc.perform(post("/installacio/despesa")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codi\":\"I104\",\r\n" + 
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
    public void caseOkInsertAmortizationBase() throws Exception{
    	Mockito.when(service.insertAmortizationBase(installationBDTO)).thenReturn(returnRDTO);
    	 
    	mockMvc.perform(post("/installacio/amortitzacio")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codi\":\"I103\",\r\n" + 
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
    public void caseOkInsertApportionment() throws Exception{
    	Mockito.when(service.insertApportionment(installationBDTO)).thenReturn(returnRDTO);
    	 
    	mockMvc.perform(post("/installacio/prorrateig")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codi\":\"I103\",\r\n" + 
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
    public void caseOkSelectDetailed() throws Exception{
    	Mockito.when(service.selectDetailed(queryParameterBDTO)).thenReturn(returnInstallationDetailedRDTO);
    	
    	mockMvc.perform(get("/installacio/"+queryParameterBDTO.getCode()+"/contracta/"+queryParameterBDTO.getCodeContract()+"/detall?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void caseOkSelectMassive() throws Exception{
    	Mockito.when(service.selectMassive(queryParameterBDTO)).thenReturn(returnMassiveRDTO);
    	
    	mockMvc.perform(get("/installacio/contracta/"+queryParameterBDTO.getCodeContract()+"?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void caseOkSelectAmortization() throws Exception{
    	Mockito.when(service.selectAmortization(queryParameterBDTO)).thenReturn(returnBreakdownAmortizationRDTO);
    	
    	mockMvc.perform(get("/installacio/"+queryParameterBDTO.getCode()+"/contracta/"+queryParameterBDTO.getCodeContract()+"/amortitzacio?codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void caseOkSelectExpenses() throws Exception{
    	Mockito.when(service.selectExpenses(queryParameterBDTO)).thenReturn(returnInstallationExpensesRDTO);
    	
    	mockMvc.perform(get("/installacio/"+queryParameterBDTO.getCode()+"/contracta/"+queryParameterBDTO.getCodeContract()+"/despesa?codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void caseOkSelectPeriod() throws Exception{
    	Mockito.when(service.selectPeriod(queryParameterBDTO)).thenReturn(returnInstallationPeriodRDTO);
    	
    	mockMvc.perform(get("/installacio/"+queryParameterBDTO.getCode()+"/contracta/"+queryParameterBDTO.getCodeContract()+"/periode/"+queryParameterBDTO.getPeriod()+"?codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
}
