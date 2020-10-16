package es.bcn.imi.framework.vigia.frontal.gap.inventary.services;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.LogicalModelGapBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface LogicalModelGapService {

	public abstract List<LogicalModelGapBDTO> getLogicalModelsGap(Map<String, Object> params) throws ImiException;

	public abstract List<LogicalModelGapBDTO> getLogicalModelsUbicationGap(String codeUbication) throws ImiException;

	public abstract List<LogicalModelGapBDTO> getLogicalModelsPositionUbicationGap(String codeUbication, long position, String codeMCL) throws ImiException;
	
	public abstract List<LogicalModelGapBDTO> getVacantLogicalModelsGap(String codeMCL, String codeMCF) throws ImiException;



	

}
