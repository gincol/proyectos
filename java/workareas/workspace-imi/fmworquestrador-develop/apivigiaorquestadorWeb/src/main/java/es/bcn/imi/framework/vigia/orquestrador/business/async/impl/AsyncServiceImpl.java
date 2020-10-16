package es.bcn.imi.framework.vigia.orquestrador.business.async.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.bcn.imi.framework.vigia.orquestrador.business.async.AsyncService;
import es.bcn.imi.framework.vigia.orquestrador.kafka.KafkaProducerInput;
import es.bcn.vigia.fmw.libcommons.business.dto.async.KafkaElement;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AsyncRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.StatesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@Lazy
@Service(ServicesConstants.SRV_ASYNC)
public class AsyncServiceImpl implements AsyncService {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.KAFKA_PRODUCER_INPUT)
	private KafkaProducerInput kafkaProducerInput;

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Value("${url.api.nodeJS}")
	private String urlApiNodeJS;

	@Value("${url.path.states}")
	private String pathStates;

	private ReturnRDTO returnRDTO;

	private String url;

	@Override
	public ReturnRDTO ubications(AsyncRDTO<UbicationRDTO> rdto, HttpServletRequest request) throws ImiException {
		return redirect(rdto, ImiConstants.ASYNC_TYPE_UBICATION, request);
	}

	@Override
	public ReturnRDTO installation(AsyncRDTO<InstallationRDTO> rdto, HttpServletRequest request) throws ImiException {
		return redirect(rdto, ImiConstants.ASYNC_TYPE_INSTALLATION, request);
	}

	@Override
	public ReturnRDTO mcl(AsyncRDTO<LogicalModelRDTO> rdto, HttpServletRequest request) throws ImiException {
		return redirect(rdto, ImiConstants.ASYNC_TYPE_MCL, request);
	}

	@Override
	public ReturnRDTO mcf(AsyncRDTO<PhysicalModelRDTO> rdto, HttpServletRequest request) throws ImiException {
		return redirect(rdto, ImiConstants.ASYNC_TYPE_MCF, request);
	}

	@Override
	public ReturnRDTO rrmm(AsyncRDTO<MaterialResourceRDTO> rdto, HttpServletRequest request) throws ImiException {
		return redirect(rdto, ImiConstants.ASYNC_TYPE_RRMM, request);
	}

	@Override
	public ReturnRDTO commerce(AsyncRDTO<CommerceRDTO> rdto, HttpServletRequest request) throws ImiException {
		return redirect(rdto, ImiConstants.ASYNC_TYPE_COMMERCE, request);
	}

	@Override
	public ReturnRDTO redirect(AsyncRDTO<?> rdto, String type, HttpServletRequest request) throws ImiException {
		String method = request.getMethod();

		returnRDTO = new ReturnRDTO();
		KafkaElement kafkaElement;
		StatesRDTO statesRDTO;

		ObjectMapper mapper = new ObjectMapper();

		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(ImiConstants.FORMAT_DATETIME_REST_GAP);

			statesRDTO = new StatesRDTO();
			statesRDTO.setContractCode(rdto.getList().get(0).getCodeContract());
			statesRDTO.setDate(sdf.format(date));
			statesRDTO.setTransactionId(rdto.getTransactionId());
			statesRDTO.setOrigin("inventari");
			statesRDTO.setUri("/service/vigia/fmw/public/frontal/async/inventari/" + type);
			statesRDTO.setOperation(method);
			statesRDTO.setCode(200);
			statesRDTO.setState(0);
			statesRDTO.setIterationsNumber(rdto.getList().size());
			statesRDTO.setPrimaryMessage("Planificat");
			statesRDTO.setSecondaryMessage("Planificat correctament");

			url = urlApiNodeJS.concat(pathStates);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, statesRDTO);

			for (BaseTransactionIdRDTO baseTransactionIdRDTO : rdto.getList()) {

				baseTransactionIdRDTO.generateTransactionId();
				kafkaElement = new KafkaElement();
				kafkaElement.setRdto(type);
				kafkaElement.setTimestamp(date);
				kafkaElement.setContractCode(baseTransactionIdRDTO.getCodeContract());
				kafkaElement.setTransactionId(rdto.getTransactionId());
				kafkaElement.setJson(mapper.writeValueAsString(baseTransactionIdRDTO));
				kafkaElement.setUri("/sync/inventari/" + type);
				kafkaElement.setMethod(method);
				kafkaElement.setClientId(rdto.getClientId());
				kafkaProducerInput.sendMessage(kafkaElement);

			}

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage());

		} catch (Exception e) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, e);
			returnRDTO.setCode(ReturnEnum.CODE_ERROR_REGISTERED.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_ERROR_REGISTERED.getMessage());
		}

		return returnRDTO;
	}

}
