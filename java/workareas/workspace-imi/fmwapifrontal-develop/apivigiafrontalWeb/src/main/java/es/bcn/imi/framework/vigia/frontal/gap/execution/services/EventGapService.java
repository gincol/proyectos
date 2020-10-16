package es.bcn.imi.framework.vigia.frontal.gap.execution.services;

import java.util.List;

import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.EventGapBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface EventGapService {

	public abstract  List<EventGapBDTO> getEventsGap(String code) throws ImiException;

}
