package es.bcn.imi.framework.vigia.frontal.gap.planification.services;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.PlanificationGapBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface PlanificationGapService {

	public abstract  List<PlanificationGapBDTO> getPlanificationsGap(Map<String,Object> params) throws ImiException;

}
