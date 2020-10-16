package es.bcn.imi.framework.vigia.frontal.gap.inventary.services;

import java.util.List;

import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.TypeSubtypeExpenseGapBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface TypeSubtypeExpenseGapService {

	public abstract List<TypeSubtypeExpenseGapBDTO> getTypeSubtypesExpenseGap(String codeType) throws ImiException;

}
