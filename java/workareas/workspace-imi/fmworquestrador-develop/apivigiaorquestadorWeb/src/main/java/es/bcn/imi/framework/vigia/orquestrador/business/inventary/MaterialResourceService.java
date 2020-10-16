package es.bcn.imi.framework.vigia.orquestrador.business.inventary;

import javax.servlet.http.HttpServletRequest;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehiclePeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface MaterialResourceService {

	public abstract ReturnRDTO redirect(MaterialResourceRDTO materialResourceRDTO, HttpServletRequest request)
			throws ImiException;

	public abstract ReturnRDTO insert(MaterialResourceRDTO materialResourceRDTO) throws ImiException;

	public abstract ReturnRDTO update(MaterialResourceRDTO materialResourceRDTO) throws ImiException;

	public abstract ReturnRDTO delete(MaterialResourceRDTO materialResourceRDTO) throws ImiException;
	
	public abstract ReturnRDTO insertExpense(MaterialResourceRDTO materialResourceRDTO) throws ImiException;

	public abstract ReturnRDTO insertAmortizationBase(MaterialResourceRDTO materialResourceRDTO) throws ImiException;

	public abstract ReturnRDTO insertApportionment(MaterialResourceRDTO materialResourceRDTO) throws ImiException;

	public abstract ReturnMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnMaterialResourceDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnMassiveRDTO selectVehicleMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnMaterialResourceVehicleDetailedRDTO selectVehicleDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnBreakdownAmortizationRDTO selectAmortization(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnMaterialResourceVehicleExpensesRDTO selectExpenses(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnMaterialResourceVehiclePeriodRDTO selectPeriod(QueryParameterRDTO queryParameterRDTO) throws ImiException;
}
