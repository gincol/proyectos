package es.bcn.imi.framework.vigia.frontal.inventary.services;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.business.dto.ActuationBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface ActuationService {

	public abstract List<ActuationBDTO> getActuations(Map<String, Object> params) throws ImiException;

	
}
