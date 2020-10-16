package es.bcn.imi.framework.vigia.orquestrador.business.execution;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterTimeRegisterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.TimeClockInstantRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.TimeClockSummaryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnTimeRegisterInstantRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnTimeRegisterSummaryRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface TimeClockService {

	public abstract ReturnRDTO insertInstant(TimeClockInstantRDTO timeClockInstantRDTO) throws ImiException;

	public abstract ReturnRDTO insertSummary(TimeClockSummaryRDTO timeClockSummaryRDTO) throws ImiException;

	public abstract ReturnTimeRegisterInstantRDTO selectTimeRegisterInstant(QueryParameterTimeRegisterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnTimeRegisterSummaryRDTO selectTimeRegisterSummary(QueryParameterTimeRegisterRDTO queryParameterRDTO) throws ImiException;
	
}
