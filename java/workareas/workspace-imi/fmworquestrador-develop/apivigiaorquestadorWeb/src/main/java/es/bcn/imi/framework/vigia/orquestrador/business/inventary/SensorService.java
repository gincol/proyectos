package es.bcn.imi.framework.vigia.orquestrador.business.inventary;

import javax.servlet.http.HttpServletRequest;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.SensorRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnSensorDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnSensorMassiveRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface SensorService {

	public abstract ReturnRDTO redirect(SensorRDTO sensorRDTO, HttpServletRequest request) throws ImiException;
	
	public abstract ReturnRDTO insert(SensorRDTO sensorBDTO) throws ImiException;

	public abstract ReturnRDTO update(SensorRDTO sensorBDTO) throws ImiException;

	public abstract ReturnRDTO delete(SensorRDTO sensorBDTO) throws ImiException;

	public abstract ReturnSensorMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnSensorDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	


	
}
