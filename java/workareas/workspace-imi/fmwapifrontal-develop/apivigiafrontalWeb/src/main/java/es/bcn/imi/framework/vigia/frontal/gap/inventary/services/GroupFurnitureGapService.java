package es.bcn.imi.framework.vigia.frontal.gap.inventary.services;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.GroupFurnitureGapBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface GroupFurnitureGapService {

	public abstract  List<GroupFurnitureGapBDTO> getGroupsFurnitureGap(Map<String, Object> params) throws ImiException;

}
