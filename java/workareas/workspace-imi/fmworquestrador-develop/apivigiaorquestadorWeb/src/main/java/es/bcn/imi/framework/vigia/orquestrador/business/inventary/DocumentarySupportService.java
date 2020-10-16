package es.bcn.imi.framework.vigia.orquestrador.business.inventary;

import javax.servlet.http.HttpServletRequest;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportExpenseRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface DocumentarySupportService {

	public abstract ReturnRDTO redirect(DocumentarySupportRDTO documentarySupportRDTO, HttpServletRequest request) throws ImiException;
	
	public abstract ReturnRDTO insert(DocumentarySupportRDTO documentarySupportRDTO) throws ImiException;
	
	public abstract ReturnRDTO delete(DocumentarySupportRDTO documentarySupportRDTO) throws ImiException;

	public abstract ReturnDocumentarySupportRDTO selectDocumentarySupport(QueryParameterDocumentarySupportRDTO queryParameterDocumentarySupportRDTO) throws ImiException;

	public abstract ReturnRDTO redirectExpense(DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO, HttpServletRequest request) throws ImiException;

	public abstract ReturnRDTO insertExpense(DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO) throws ImiException;
	
	public abstract ReturnRDTO deleteExpense(DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO) throws ImiException;

	
	
}
