package es.bcn.imi.framework.vigia.frontal.business.itinerary;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PlanningRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterPlanningRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPlanningDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPlanningMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface PlanningService {
	
	public abstract ReturnRDTO insert(PlanningRDTO planningRDTO) throws ImiException;
	
	public abstract ReturnPlanningMassiveRDTO selectMassive(QueryParameterPlanningRDTO queryParameterPlanningRDTO) throws ImiException;

	public abstract ReturnPlanningDetailedRDTO selectDetailed(QueryParameterPlanningRDTO queryParameterPlanningRDTO) throws ImiException;

}
