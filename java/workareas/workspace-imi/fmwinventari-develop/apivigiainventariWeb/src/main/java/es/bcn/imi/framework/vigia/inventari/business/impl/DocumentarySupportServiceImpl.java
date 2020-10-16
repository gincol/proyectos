package es.bcn.imi.framework.vigia.inventari.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.inventari.business.DocumentarySupportService;
import es.bcn.imi.framework.vigia.inventari.integration.orm.dao.DocumentarySupportDao;
import es.bcn.vigia.fmw.integration.document.output.AddDocumentsDefOutput;
import es.bcn.vigia.fmw.integration.document.output.DeleteDocumentsOutput;
import es.bcn.vigia.fmw.integration.document.output.SetAttributeOutput;
import es.bcn.vigia.fmw.integration.document.service.IntegrationDocumentService;
import es.bcn.vigia.fmw.libcommons.business.dto.DocumentarySupportBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.DocumentarySupportExpenseBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.DocumentarySupportFileBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.GdAddDocumentDefBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.GdContentFileDefWsBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.GdCredentialsImiDefWsBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.GdDeleteDocumentBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.GdDocumentDefWsBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.GdSetAttributeBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterDocumentarySupportBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.MessageContansts;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.DocumentarySupport;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.DocumentarySupportExpense;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.DocumentarySupportGetRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.DocumentumUtils;
import es.bcn.vigia.fmw.libutils.convert.DocumentarySupportConvert;
import es.bcn.vigia.fmw.libutils.convert.DocumentarySupportExpenseConvert;
import es.bcn.vigia.fmw.libutils.convert.DocumentarySupportGetConvert;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_INVENTARY_DOCUMENTARY_SUPPORT)
public class DocumentarySupportServiceImpl implements DocumentarySupportService {

	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;
	
	
	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INT_DOCUMENT)
	IntegrationDocumentService service;
	
	@Value("${user.documentum}")
	private String userDocumentum;


	@Value("${password.documentum}")
	private String passwordDocumentum;

	@Value("${aplication.documentum}")
	private String aplicationDocumentum;
	
	@Value("${path.documentum}")
	private String pathDocumentum;
	
	
	private ReturnRDTO returnRDTO;
	
	private String isCode;

	private String isMessage;


	@Override
	public ReturnRDTO insert(DocumentarySupportBDTO documentarySupportBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		AddDocumentsDefOutput response;
		try {
			response = createDocument(documentarySupportBDTO);
			if (response.getResult().equals(ImiConstants.DOCUMENTUM_RESPONSE_OK))
			{
				documentarySupportBDTO.setCodeMunicipalDocument(response.getId().get(0));
					
			}else{
				logger.error(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, response.getResult()));
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_DOCUMENTUM_REGISTERED.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_ERROR_DOCUMENTUM_REGISTERED.getMessage());
				return returnRDTO;
			}
		} catch (Exception e1) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportBDTO.toString()), e1);

			returnRDTO.setCode(ReturnEnum.CODE_ERROR_DOCUMENTUM_REGISTERED.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_ERROR_DOCUMENTUM_REGISTERED.getMessage());
			return returnRDTO;
		}
		
		final DocumentarySupport documentarySupport = DocumentarySupportConvert.bdto2object(documentarySupportBDTO);
		try {
			
			myBatisTemplate.execute(DocumentarySupportDao.class, new MyBatisDaoCallback<Void>() {

				@Override
				public Void execute(MyBatisDao dao) {
					((DocumentarySupportDao) dao).insert(documentarySupport);
					return null;
				}
			});
			
			isCode = ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription();
			isMessage = ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage();
			
		} catch (Exception e) {	
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportBDTO.toString()), e);		
			returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getCodeDescription());
			returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_FMW));
			return returnRDTO;
		}
		
		try {					
				 
			myBatisTemplate.execute(DocumentarySupportDao.class, new MyBatisDaoCallback<Void>() {

				@Override
				public Void execute(MyBatisDao dao) {
					((DocumentarySupportDao) dao).insertDocumentElement(documentarySupport);
					return null;
				}
			});
			isCode = ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription();
			isMessage = ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage();
			

		} catch (Exception e) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportBDTO.toString()), e);			
			returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getCodeDescription());
			returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_FMW));
			return returnRDTO;
		}
		 
		try {
			if(isCode.equals(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription())) {
				
				SetAttributeOutput responseUpdateStatus = updateStatus(documentarySupportBDTO);
				if (responseUpdateStatus.getResult().equals(ImiConstants.DOCUMENTUM_RESPONSE_OK))
				{
					isCode = ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription();
					isMessage = ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage();
						
				}else{
					isCode = ReturnEnum.CODE_ERROR_DOCUMENTUM_UPDATED.getCodeDescription();
					isMessage = ReturnEnum.CODE_ERROR_DOCUMENTUM_UPDATED.getMessage();
				}
			}
		} catch (Exception e1) {
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportBDTO.toString()), e1);			
			returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getCodeDescription());
			returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_DOCUMENTUM));
			return returnRDTO;
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}
	
	private SetAttributeOutput updateStatus(DocumentarySupportBDTO documentarySupportBDTO) throws Exception{
		try {
			GdSetAttributeBDTO gdSetAttributeBDTO = new GdSetAttributeBDTO();
			GdCredentialsImiDefWsBDTO credentials = new GdCredentialsImiDefWsBDTO();
			credentials.setUsername(userDocumentum);
			credentials.setPassword(passwordDocumentum);
			credentials.setApplication(aplicationDocumentum);
			gdSetAttributeBDTO.setCredentials(credentials);
			gdSetAttributeBDTO.setAttrName(ImiConstants.DOCUMENTUM_ATTRIBUTE_DOCUMENT_STATE);
			gdSetAttributeBDTO.setAttrValue(ImiConstants.DOCUMENTUM_STATE_DEFINTIVE);
			gdSetAttributeBDTO.setId(documentarySupportBDTO.getCodeMunicipalDocument());
			
			SetAttributeOutput response = service.setAttribute(gdSetAttributeBDTO);
			return response;
			
		} catch (Exception e1) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportBDTO.toString()), e1);
			throw e1;
			
		}
	}

	private AddDocumentsDefOutput createDocument(DocumentarySupportBDTO documentarySupportBDTO) throws Exception
	{
		try {
			GdAddDocumentDefBDTO gdAddDocumentDefBDTO = new GdAddDocumentDefBDTO();
			GdCredentialsImiDefWsBDTO credentials = new GdCredentialsImiDefWsBDTO();
			credentials.setUsername(userDocumentum);
			credentials.setPassword(passwordDocumentum);
			credentials.setApplication(aplicationDocumentum);
			gdAddDocumentDefBDTO.setCredentials(credentials);
			
			
			if (documentarySupportBDTO.getDocumentarySupportFileBDTO()!=null)
			{
				DocumentarySupportFileBDTO file = documentarySupportBDTO.getDocumentarySupportFileBDTO();
				GdDocumentDefWsBDTO document = new GdDocumentDefWsBDTO();
				document.setItemType(ImiConstants.DOCUMENTUM_ITEM_TYPE);
				
				document.setAttribute(DocumentumUtils.getDocumentumAttributesList(file.getFileName(),
						null, ImiConstants.DOCUMENTUM_STATE_PROVISIONAL));
				GdContentFileDefWsBDTO contentFile = new GdContentFileDefWsBDTO();
				contentFile.setFileName(file.getFileName());
				contentFile.setData(file.getFileContent());
				document.getContentFile().add(contentFile);
				gdAddDocumentDefBDTO.getDocumentDef().add(document);
				
			}
			gdAddDocumentDefBDTO.setPath(pathDocumentum);
			AddDocumentsDefOutput response = service.createDocument(gdAddDocumentDefBDTO);
			return response;
			
		} catch (Exception e1) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportBDTO.toString()), e1);
			throw e1;
			
			
		}

	}
	
	private DeleteDocumentsOutput deleteDocument(String codeMunicipalDocument) throws Exception
	{
		try {
			GdDeleteDocumentBDTO gdDeleteDocumentBDTO = new GdDeleteDocumentBDTO();
			GdCredentialsImiDefWsBDTO credentials = new GdCredentialsImiDefWsBDTO();
			credentials.setUsername(userDocumentum);
			credentials.setPassword(passwordDocumentum);
			credentials.setApplication(aplicationDocumentum);
			
			gdDeleteDocumentBDTO.setCredentials(credentials);
			gdDeleteDocumentBDTO.getId().add(codeMunicipalDocument);
			
			DeleteDocumentsOutput response = service.deleteDocument(gdDeleteDocumentBDTO);
			return response;
			
		} catch (Exception e1) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, "gestor documental"), e1);
			throw e1;
			
			
		}
	}

	
	@Override
	public ReturnRDTO update(DocumentarySupportBDTO documentarySupportBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		final DocumentarySupport documentarySupport = DocumentarySupportConvert.bdto2object(documentarySupportBDTO);
		DocumentarySupport documentarySupportBBDD =null;
		try {
			documentarySupportBBDD = myBatisTemplate.execute(DocumentarySupportDao.class, new MyBatisDaoCallback<DocumentarySupport>() {

				@Override
				public DocumentarySupport execute(MyBatisDao dao) {
					return ((DocumentarySupportDao) dao).selectDocumentExpense(documentarySupport);
					
				}
			});
			
			try {

				DeleteDocumentsOutput response = deleteDocument(documentarySupportBBDD.getCodeMunicipalDocument());
				if (!response.getResult().equals(ImiConstants.DOCUMENTUM_RESPONSE_OK))
				{
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_DOCUMENTUM_REMOVED.getCodeDescription());
					returnRDTO.setMessage(ReturnEnum.CODE_ERROR_DOCUMENTUM_REMOVED.getMessage());
					return returnRDTO;
				}
			}catch (Exception e) {
				logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportBBDD), e);			
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_TRANSACTION_DELETE_NOT_COMPLETE.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_TRANSACTION_DELETE_NOT_COMPLETE.getMessage(), MessageContansts.ESTAT_KO_DOCUMENTUM));
				return returnRDTO;
			}
		} catch (Exception e1) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportBDTO.toString()), e1);

			returnRDTO.setCode(ReturnEnum.CODE_ERROR_DOCUMENTUM_REMOVED.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_ERROR_DOCUMENTUM_REMOVED.getMessage());
			return returnRDTO;
		}	
		try{	
			myBatisTemplate.execute(DocumentarySupportDao.class, new MyBatisDaoCallback<Void>() {

				@Override
				public Void execute(MyBatisDao dao) {
					((DocumentarySupportDao) dao).update(documentarySupport);
					return null;
				}
			});
			
			isCode = ReturnEnum.CODE_SUCCESS_REMOVED.getCodeDescription();
			isMessage = ReturnEnum.CODE_SUCCESS_REMOVED.getMessage();
			
		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportBDTO.toString()), e);
		}
		try {
			
			if(isCode.equals(ReturnEnum.CODE_SUCCESS_REMOVED.getCodeDescription())) {
				 
				
				documentarySupport.setDateCreation(documentarySupportBBDD.getDateCreation());
				documentarySupport.setCodeMunicipalDocument(documentarySupportBBDD.getCodeMunicipalDocument());
				documentarySupport.setNameDocument(documentarySupportBBDD.getNameDocument());
				documentarySupport.setTypeDocument(documentarySupportBBDD.getTypeDocument());
				documentarySupport.setObservations(documentarySupportBBDD.getObservations());
				documentarySupport.setCodeActuation(documentarySupportBBDD.getCodeActuation());

				
				myBatisTemplate.execute(DocumentarySupportDao.class, new MyBatisDaoCallback<Void>() {

					@Override
					public Void execute(MyBatisDao dao) {
						((DocumentarySupportDao) dao).deleteDocumentElement(documentarySupport);
						return null;
					}
				});
				isCode = ReturnEnum.CODE_SUCCESS_REMOVED.getCodeDescription();
				isMessage = ReturnEnum.CODE_SUCCESS_REMOVED.getMessage();	
				
					
			} else {

				isCode = ReturnEnum.CODE_ERROR_REMOVED.getCodeDescription();
				isMessage = ReturnEnum.CODE_ERROR_REMOVED.getMessage();
			}
			

		} catch (Exception e) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportBDTO.toString()),
					e);
		}
		
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;				
	}


	@Override
	public ReturnRDTO insertExpense(DocumentarySupportExpenseBDTO documentarySupportExpenseBDTO) throws ImiException {
	logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		AddDocumentsDefOutput response;
		try {
			response = createDocument(documentarySupportExpenseBDTO);
			if (response.getResult().equals(ImiConstants.DOCUMENTUM_RESPONSE_OK))
			{
				documentarySupportExpenseBDTO.setCodeMunicipalDocument(response.getId().get(0));
					
			}else{
				logger.error(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, response.getResult()));
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_DOCUMENTUM_REGISTERED.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_ERROR_DOCUMENTUM_REGISTERED.getMessage());
				return returnRDTO;
			}
		} catch (Exception e1) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportExpenseBDTO.toString()), e1);

			returnRDTO.setCode(ReturnEnum.CODE_ERROR_DOCUMENTUM_REGISTERED.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_ERROR_DOCUMENTUM_REGISTERED.getMessage());
			return returnRDTO;
		}
		
		final DocumentarySupportExpense documentarySupportExpense = DocumentarySupportExpenseConvert.bdto2object(documentarySupportExpenseBDTO);
		try {
			
			myBatisTemplate.execute(DocumentarySupportDao.class, new MyBatisDaoCallback<Void>() {

				@Override
				public Void execute(MyBatisDao dao) {
					((DocumentarySupportDao) dao).insert(documentarySupportExpense);
					return null;
				}
			});
			
			isCode = ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription();
			isMessage = ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage();
			
		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportExpenseBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportExpenseBDTO.toString()), e);
		}
		
		try {
			
			if(isCode.equals(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription())) {
				 
				myBatisTemplate.execute(DocumentarySupportDao.class, new MyBatisDaoCallback<Void>() {

					@Override
					public Void execute(MyBatisDao dao) {
						((DocumentarySupportDao) dao).insertDocumentExpense(documentarySupportExpense);
						return null;
					}
				});
				isCode = ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription();
				isMessage = ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage();


			} else {

				isCode = ReturnEnum.CODE_ERROR_REGISTERED.getCodeDescription();
				isMessage = ReturnEnum.CODE_ERROR_REGISTERED.getMessage();
			}

		} catch (Exception e) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportExpenseBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportExpenseBDTO.toString()),
					e);
		}

		try {
			if(isCode.equals(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription())) {

				SetAttributeOutput responseUpdateStatus = updateStatus(documentarySupportExpenseBDTO);
				if (responseUpdateStatus.getResult().equals(ImiConstants.DOCUMENTUM_RESPONSE_OK))
				{
					isCode = ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription();
					isMessage = ReturnEnum.CODE_SUCCESS_REGISTERED.getMessage();
				}else{
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_DOCUMENTUM_UPDATED.getCodeDescription());
					returnRDTO.setMessage(ReturnEnum.CODE_ERROR_DOCUMENTUM_UPDATED.getMessage());
					return returnRDTO;
				}
			}
		} catch (Exception e1) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportExpenseBDTO.toString()), e1);

			returnRDTO.setCode(ReturnEnum.CODE_ERROR_DOCUMENTUM_UPDATED.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_ERROR_DOCUMENTUM_UPDATED.getMessage());
			return returnRDTO;
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;

	}


	@Override
	public ReturnRDTO updateExpense(DocumentarySupportExpenseBDTO documentarySupportExpenseBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		final DocumentarySupportExpense documentarySupportExpense = DocumentarySupportExpenseConvert.bdto2object(documentarySupportExpenseBDTO);
		DocumentarySupport documentarySupportBBDD =null;
		try {
			documentarySupportBBDD = myBatisTemplate.execute(DocumentarySupportDao.class, new MyBatisDaoCallback<DocumentarySupport>() {

				@Override
				public DocumentarySupport execute(MyBatisDao dao) {
					return ((DocumentarySupportDao) dao).selectDocumentExpense(documentarySupportExpense);
					
				}
			});

			DeleteDocumentsOutput response = deleteDocument(documentarySupportBBDD.getCodeMunicipalDocument());
			if (!response.getResult().equals(ImiConstants.DOCUMENTUM_RESPONSE_OK))
			{
				
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_DOCUMENTUM_REMOVED.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_ERROR_DOCUMENTUM_REMOVED.getMessage());
				return returnRDTO;
			}
		} catch (Exception e1) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportExpenseBDTO.toString()), e1);

			returnRDTO.setCode(ReturnEnum.CODE_ERROR_DOCUMENTUM_REMOVED.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_ERROR_DOCUMENTUM_REMOVED.getMessage());
			return returnRDTO;
		}
		try{
			
			myBatisTemplate.execute(DocumentarySupportDao.class, new MyBatisDaoCallback<Void>() {

				@Override
				public Void execute(MyBatisDao dao) {
					((DocumentarySupportDao) dao).update(documentarySupportExpense);
					return null;
				}
			});
			
			isCode = ReturnEnum.CODE_SUCCESS_REMOVED.getCodeDescription();
			isMessage = ReturnEnum.CODE_SUCCESS_REMOVED.getMessage();
			
		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportExpenseBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportExpenseBDTO.toString()), e);
		}
		try {
			
			if(isCode.equals(ReturnEnum.CODE_SUCCESS_REMOVED.getCodeDescription())) {
				 
				documentarySupportExpense.setDateCreation(documentarySupportBBDD.getDateCreation());
				documentarySupportExpense.setCodeMunicipalDocument(documentarySupportBBDD.getCodeMunicipalDocument());
				documentarySupportExpense.setNameDocument(documentarySupportBBDD.getNameDocument());
				documentarySupportExpense.setTypeDocument(documentarySupportBBDD.getTypeDocument());
				documentarySupportExpense.setObservations(documentarySupportBBDD.getObservations());
				documentarySupportExpense.setCodeActuation(documentarySupportBBDD.getCodeActuation());

				myBatisTemplate.execute(DocumentarySupportDao.class, new MyBatisDaoCallback<Void>() {

					@Override
					public Void execute(MyBatisDao dao) {
						((DocumentarySupportDao) dao).deleteDocumentExpense(documentarySupportExpense);
						return null;
					}
				});
					
				isCode = ReturnEnum.CODE_SUCCESS_REMOVED.getCodeDescription();
				isMessage = ReturnEnum.CODE_SUCCESS_REMOVED.getMessage();	
					
				
					
			} else {

				isCode = ReturnEnum.CODE_ERROR_REMOVED.getCodeDescription();
				isMessage = ReturnEnum.CODE_ERROR_REMOVED.getMessage();
			}
			

		} catch (Exception e) {

			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportExpenseBDTO.toString()), e);
			throw new ImiException(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, documentarySupportExpenseBDTO.toString()),
					e);
		}
		
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;				

	}

	@Override
	public ReturnDocumentarySupportRDTO selectDocumentarySupport(QueryParameterDocumentarySupportBDTO queryParameterDocumentarySupportBDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		ReturnDocumentarySupportRDTO returnDocumentarySupportRDTO = new ReturnDocumentarySupportRDTO();
		List<DocumentarySupportGetRDTO> documentarySupportGetRDTOs  = new ArrayList<>();
		returnRDTO = new ReturnRDTO();

		try {

			documentarySupportGetRDTOs = getDocumentarySupport(queryParameterDocumentarySupportBDTO);
			
			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception e) {
			
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterDocumentarySupportBDTO.toString()), e);
			throw new ImiException(e);
		}

		returnDocumentarySupportRDTO.setReturnRDTO(returnRDTO);
		returnDocumentarySupportRDTO.setDocumentarySupportGetRDTOs(documentarySupportGetRDTOs);
		logger.info(LogsConstants.LOG_END);
		return returnDocumentarySupportRDTO;
	}

	public List<DocumentarySupportGetRDTO> getDocumentarySupport(QueryParameterDocumentarySupportBDTO queryParameterDocumentarySupportBDTO) throws ImiException {
		List<DocumentarySupportGetRDTO> documentarySupportGetRDTOs = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = queryParameterDocumentarySupportBDTO.getMap();
			List<DocumentarySupport> documentarySupports = myBatisTemplate.execute(DocumentarySupportDao.class,
					new MyBatisDaoCallback<List<DocumentarySupport>>() {
						@Override
						public List<DocumentarySupport> execute(MyBatisDao dao) {
							return ((DocumentarySupportDao) dao).selectDocumentarySupport(map);
						}
					});

			if (documentarySupports != null && !documentarySupports.isEmpty()) {
				documentarySupportGetRDTOs = DocumentarySupportGetConvert.object2rdto(documentarySupports);
			}
		} catch (Exception e) {			
				
			logger.error(String.format(LogsConstants.LOG_GENERAL_CALL_ERROR, queryParameterDocumentarySupportBDTO.toString()), e);
			throw new ImiException(e);
		}
		return documentarySupportGetRDTOs;
	}
}
