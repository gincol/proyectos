package es.bcn.imi.framework.vigia.orquestrador.business.inventary;

import javax.servlet.http.HttpServletRequest;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AggregateAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface PhysicalModelService {
	
	public abstract ReturnRDTO redirect(PhysicalModelRDTO physicalModelRDTO, HttpServletRequest request) throws ImiException;
	
	public abstract ReturnRDTO insert(PhysicalModelRDTO physicalModelRDTO) throws ImiException;

	public abstract ReturnRDTO update(PhysicalModelRDTO physicalModelRDTO) throws ImiException;

	public abstract ReturnRDTO delete(PhysicalModelRDTO physicalModelRDTO) throws ImiException;
	
	public abstract ReturnPhysicalModelRDTO select(PhysicalModelRDTO physicalModelRDTO) throws ImiException;
	
	public abstract ReturnRDTO insert(AggregateAmortizationRDTO aggregateAmortizationRDTO) throws ImiException;

	public abstract ReturnPhysicalModelMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnPhysicalModelDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnPhysicalModelAmortizationRDTO selectAmortization(QueryParameterRDTO queryParameterRDTO) throws ImiException;

}
