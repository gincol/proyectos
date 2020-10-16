package es.bcn.imi.framework.vigia.orquestrador.business.async;

import javax.servlet.http.HttpServletRequest;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AsyncRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface AsyncService {

	public abstract ReturnRDTO ubications(AsyncRDTO<UbicationRDTO> rdto, HttpServletRequest request) throws ImiException;
	
	public abstract ReturnRDTO installation(AsyncRDTO<InstallationRDTO> rdto, HttpServletRequest request) throws ImiException;
	
	public abstract ReturnRDTO mcl(AsyncRDTO<LogicalModelRDTO> rdto, HttpServletRequest request) throws ImiException;
	
	public abstract ReturnRDTO mcf(AsyncRDTO<PhysicalModelRDTO> rdto, HttpServletRequest request) throws ImiException;
	
	public abstract ReturnRDTO rrmm(AsyncRDTO<MaterialResourceRDTO> rdto, HttpServletRequest request) throws ImiException;
	
	public abstract ReturnRDTO commerce(AsyncRDTO<CommerceRDTO> rdto, HttpServletRequest request) throws ImiException;
	
	public abstract ReturnRDTO redirect(AsyncRDTO<?> rdto, String type, HttpServletRequest request) throws ImiException;
		
}
