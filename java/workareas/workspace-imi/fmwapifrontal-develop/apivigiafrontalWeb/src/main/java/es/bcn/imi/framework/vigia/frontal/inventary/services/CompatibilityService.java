package es.bcn.imi.framework.vigia.frontal.inventary.services;

import java.util.List;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesMCFSensor;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesMCLUbication;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesRRMMSensor;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface CompatibilityService {

	public abstract List<CompatibilityTypesMCLUbication> getCompatilibityTypeMCLUbication(String codeMCLType, String codeUbicationType) throws ImiException;

	public abstract List<CompatibilityTypesMCLUbication> getCompatibleTypesMCLByTypeUbication(String codeUbicationType) throws ImiException;

	public abstract List<CompatibilityTypesMCFSensor> getCompatibleTypesSensorByTypeMCF(String codeMCFType) throws ImiException;

	public abstract List<CompatibilityTypesRRMMSensor> getCompatibleTypesSensorByTypeRRMM(String codeMaterialResourceType) throws ImiException;

	public abstract List<CompatibilityTypesMCFSensor> getCompatibilityTypeMCFTypeSensor(String codeMCFType, String codeSensorType)
			throws ImiException;

	public abstract List<CompatibilityTypesRRMMSensor> getCompatibilityTypeRRMMTypeSensor(String codeMaterialResourceType,
			String codeSensorType) throws ImiException;

}
