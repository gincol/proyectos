package es.bcn.imi.framework.vigia.frontal.gap.inventary.services;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.UbicationGapBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface UbicationGapService {

	public abstract List<UbicationGapBDTO> getUbicationsGap(Map<String, Object> params) throws ImiException;

	public abstract List<UbicationGapBDTO> getUbicationsGapByCodeType(String codeUbication, String codeUbicationType) throws ImiException;

	

	

}
