/**
 * 
 */
package es.bcn.imi.framework.vigia.inventari.business;

import es.bcn.vigia.fmw.libcommons.business.dto.MaterialResourceBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehiclePeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface MaterialResourceService {

	public abstract ReturnRDTO insert(MaterialResourceBDTO materialResourceBDTO) throws ImiException;

	public abstract ReturnRDTO update(MaterialResourceBDTO materialResourceBDTO) throws ImiException;

	public abstract ReturnRDTO delete(MaterialResourceBDTO materialResourceBDTO) throws ImiException;

	public abstract ReturnRDTO insertExpense(MaterialResourceBDTO materialResourceBDTO) throws ImiException;

	public abstract ReturnRDTO insertAmortizationBase(MaterialResourceBDTO materialResourceBDTO) throws ImiException;

	public abstract ReturnRDTO insertApportionment(MaterialResourceBDTO materialResourceBDTO) throws ImiException;

	public abstract ReturnMaterialResourceDetailedRDTO selectDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException;

	public abstract ReturnMassiveRDTO selectMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException;

	public abstract ReturnMassiveRDTO selectVehicleMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException;

	public abstract ReturnMaterialResourceVehicleDetailedRDTO selectVehicleDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException;

	public abstract ReturnBreakdownAmortizationRDTO selectAmortization(QueryParameterBDTO queryParameterBDTO) throws ImiException;

	public abstract ReturnMaterialResourceVehicleExpensesRDTO selectExpenses(QueryParameterBDTO queryParameterBDTO) throws ImiException;

	public abstract ReturnMaterialResourceVehiclePeriodRDTO selectPeriod(QueryParameterBDTO queryParameterBDTO) throws ImiException;
	
}
