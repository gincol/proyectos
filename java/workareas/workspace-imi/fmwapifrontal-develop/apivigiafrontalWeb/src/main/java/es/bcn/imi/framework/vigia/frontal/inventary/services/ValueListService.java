package es.bcn.imi.framework.vigia.frontal.inventary.services;

import java.util.List;

import es.bcn.vigia.fmw.libcommons.business.dto.ValueListBDTO;
import es.bcn.vigia.fmw.libcommons.valueobject.Type;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface ValueListService {

	public abstract List<ValueListBDTO> getValueList(Type type) throws ImiException;

}
