package es.bcn.imi.framework.vigia.frontal.gap.certification.services;

import java.util.List;

import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.ValueListGapBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface CertificationConceptGapService {

	public abstract List<ValueListGapBDTO> getCodesSubtypeConceptInstallationGap() throws ImiException;
	
	public abstract List<ValueListGapBDTO> getCodesTypeExtraordinaryConceptGap() throws ImiException;

	public abstract List<ValueListGapBDTO> getCodesTypeIvaGap() throws ImiException;

	public abstract List<ValueListGapBDTO> getCodesConceptCertificationGap() throws ImiException;

}
