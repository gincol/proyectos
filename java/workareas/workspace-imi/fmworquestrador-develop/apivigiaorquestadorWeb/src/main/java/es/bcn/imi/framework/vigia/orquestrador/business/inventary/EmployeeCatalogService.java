package es.bcn.imi.framework.vigia.orquestrador.business.inventary;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EmployeeCatalogRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEmployeeCatalogDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface EmployeeCatalogService {

	public abstract ReturnRDTO insert(EmployeeCatalogRDTO employeeCatalogRDTO) throws ImiException;

	public abstract ReturnMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnEmployeeCatalogDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO)	throws ImiException;

}
