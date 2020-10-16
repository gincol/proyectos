package es.bcn.imi.framework.vigia.inventari.business;

import es.bcn.vigia.fmw.libcommons.business.dto.DocumentarySupportBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterDocumentarySupportBDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.DocumentarySupportExpenseBDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface DocumentarySupportService {

	public abstract ReturnRDTO insert(DocumentarySupportBDTO documentarySupportBDTO) throws ImiException;

	public abstract ReturnRDTO update(DocumentarySupportBDTO documentarySupportBDTO) throws ImiException;

	public abstract ReturnDocumentarySupportRDTO selectDocumentarySupport(QueryParameterDocumentarySupportBDTO queryParameterDocumentarySupportBDTO) throws ImiException;

	public abstract ReturnRDTO insertExpense(DocumentarySupportExpenseBDTO documentarySupportExpenseBDTO) throws ImiException;
	
	public abstract ReturnRDTO updateExpense(DocumentarySupportExpenseBDTO documentarySupportExpenseBDTO) throws ImiException;

}
