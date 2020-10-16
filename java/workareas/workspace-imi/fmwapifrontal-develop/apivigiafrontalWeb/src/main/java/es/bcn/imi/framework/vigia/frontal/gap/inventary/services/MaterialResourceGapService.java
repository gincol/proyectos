package es.bcn.imi.framework.vigia.frontal.gap.inventary.services;

import java.util.List;

import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.MaterialResourceGapBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface MaterialResourceGapService {

	public abstract List<MaterialResourceGapBDTO> getMaterialResourcesGap(String codeRRMM) throws ImiException;

}
