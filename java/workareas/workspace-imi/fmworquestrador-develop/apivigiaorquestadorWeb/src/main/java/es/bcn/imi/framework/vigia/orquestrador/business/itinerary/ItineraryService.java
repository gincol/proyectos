package es.bcn.imi.framework.vigia.orquestrador.business.itinerary;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ItineraryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.NotificationItineraryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnItineraryDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnItineraryMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface ItineraryService {

	public abstract ReturnItineraryMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException;
	
	public abstract ReturnItineraryDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnRDTO insert(ItineraryRDTO itineraryRDTO) throws ImiException;

	public abstract ReturnRDTO notify(NotificationItineraryRDTO notificationItineraryRDTO)throws ImiException;

	
}
