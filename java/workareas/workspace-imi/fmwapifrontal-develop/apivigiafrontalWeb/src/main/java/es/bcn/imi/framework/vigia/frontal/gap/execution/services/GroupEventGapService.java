package es.bcn.imi.framework.vigia.frontal.gap.execution.services;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.GroupEventGapBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface GroupEventGapService {

	public abstract  List<GroupEventGapBDTO> getGroupEventsGap(Map<String, Object> params) throws ImiException;

}
