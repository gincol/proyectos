package es.bcn.imi.framework.vigia.frontal.business.inventary;


import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.SensorRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnSensorDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnSensorMassiveRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface SensorsService {

	public abstract ReturnRDTO insert(SensorRDTO sensorRDTO) throws ImiException;

	public abstract ReturnRDTO update(SensorRDTO sensorRDTO) throws ImiException;

	public abstract ReturnRDTO delete(SensorRDTO sensorRDTO) throws ImiException;

	public abstract ReturnSensorDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnSensorMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	
}
