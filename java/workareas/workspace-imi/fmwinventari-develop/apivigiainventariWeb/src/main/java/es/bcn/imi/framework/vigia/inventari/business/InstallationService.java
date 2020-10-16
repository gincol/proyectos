package es.bcn.imi.framework.vigia.inventari.business;

import es.bcn.vigia.fmw.libcommons.business.dto.InstallationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationPeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface InstallationService {

	public abstract ReturnRDTO insert(InstallationBDTO installationBDTO) throws ImiException;

	public abstract ReturnRDTO update(InstallationBDTO installationBDTO) throws ImiException;

	public abstract ReturnRDTO delete(InstallationBDTO installationBDTO) throws ImiException;

	public abstract ReturnRDTO insertExpense(InstallationBDTO installationBDTO) throws ImiException;

	public abstract ReturnRDTO insertAmortizationBase(InstallationBDTO installationBDTO) throws ImiException;

	public abstract ReturnRDTO insertApportionment(InstallationBDTO installationBDTO) throws ImiException;
	
	public abstract ReturnInstallationDetailedRDTO selectDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException;
	
	public abstract ReturnMassiveRDTO selectMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException;

	public abstract ReturnBreakdownAmortizationRDTO selectAmortization(QueryParameterBDTO queryParameterBDTO) throws ImiException;

	public abstract ReturnInstallationExpensesRDTO selectExpenses(QueryParameterBDTO queryParameterBDTO) throws ImiException;

	public abstract ReturnInstallationPeriodRDTO selectPeriod(QueryParameterBDTO queryParameterBDTO) throws ImiException;

}
