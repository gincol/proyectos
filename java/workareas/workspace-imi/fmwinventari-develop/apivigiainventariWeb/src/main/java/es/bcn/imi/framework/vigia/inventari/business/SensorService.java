package es.bcn.imi.framework.vigia.inventari.business;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.SensorBDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnSensorDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnSensorMassiveRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface SensorService {

	public abstract ReturnRDTO insert(SensorBDTO sensorBDTO) throws ImiException;

	public abstract ReturnRDTO update(SensorBDTO sensorBDTO) throws ImiException;

	public abstract List<SensorBDTO> selectByCodeMCF(Map<String, Object> map) throws ImiException;

	public abstract List<SensorBDTO> selectByCodeRRMM(Map<String, Object> map) throws ImiException;

	public abstract ReturnRDTO delete(SensorBDTO sensorBDTO)throws ImiException;

	public abstract ReturnSensorDetailedRDTO selectDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException;

	public abstract ReturnSensorMassiveRDTO selectMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException;

}