package es.bcn.imi.framework.vigia.frontal.execution.services;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.business.dto.TimeClockSummaryBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface TimeClockSummaryService {

	public abstract List<TimeClockSummaryBDTO> getTimeClockSummaries(Map <String,Object> params) throws ImiException;

	

}
