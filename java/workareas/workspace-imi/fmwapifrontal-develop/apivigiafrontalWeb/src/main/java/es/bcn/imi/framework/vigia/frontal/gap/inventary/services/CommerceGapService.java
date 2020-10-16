package es.bcn.imi.framework.vigia.frontal.gap.inventary.services;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.CommerceGapBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface CommerceGapService {

	public abstract  List<CommerceGapBDTO> getCommercesGap(Map<String, Object> params) throws ImiException;

}
