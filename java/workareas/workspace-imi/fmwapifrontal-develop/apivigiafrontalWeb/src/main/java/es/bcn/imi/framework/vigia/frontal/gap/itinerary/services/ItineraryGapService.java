package es.bcn.imi.framework.vigia.frontal.gap.itinerary.services;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.ItineraryGapBDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface ItineraryGapService {

	public abstract  List<ItineraryGapBDTO> getItinerariesGap(Map<String, Object> params) throws ImiException;

}
