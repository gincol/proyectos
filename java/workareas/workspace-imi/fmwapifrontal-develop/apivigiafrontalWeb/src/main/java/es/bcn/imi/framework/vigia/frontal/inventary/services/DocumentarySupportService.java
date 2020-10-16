package es.bcn.imi.framework.vigia.frontal.inventary.services;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.business.dto.DocumentarySupportBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface DocumentarySupportService {

	public abstract List<DocumentarySupportBDTO> getDocumentarySupports(Map<String, Object> params) throws ImiException;

	
}
