package es.bcn.imi.framework.vigia.inventari.kafka;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.bcn.vigia.fmw.libcommons.business.dto.StatesBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.async.KafkaElement;
import es.bcn.vigia.fmw.libcommons.business.dto.async.KafkaElementResult;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterStatesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.StatesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.convert.StatesConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.impl.UtilsImpl;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.services.rest.impl.RestCallImpl;

@Service(ServicesConstants.KAFKA_CONSUMER)
public class KafkaConsumerInputOutput {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.KAFKA_PRODUCER_OUTPUT)
	private KafkaProducerOutput kafkaProducerOutput;

	@Value("${url.api.frontal}")
	private String urlApiFrontal;

	private RestCall restCall;

	private Utils utils;
	
	@Value("${url.api.nodeJS}")
	private String urlApiNodeJS;

	@Value("${url.path.states}")
	private String pathStates;

	@Value("${url.path.states.getTransactionId}")
	private String pathStatesGetTransactionId;

	private ReturnRDTO returnRDTO;
	
	public KafkaConsumerInputOutput() {
		restCall = new RestCallImpl();
		utils = new UtilsImpl();
	}

	private void processResponse(ResponseEntity<Object> resp) throws ImiException

	{
		returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

		logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

	}

	@KafkaListener(topics = "inventari-kafka", groupId = "group_kafkaElement", containerFactory = "kafkaListenerContainerFactory")
	public void consume(KafkaElement kafkaElement) {		
		logger.info("Entra elemento kafka por la cola de entrada: " + kafkaElement);
		returnRDTO = new ReturnRDTO();
		
		BaseTransactionIdRDTO rdto = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			switch (kafkaElement.getRdto()) {
				case ImiConstants.ASYNC_TYPE_UBICATION:
					rdto = mapper.readValue(kafkaElement.getJson(), UbicationRDTO.class);				
					break;
				case ImiConstants.ASYNC_TYPE_INSTALLATION:
					rdto = mapper.readValue(kafkaElement.getJson(), InstallationRDTO.class);				
					break;
				case ImiConstants.ASYNC_TYPE_MCL:
					rdto = mapper.readValue(kafkaElement.getJson(), LogicalModelRDTO.class);				
					break;
				case ImiConstants.ASYNC_TYPE_MCF:
					rdto = mapper.readValue(kafkaElement.getJson(), PhysicalModelRDTO.class);				
					break;
				case ImiConstants.ASYNC_TYPE_RRMM:
					rdto = mapper.readValue(kafkaElement.getJson(), MaterialResourceRDTO.class);				
					break;
				case ImiConstants.ASYNC_TYPE_COMMERCE:
					rdto = mapper.readValue(kafkaElement.getJson(), CommerceRDTO.class);				
					break;
				default:					
			}
		} catch (Exception e1) {
			logger.info("Error en recuperar RDTO del KAFKA: "+e1);
		}
		
		Integer code = HttpStatus.OK.value();		
		KafkaElementResult kafkaElementResult = new KafkaElementResult();

		Map<String, String> clientId = new HashMap<>();
		clientId.put("clientIdKey", "x-ibm-client-id");
		clientId.put("clientIdValue", kafkaElement.getClientId());
		

		Map<String, String> urlParams = new HashMap<>();

		String url = urlApiFrontal.concat(kafkaElement.getUri());
		String subTransactionId = "";

		ResponseEntity<Object> resp = null;
		try {
			if (kafkaElement.getMethod().equals(String.valueOf(RequestMethod.POST))) {
				resp = restCall.executePOSTClientId(url, rdto, clientId);
			} else if (kafkaElement.getMethod().equals(String.valueOf(RequestMethod.PUT))) {
				resp = restCall.executePUTClientId(url, urlParams, rdto, clientId);
			} else if (kafkaElement.getMethod().equals(String.valueOf(RequestMethod.DELETE))) {
				resp = restCall.executeDELETEClientId(url, urlParams, rdto, clientId);
			}

			processResponse(resp);
			subTransactionId = returnRDTO.getTransactionId();

		} catch (ImiException e) {
			logger.info("Error al llamar al frontal: "+e);
		} catch(HttpStatusCodeException e) {
			logger.info("Error al llamar al frontal: "+e);
			code = e.getStatusCode().value();
			try {
				returnRDTO = mapper.readValue(e.getResponseBodyAsString(), ReturnRDTO.class);
			} catch (Exception e1) {
				logger.info("Error en recuperar la resposta del frontal: "+e1);
			} finally {
				subTransactionId = returnRDTO.getTransactionId();
			}
		} catch (Exception e) {
			logger.info("Error al llamar al frontal: "+e);
			code = HttpStatus.BAD_REQUEST.value();
			if(rdto == null) {
				rdto = new UbicationRDTO();
			}
			rdto.generateTransactionId();
			subTransactionId = rdto.getTransactionId();
		}
			
		kafkaElementResult.setContractCode(kafkaElement.getContractCode());
		kafkaElementResult.setTransactionId(kafkaElement.getTransactionId());
		kafkaElementResult.setSubTransactionId(subTransactionId);
		kafkaElementResult.setUri("/service/vigia/fmw/public/frontal"+kafkaElement.getUri());
		kafkaElementResult.setJson(kafkaElement.getJson());
		kafkaElementResult.setOperation(kafkaElement.getMethod());
		kafkaElementResult.setOrigin("inventari");
		kafkaElementResult.setRdto(kafkaElement.getRdto());
		kafkaElementResult.setPrimaryMessage(returnRDTO.getCode());
		kafkaElementResult.setSecondaryMessage(returnRDTO.getMessage());

		kafkaElementResult.setCode(code);
		kafkaElementResult.setState(4);
		
		kafkaProducerOutput.sendMessage(kafkaElementResult);

	}

	@KafkaListener(topics = "inventari-out-kafka", groupId = "group_kafkaElementResult", containerFactory = "kafkaListenerContainerFactoryResult")
	public void consume(KafkaElementResult kafkaElementResult) {
		logger.info("Entra elemento kafka por la cola de salida: " + kafkaElementResult);

		BaseTransactionIdRDTO rdto = null;

		try {
			String url = urlApiNodeJS.concat(pathStates);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(ImiConstants.FORMAT_DATETIME_REST_GAP);			

			StatesRDTO statesRDTO = new StatesRDTO();
			statesRDTO.setContractCode(kafkaElementResult.getContractCode());
			statesRDTO.setDate(sdf.format(date));
			statesRDTO.setTransactionId(kafkaElementResult.getTransactionId());
			statesRDTO.setSubtransactionId(kafkaElementResult.getSubTransactionId());
			statesRDTO.setUri(kafkaElementResult.getUri());
			statesRDTO.setOperation(kafkaElementResult.getOperation());
			statesRDTO.setOrigin(kafkaElementResult.getOrigin());
			statesRDTO.setCode(kafkaElementResult.getCode());
			statesRDTO.setState(kafkaElementResult.getState());
			statesRDTO.setPrimaryMessage(kafkaElementResult.getPrimaryMessage());
			statesRDTO.setSecondaryMessage(kafkaElementResult.getSecondaryMessage());

			ResponseEntity<Object> resp = restCall.executePOST(url, statesRDTO);

			url = urlApiNodeJS.concat(pathStates).concat(pathStatesGetTransactionId);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<>();
			urlParams.put("idTransaccio", kafkaElementResult.getTransactionId());
			
			QueryParameterStatesRDTO queryParameterStatesRDTO = new QueryParameterStatesRDTO();
			queryParameterStatesRDTO.setTransactionId(kafkaElementResult.getTransactionId());

			resp = restCall.executeGET(url, urlParams, queryParameterStatesRDTO);

			List<StatesBDTO> statesBDTOs = (List<StatesBDTO>) utils.rest2States(resp);
			List<StatesRDTO> statesRDTOs = StatesConvert.bdto2rdto(statesBDTOs);
			
			statesRDTO = statesRDTOs.get(0);
			if(statesRDTOs.size()-1 >= statesRDTO.getIterationsNumber()) {
				boolean errors = false;
				for(StatesRDTO stateRDTO : statesRDTOs) {
					if(stateRDTO.getCode() != 200) {
						errors = true;
						break;
					}
				}
				statesRDTO.setPrimaryMessage("Tancat");
				if(errors) {
					statesRDTO.setSecondaryMessage("Tancat amb errors");					
				}else {
					statesRDTO.setSecondaryMessage("Tancat");						
				}
				statesRDTO.setState(2);
				resp = restCall.executePUT(url, urlParams, statesRDTO);
			}
			
			
		} catch (Exception e) {
			logger.info("Error en actualizar los estados: "+e);
		}

	}

}
