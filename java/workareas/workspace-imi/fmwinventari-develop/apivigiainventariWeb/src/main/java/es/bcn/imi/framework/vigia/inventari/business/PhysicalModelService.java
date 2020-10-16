package es.bcn.imi.framework.vigia.inventari.business;

import es.bcn.vigia.fmw.libcommons.business.dto.AggregateAmortizationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.PhysicalModelBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface PhysicalModelService {
	
	public abstract ReturnRDTO insert(PhysicalModelBDTO physicalModelBDTO) throws ImiException;

	public abstract ReturnRDTO update(PhysicalModelBDTO physicalModelBDTO) throws ImiException;

	public abstract ReturnRDTO delete(PhysicalModelBDTO physicalModelBDTO) throws ImiException;
	
	public abstract ReturnRDTO insert(AggregateAmortizationBDTO aggregateAmortizationBDTO) throws ImiException;
	
	public abstract ReturnPhysicalModelMassiveRDTO selectMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException;
	
	public abstract ReturnPhysicalModelDetailedRDTO selectDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException;

	public abstract ReturnPhysicalModelAmortizationRDTO selectAmortization(QueryParameterBDTO queryParameterBDTO) throws ImiException;

}
