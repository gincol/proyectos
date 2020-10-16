package es.bcn.imi.framework.vigia.frontal.gap.inventary.services;

import java.util.List;
import java.util.Map;


import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.PhysicalModelGapBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface PhysicalModelGapService {

	public abstract List<PhysicalModelGapBDTO> getPhysicalModelsGap(String codeMCF) throws ImiException;

	public abstract List<PhysicalModelGapBDTO> getPhysicalModelsLogicalModelGap(String codeMCL) throws ImiException;

	public abstract List<PhysicalModelGapBDTO> getPhysicalModelsGap(String codeUbication, String codeMCL, String codeMCF) throws ImiException;

	public abstract List<PhysicalModelGapBDTO> getPhysicalModelsGap(Map<String, Object> params) throws ImiException;;
}
