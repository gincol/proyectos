package es.bcn.imi.framework.vigia.frontal.gap.inventary.services;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.InstallationGapBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface InstallationGapService {

	public abstract  List<InstallationGapBDTO> getInstallationsGap(Map<String, Object> params) throws ImiException;

}
