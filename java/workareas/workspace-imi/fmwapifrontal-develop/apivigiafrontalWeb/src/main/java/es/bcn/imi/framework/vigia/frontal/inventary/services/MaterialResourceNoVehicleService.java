package es.bcn.imi.framework.vigia.frontal.inventary.services;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.business.dto.MaterialResourceBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface MaterialResourceNoVehicleService {

	public abstract List<MaterialResourceBDTO> getMaterialResourcesNoVehicleByCode(Map<String, Object> params) throws ImiException;

}
