package es.bcn.imi.framework.vigia.frontal.business.inventary;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AggregateAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface PhysicalModelService {

	public abstract ReturnRDTO insert(PhysicalModelRDTO physicalModelRDTO) throws ImiException;

	public abstract ReturnRDTO update(PhysicalModelRDTO physicalModelRDTO) throws ImiException;

	public abstract ReturnRDTO delete(PhysicalModelRDTO physicalModelRDTO) throws ImiException;

	public abstract ReturnPhysicalModelRDTO select(PhysicalModelRDTO physicalModelRDTO) throws ImiException;

	public abstract ReturnRDTO insert(AggregateAmortizationRDTO aggregateAmortizationRDTO) throws ImiException;
	
	public abstract ReturnPhysicalModelMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnPhysicalModelDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnPhysicalModelAmortizationRDTO selectAmortization(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnRDTO insertDocumentalSupport(DocumentarySupportRDTO documentarySupportRDTO)throws ImiException;
	
	public abstract ReturnRDTO deleteDocumentalSupport(DocumentarySupportRDTO documentarySupportRDTO) throws ImiException;

	public abstract ReturnDocumentarySupportRDTO selectDocumentarySupports(QueryParameterDocumentarySupportRDTO queryParameterDocumentarySupportRDTO) throws ImiException;

	public abstract ReturnRDTO insertActuation(ActuationRDTO actuationRDTO) throws ImiException;
	
	public abstract ReturnRDTO deleteActuation(ActuationRDTO actuationRDTO)throws ImiException;


}
	
