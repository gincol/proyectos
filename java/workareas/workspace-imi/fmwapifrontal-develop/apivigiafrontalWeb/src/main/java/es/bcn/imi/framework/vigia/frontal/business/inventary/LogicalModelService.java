package es.bcn.imi.framework.vigia.frontal.business.inventary;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface LogicalModelService {

	public abstract ReturnRDTO insert(LogicalModelRDTO logicalModelRDTO) throws ImiException;

	public abstract ReturnRDTO update(LogicalModelRDTO logicalModelRDTO) throws ImiException;

	public abstract ReturnRDTO delete(LogicalModelRDTO logicalModelRDTO) throws ImiException;

	public abstract ReturnLogicalModelRDTO select(LogicalModelRDTO logicalModelRDTO) throws ImiException;
	
	public abstract ReturnLogicalModelMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnLogicalModelDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnRDTO insertDocumentalSupport(DocumentarySupportRDTO documentarySupportRDTO) throws ImiException;

	public abstract ReturnRDTO deleteDocumentalSupport(DocumentarySupportRDTO documentarySupportRDTO)throws ImiException;

	public abstract ReturnDocumentarySupportRDTO selectDocumentarySupports(QueryParameterDocumentarySupportRDTO queryParameterDocumentarySupportRDTO) throws ImiException;

	public abstract ReturnRDTO insertActuation(ActuationRDTO actuationRDTO) throws ImiException;
	
	public abstract ReturnRDTO deleteActuation(ActuationRDTO actuationRDTO)throws ImiException;
	
}
