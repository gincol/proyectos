package es.bcn.imi.framework.vigia.frontal.gap.execution.services;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.ServiceGapBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface ServiceGapService {

	public abstract  List<ServiceGapBDTO> getServicesGap(Map<String,Object> params) throws ImiException;

}
