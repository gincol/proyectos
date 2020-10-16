package es.bcn.imi.framework.vigia.frontal.gap.inventary.services;

import java.util.List;

import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.BrandModelGapBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface BrandModelGapService {

	public abstract List<BrandModelGapBDTO> getBrandModelsGap(String codeBrand) throws ImiException;

}
