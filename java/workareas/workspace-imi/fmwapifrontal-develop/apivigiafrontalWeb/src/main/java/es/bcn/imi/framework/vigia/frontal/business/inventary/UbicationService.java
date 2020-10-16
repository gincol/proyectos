package es.bcn.imi.framework.vigia.frontal.business.inventary;


import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnUbicationDetailedRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface UbicationService {

	public abstract ReturnRDTO insert(UbicationRDTO ubicationRDTO) throws ImiException;

	public abstract ReturnRDTO update(UbicationRDTO ubicationRDTO) throws ImiException;

	public abstract ReturnRDTO delete(UbicationRDTO ubicationRDTO) throws ImiException;

	public abstract ReturnMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnUbicationDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnRDTO insertDocumentalSupport(DocumentarySupportRDTO documentarySupportRDTO) throws ImiException;

	public abstract ReturnRDTO deleteDocumentalSupport(DocumentarySupportRDTO documentarySupportRDTO) throws ImiException;

	public abstract ReturnDocumentarySupportRDTO selectDocumentarySupports(QueryParameterDocumentarySupportRDTO queryParameterDocumentarySupportRDTO) throws ImiException;

	public abstract ReturnRDTO insertActuation(ActuationRDTO actuationRDTO) throws ImiException;

	public abstract ReturnRDTO deleteActuation(ActuationRDTO actuationRDTO) throws ImiException;

//	public abstract ReturnRDTO redirectAsync(AsyncRDTO<UbicationRDTO> ubicationsRDTO, HttpServletRequest request) throws ImiException;

}
