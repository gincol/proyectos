package es.bcn.imi.framework.vigia.inventari.business;

import es.bcn.vigia.fmw.libcommons.business.dto.LogicalModelBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface LogicalModelService {

	public abstract ReturnRDTO insert(LogicalModelBDTO logicalModelBDTO) throws ImiException;

	public abstract ReturnRDTO update(LogicalModelBDTO logicalModelBDTO) throws ImiException;

	public abstract ReturnRDTO delete(LogicalModelBDTO logicalModelBDTO) throws ImiException;
	
	public abstract ReturnLogicalModelRDTO select(LogicalModelBDTO logicalModelBDTO) throws ImiException;
	
	public abstract ReturnLogicalModelMassiveRDTO selectMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException;
	
	public abstract ReturnLogicalModelDetailedRDTO selectDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException;
	
}
