package es.bcn.imi.framework.vigia.orquestrador.business.execution;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.APSEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AnnulmentEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EventServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InformativeEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UnloadingEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventApprovedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventPendingRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventRejectedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface EventService {

	public abstract ReturnRDTO insertService(EventServiceRDTO eventServiceRDTO) throws ImiException;

	public abstract ReturnRDTO insertInformative(InformativeEventRDTO informativeEventRDTO) throws ImiException;

	public abstract ReturnRDTO insertAps(APSEventRDTO apsEventRDTO) throws ImiException;

	public abstract ReturnRDTO insertUnloading(UnloadingEventRDTO unloadingEventRDTO) throws ImiException;

	public abstract ReturnRDTO insertAnnulment(AnnulmentEventRDTO annulmentEventRDTO) throws ImiException;

	public abstract ReturnEventDetailedRDTO selectDetailed(QueryParameterEventRDTO queryParameterEventRDTO) throws ImiException;

	public abstract ReturnEventApprovedRDTO selectApproved(QueryParameterEventRDTO queryParameterEventRDTO) throws ImiException;

	public abstract ReturnEventRejectedRDTO selectRejected(QueryParameterEventRDTO queryParameterEventRDTO) throws ImiException;

	public abstract ReturnEventPendingRDTO selectPending(QueryParameterEventRDTO queryParameterEventRDTO) throws ImiException;

}
