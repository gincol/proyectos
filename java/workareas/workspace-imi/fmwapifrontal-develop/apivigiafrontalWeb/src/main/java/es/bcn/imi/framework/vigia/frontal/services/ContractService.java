package es.bcn.imi.framework.vigia.frontal.services;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.business.dto.ContractBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface ContractService {

	public abstract List<ContractBDTO> getContract(Map<String, String> params) throws ImiException;

	
}
