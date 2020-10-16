package es.bcn.imi.framework.vigia.frontal.gap.execution.services;

import java.util.List;

import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.PlantWeighingGapBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface PlantWeighingGapService {

	public abstract  List<PlantWeighingGapBDTO> getPlantWeighingsGap(String code) throws ImiException;

}
