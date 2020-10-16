package es.bcn.imi.framework.vigia.frontal.business.masters;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterMastersRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBranchModelsVehicleRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCompatibilityMclUbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCompatibilitySensorMcfRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCompatibilitySensorRRMMRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventClassificationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnHolidayCalendarRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPricesTableRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnServiceContractRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnServiceHierarchyRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnServiceSubContractRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnWasteManagementPlantDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnWasteManagementPlantMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnWorkRegimeRelationshipRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface MastersService {
	
	public abstract ReturnServiceHierarchyRDTO selectServiceHierarchy(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException;

	public abstract ReturnServiceContractRDTO selectServiceContract(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException;

	public abstract ReturnServiceSubContractRDTO selectServiceSubContract(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException;

	public abstract ReturnWasteManagementPlantMassiveRDTO selectWasteManagementPlantMassive(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException;

	public abstract ReturnWasteManagementPlantDetailedRDTO selectWasteManagementPlantDetailed(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException;

	public abstract ReturnPricesTableRDTO selectPricesTable(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException;

	public abstract ReturnEventClassificationRDTO selectEventClassification(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException;

	public abstract ReturnBranchModelsVehicleRDTO selectBranchModelsVehicle(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException;

	public abstract ReturnHolidayCalendarRDTO selectHolidayCalendar(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException;

	public abstract ReturnWorkRegimeRelationshipRDTO selectWorkRegimeRelationship(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException;

	public abstract ReturnCompatibilityMclUbicationRDTO selectCompatibilityMclUbication() throws ImiException;

	public abstract ReturnCompatibilitySensorRRMMRDTO selectCompatibilitySensorRRMM() throws ImiException;

	public abstract ReturnCompatibilitySensorMcfRDTO selectCompatibilitySensorMcf() throws ImiException;
	
}
