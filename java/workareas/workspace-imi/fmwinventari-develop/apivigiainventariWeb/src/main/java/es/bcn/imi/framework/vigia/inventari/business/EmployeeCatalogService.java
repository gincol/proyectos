package es.bcn.imi.framework.vigia.inventari.business;

import es.bcn.vigia.fmw.libcommons.business.dto.EmployeeCatalogBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEmployeeCatalogDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface EmployeeCatalogService {

	public abstract ReturnRDTO insert(EmployeeCatalogBDTO employeeCatalogBDTO) throws ImiException;

	public abstract ReturnMassiveRDTO selectMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException;

	public abstract ReturnEmployeeCatalogDetailedRDTO selectDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException;
}
