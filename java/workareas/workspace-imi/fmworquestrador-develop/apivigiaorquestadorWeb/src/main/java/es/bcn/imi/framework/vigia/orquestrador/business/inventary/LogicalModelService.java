package es.bcn.imi.framework.vigia.orquestrador.business.inventary;

import javax.servlet.http.HttpServletRequest;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface LogicalModelService {
	
	public abstract ReturnRDTO redirect(LogicalModelRDTO logicalModelRDTO, HttpServletRequest request) throws ImiException;
	
	public abstract ReturnRDTO insert(LogicalModelRDTO logicalModelRDTO) throws ImiException;

	public abstract ReturnRDTO update(LogicalModelRDTO logicalModelRDTO) throws ImiException;

	public abstract ReturnRDTO delete(LogicalModelRDTO logicalModelRDTO) throws ImiException;
	
	public abstract ReturnLogicalModelRDTO select(LogicalModelRDTO logicalModelRDTO) throws ImiException;

	public abstract ReturnLogicalModelMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnLogicalModelDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException;

}
