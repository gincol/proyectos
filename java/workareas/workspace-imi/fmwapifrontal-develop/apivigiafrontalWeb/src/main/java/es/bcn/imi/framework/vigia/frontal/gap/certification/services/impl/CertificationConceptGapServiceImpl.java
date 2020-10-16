package es.bcn.imi.framework.vigia.frontal.gap.certification.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.gap.certification.services.CertificationConceptGapService;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.ValueListGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@Lazy(true)
@Service(ServicesConstants.SRV_CONCEPT_GAP)
public class CertificationConceptGapServiceImpl implements CertificationConceptGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Value("${url.api.certification}")
	private String urlCertification;
	
	@Value("${url.path.certification.gap}")
	private String pathCertification;
	
	@Value("${url.path.certification.getMasters}")
	private String pathCertificationGetMasters;

	@Value("${load.properties.client.id.key.ibm}")
	private String clientIdIbmKey;

	@Value("${load.properties.client.id.value.ibm}")
	private String clientIdIbmValue;

	

	
	@Override
	public List<ValueListGapBDTO> getCodesSubtypeConceptInstallationGap() throws ImiException {
		
		return getMasterGap(ImiConstants.GET_MASTERS_GAP_VALUE_INSTALLATION_SUBTYPE_CONCEPTS);

	}


	@Override
	public List<ValueListGapBDTO> getCodesTypeExtraordinaryConceptGap() throws ImiException {
		
		return getMasterGap(ImiConstants.GET_MASTERS_GAP_VALUE_EXTRAORDINARY_CONCEPTS);

	}
	
	@Override
	public List<ValueListGapBDTO> getCodesTypeIvaGap() throws ImiException {
		
		return getMasterGap(ImiConstants.GET_MASTERS_GAP_VALUE_TYPE_IVA);

	}
	
	@Override
	public List<ValueListGapBDTO> getCodesConceptCertificationGap() throws ImiException {
		
		return getMasterGap(ImiConstants.GET_MASTERS_GAP_VALUE_CONCEPTS_CERTIFICATION);

	}


	private List<ValueListGapBDTO> getMasterGap(String masterName) throws ImiException {
		
		List<ValueListGapBDTO> concepts = new ArrayList<>(); 
		String url = urlCertification + pathCertification + pathCertificationGetMasters;

		logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
		
		
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(ImiConstants.GET_MASTERS_GAP_PARAM_NAME, masterName);
		
		Map<String, String> clientId = new HashMap<>();
		
		clientId.put("clientIdKey", clientIdIbmKey);
		clientId.put("clientIdValue", clientIdIbmValue);

		
		ResponseEntity<Object> resp = restCall.executeGETClientId(url, urlParams, concepts ,clientId);
		concepts = utils.rest2ListValueList(resp);
		
		return concepts;

	}
	
}