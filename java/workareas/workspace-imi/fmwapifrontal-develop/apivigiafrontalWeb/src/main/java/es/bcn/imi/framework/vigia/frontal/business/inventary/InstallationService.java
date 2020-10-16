package es.bcn.imi.framework.vigia.frontal.business.inventary;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportExpenseRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationPeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface InstallationService {

	public abstract ReturnRDTO insert(InstallationRDTO installationRDTO) throws ImiException;

	public abstract ReturnRDTO update(InstallationRDTO installationRDTO) throws ImiException;

	public abstract ReturnRDTO delete(InstallationRDTO installationRDTO) throws ImiException;

	public abstract ReturnInstallationDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnRDTO insertExpense(InstallationRDTO installationRDTO) throws ImiException;

	public abstract ReturnRDTO insertAmortizationBase(InstallationRDTO installationRDTO) throws ImiException;

	public abstract ReturnRDTO insertApportionment(InstallationRDTO installationRDTO) throws ImiException;

	public abstract ReturnBreakdownAmortizationRDTO selectAmortization(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnInstallationExpensesRDTO selectExpenses(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnInstallationPeriodRDTO selectPeriod(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnRDTO insertActuation(ActuationRDTO actuationRDTO) throws ImiException;

	public abstract ReturnRDTO deleteActuation(ActuationRDTO actuationRDTO) throws ImiException;

	public abstract ReturnRDTO insertExpenseDocumentalSupport(DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO) throws ImiException;

	public abstract ReturnRDTO deleteExpenseDocumentalSupport(DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO) throws ImiException;

	public abstract ReturnRDTO insertDocumentalSupport(DocumentarySupportRDTO documentarySupportRDTO) throws ImiException;

	public abstract ReturnRDTO deleteDocumentalSupport(DocumentarySupportRDTO documentarySupportRDTO) throws ImiException;
}
