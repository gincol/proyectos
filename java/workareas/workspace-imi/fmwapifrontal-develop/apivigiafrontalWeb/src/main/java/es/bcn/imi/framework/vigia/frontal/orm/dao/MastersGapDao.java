package es.bcn.imi.framework.vigia.frontal.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.BranchModelsVehicleGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.EventClassificationGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.HolidayCalendarGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ServiceContractGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ServiceHierarchyGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ServiceSubContractGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.WasteManagementPlantDetailedGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.WasteManagementPlantMassiveGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.WorkRegimeRelationshipGap;

public interface MastersGapDao extends MyBatisDaoGap {
	
	public abstract List<ServiceHierarchyGap> getServiceHierarchy(Map<String, Object> params);

	public abstract List<ServiceContractGap> getServiceContract(Map<String, Object> params);

	public abstract List<ServiceSubContractGap> getServiceSubContract(Map<String, Object> params);

	public abstract List<WasteManagementPlantMassiveGap> getWasteManagementPlantMassive(Map<String, Object> params);

	public abstract List<WasteManagementPlantDetailedGap> getWasteManagementPlantDetailed(Map<String, Object> params);

	public abstract List<EventClassificationGap> getEventClassification(Map<String, Object> params);

	public abstract List<BranchModelsVehicleGap> getBranchModelsVehicle(Map<String, Object> params);

	public abstract List<HolidayCalendarGap> getHolidayCalendar(Map<String, Object> params);

	public abstract List<WorkRegimeRelationshipGap> getWorkRegimeRelationship(Map<String, Object> params);
	
}

