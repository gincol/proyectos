package es.bcn.imi.framework.vigia.frontal.inventary.services;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.business.dto.SensorBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface SensorService {

	public abstract List<SensorBDTO> getSensorsContract(String codeContract) throws ImiException;

	public abstract List<SensorBDTO> getSensorsByCodes(List<String> sensorCodes) throws ImiException;

	public abstract List<SensorBDTO> getSensorsByParams(Map<String, Object> params) throws ImiException;

	public abstract List<SensorBDTO> getSensorsByCodeRRMM(Map<String, Object> params) throws ImiException;

	public abstract List<SensorBDTO> getSensorsByCodeMCF(Map<String, Object> params) throws ImiException;

}
