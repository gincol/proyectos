package es.bcn.imi.framework.vigia.frontal.gap.inventary.services;

import java.util.List;

import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.ValueListGapBDTO;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface ValueListGapService {

	public abstract List<ValueListGapBDTO> getValueListGap(Entity entity) throws ImiException;

}
