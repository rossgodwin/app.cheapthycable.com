package com.gwn.xcbl.web.rest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.gwn.xcbl.bl.bill.BillCreateObservable;
import com.gwn.xcbl.bl.bill.BillCreateObserver;
import com.gwn.xcbl.bl.bill.report.BillReportDAO;
import com.gwn.xcbl.common.UserPrincipal;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.dao.be.BeDAOImpl;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;
import com.gwn.xcbl.data.model.AuthenticationException;
import com.gwn.xcbl.data.model.HaversineFormulaConsts;
import com.gwn.xcbl.data.model.be.BeStats;
import com.gwn.xcbl.data.shared.PagingResultDTO;
import com.gwn.xcbl.data.shared.ReqParams;
import com.gwn.xcbl.data.shared.ResponseDTO;
import com.gwn.xcbl.data.shared.be.BeStatsDTO;
import com.gwn.xcbl.data.shared.bill.BillDTO;
import com.gwn.xcbl.data.shared.bill.BillLocationStatsDTO;
import com.gwn.xcbl.data.shared.bill.BillSearchCritrDTO;
import com.gwn.xcbl.data.shared.bill.create.BillCreateDTO;
import com.gwn.xcbl.data.shared.bill.report.BillReportCritrDTO;
import com.gwn.xcbl.data.transformer.ProviderDtoTransformer;
import com.gwn.xcbl.data.transformer.bill.BillCableOptionsDtoTransformer;
import com.gwn.xcbl.data.transformer.bill.BillDtoTransformer;
import com.gwn.xcbl.data.transformer.bill.BillInternetOptionsDtoTransformer;
import com.gwn.xcbl.data.transformer.geo.GeoZipCodeDtoTransformer;

@Path("bill")
public class BillRS extends BaseRS {

//	private static Log log = LogFactory.getLog(BillRS.class);
	
	@GET
	@Path("/my/list")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getMyBillsList(
			@Context HttpServletRequest httpRequest) {
		try {
			UserPrincipal principal = authenticate(httpRequest);
			
			User user = DAOFactory.getInstance().getUserDAO().findByUsername(principal.getUsername());
			
			List<Bill> objs = DAOFactory.getInstance().getBillDAO().findAccountBills(user.getAccount().getId(), null, null);
			
			List<BillDTO> dtos = transform(objs);
			
			ResponseDTO<List<BillDTO>> response = new ResponseDTO<List<BillDTO>>(dtos);
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	private List<BillDTO> transform(List<Bill> objs) {
		BillDtoTransformer trnsfmr = new BillDtoTransformer();
		trnsfmr.setPrvdrTrnsfmr(new ProviderDtoTransformer());
		trnsfmr.setGeoZipCodeTrnsfmr(new GeoZipCodeDtoTransformer());
		trnsfmr.setInternetOptionsTrnsfmr(new BillInternetOptionsDtoTransformer());
		trnsfmr.setCableOptionsTrnsfmr(new BillCableOptionsDtoTransformer());
		
		List<BillDTO> dtos = new ArrayList<BillDTO>();
		for (Bill obj : objs) {
			double radius = 50;
			
			BillReportCritrDTO critr = new BillReportCritrDTO();
			critr.setLatitude(obj.getGeoZipCode().getLatitude().doubleValue());
			critr.setLongitude(obj.getGeoZipCode().getLongitude().doubleValue());
			critr.setRadius(radius);
			critr.setDistanceUnit(HaversineFormulaConsts.DISTANCE_UNIT_MILES);
//			critr.setCableService(obj.isCableService());
//			critr.setInternetService(obj.isInternetService());
//			critr.setPhoneService(obj.isPhoneService());
			
			/**
			 * look at using {@link BillDAOImpl#getBeStatsByCritr}
			 */
			BeStats stats = new BillReportDAO().getReportData(critr);
			
			BillLocationStatsDTO statsDto = new BillLocationStatsDTO();
			statsDto.setMileRadius(radius);
			statsDto.setCountOfBills(stats.getCountOfBills());
			statsDto.setCountOfZipCodes(stats.getCountOfZipCodes());
			statsDto.setAverageTotalAmount(stats.getAverageTotalAmount());
			statsDto.setHighestTotalAmount(stats.getHighestTotalAmount());
			statsDto.setLowestTotalAmount(stats.getLowestTotalAmount());
			
			BillDTO dto = trnsfmr.transform(obj);
			dto.setStats(statsDto);
			dtos.add(dto);
		}
		return dtos;
	}
	
	private BillDtoTransformer getBillDtoTrnsfmr() {
		BillDtoTransformer trnsfmr = new BillDtoTransformer();
		trnsfmr.setPrvdrTrnsfmr(new ProviderDtoTransformer());
		trnsfmr.setGeoZipCodeTrnsfmr(new GeoZipCodeDtoTransformer());
		trnsfmr.setInternetOptionsTrnsfmr(new BillInternetOptionsDtoTransformer());
		trnsfmr.setCableOptionsTrnsfmr(new BillCableOptionsDtoTransformer());
		return trnsfmr;
	}
	
	// TODO change path to /be/list/page
	@GET
	@Path("/list/page")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getBeListPage(
			@QueryParam(ReqParams.PARAM0) String critrJson,
			@QueryParam(ReqParams.PARAM1) Integer offset,
			@QueryParam(ReqParams.PARAM2) Integer limit,
			@Context HttpServletRequest httpRequest) {
		try {
			long accountId = getAuthAccountId(httpRequest);
			
			GsonBuilder gsonBldr = new GsonBuilder();
			gsonBldr.registerTypeAdapter(BigDecimal.class, new SafeBigDecimalAdptr());
			Gson gson = gsonBldr.create();
			BillSearchCritrDTO critr = gson.fromJson(critrJson, BillSearchCritrDTO.class);
			
			List<Bill> objs = DAOFactory.getInstance().getBeDAO().findBeBillsByCritr(accountId, critr, offset, limit);
			int total = DAOFactory.getInstance().getBeDAO().countBeBillsByCritr(accountId, critr);
			
			List<BillDTO> dtos = (List<BillDTO>) CollectionUtils.collect(objs, getBillDtoTrnsfmr());
			for (BillDTO dto : dtos) {
				int cnt = DAOFactory.getInstance().getDsqBillPostDAO().countByBill(dto.getId());
				dto.setDsqPostCount(cnt);
			}
			
			ResponseDTO<PagingResultDTO<BillDTO>> response = new ResponseDTO<PagingResultDTO<BillDTO>>(new PagingResultDTO<>(offset, limit, total, dtos));
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	/**
	 * http://stackoverflow.com/questions/9239117/numberformatexception-in-gson-when-converting-string-to-double
	 */
	private static class SafeBigDecimalAdptr extends TypeAdapter<BigDecimal> {
		
		@Override
		public BigDecimal read(JsonReader reader) throws IOException {
			if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
            } else {
            	String stringValue = reader.nextString();
            	if (StringUtils.isNotEmpty(stringValue)) {
            		return new BigDecimal(stringValue);
            	}
            }
			return null;
		}

		@Override
		public void write(JsonWriter arg0, BigDecimal arg1) throws IOException {
		}
	}
	
	@POST
	@Path("/save")
	@Produces({MediaType.APPLICATION_JSON})
	public Response saveBill(
			@FormParam(ReqParams.PARAM0) String billCreateJson,
			@Context HttpServletRequest httpRequest) {
		try {
			UserPrincipal principal = authenticate(httpRequest);
			
			User user = DAOFactory.getInstance().getUserDAO().findByUsername(principal.getUsername());
			
			BillCreateDTO billDto = new Gson().fromJson(billCreateJson, BillCreateDTO.class);
			
			Bill obj = new BillCreateObservable(new BillCreateObserver() {
				
				@Override
				public void create(Bill bill) {
//					try {
//						Email email = BillHelper.buildBillCreateEmail(httpRequest, user, bill);
//						Emailer.sendEmail(email);
//					} catch (Exception e) {
//						// TODO record this as a email to be sent so a 2nd attempt to send can be made
//						log.error(e);
//					}
				}
			}).createBill(user.getAccount(), billDto);
			
			BillDTO dto = getBillDtoTrnsfmr().transform(obj);
			
			ResponseDTO<BillDTO> response = new ResponseDTO<BillDTO>(dto);
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	@GET
	@Path("/current")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getCurrentBill(
			@Context HttpServletRequest httpRequest) {
		try {
			long accountId = getAuthAccountId(httpRequest);
			
			ResponseDTO<BillDTO> response = null;
			
			Bill obj = DAOFactory.getInstance().getBillDAO().findCurrentBill(accountId);
			if (obj != null) {
				BillDTO dto = getBillDtoTrnsfmr().transform(obj);
				response = new ResponseDTO<BillDTO>(dto);
			}
			
			if (response == null) {
				response = new ResponseDTO<BillDTO>(ResponseDTO.RESULT_OK);
			}
			
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	@GET
	@Path("/{billId}/detail")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getBillDetail(
			@PathParam("billId") Long billId,
			@Context HttpServletRequest httpRequest) {
		try {
			getAuthAccountId(httpRequest);
			
			ResponseDTO<BillDTO> response = null;
			
			Bill obj = DAOFactory.getInstance().getBillDAO().findById(billId, true);
			if (obj != null) {
				BillDTO dto = getBillDtoTrnsfmr().transform(obj);
				response = new ResponseDTO<BillDTO>(dto);
			}
			
			if (response == null) {
				response = new ResponseDTO<BillDTO>(ResponseDTO.RESULT_OK);
			}
			
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	@POST
	@Path("/{billId}/delete")
	@Produces({MediaType.APPLICATION_JSON})
	public Response deleteBill(
			@PathParam("billId") Long billId,
			@Context HttpServletRequest httpRequest) {
		try {
			authenticate(httpRequest);
			
			// TODO create facade to validate user has permission to delete bill
			Bill bill = DAOFactory.getInstance().getBillDAO().findById(billId, false);
			
			HibernateUtil.getSessionFactory().getCurrentSession().delete(bill);
			
			ResponseDTO<Void> response = new ResponseDTO<Void>(ResponseDTO.RESULT_OK);
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
//	@GET
//	@Path("/explorer/results")
//	@Produces({MediaType.APPLICATION_JSON})
//	public Response getBillExplorerResults(
//			@QueryParam(ReqParams.PARAM0) String zipCode,
//			@QueryParam(ReqParams.PARAM1) double radius,
//			@Context HttpServletRequest httpRequest) {
//		try {
//			long accountId = getAuthAccountId(httpRequest);
//			
//			GeoZipCode geoZipCode = null;
//			if (StringUtils.isNotEmpty(zipCode)) {
//				List<GeoZipCode> zipCodes = DAOFactory.getInstance().getGeoZipCodeDAO().findByZipCode(zipCode);
//				geoZipCode = zipCodes.get(0);
//			} else {
//				Bill obj = DAOFactory.getInstance().getBillDAO().findLatestBill(accountId, true);
//				geoZipCode = obj.getGeoZipCode();
//			}
//			
//			if (geoZipCode == null) {
//				// TODO throw exception
//			}
//			
//			BillReportCritrDTO critr = new BillReportCritrDTO();
//			critr.setLatitude(geoZipCode.getLatitude().doubleValue());
//			critr.setLongitude(geoZipCode.getLongitude().doubleValue());
//			critr.setRadius(radius);
//			critr.setDistanceUnit(HaversineFormulaConsts.DISTANCE_UNIT_MILES);
//			
//			BillExplorerStats stats = new BillReportDAO().getReportData(critr);
//			
//			BillExplorerStatsDTO dto = new BillExplorerStatsDTO();
//			dto.setCountOfBills(stats.getCountOfBills());
//			dto.setCountOfZipCodes(stats.getCountOfZipCodes());
//			dto.setHighestTotalAmount(stats.getHighestTotalAmount().toString());
//			dto.setAverageTotalAmount(stats.getAverageTotalAmount().toString());
//			dto.setLowestTotalAmount(stats.getLowestTotalAmount().toString());
//			
//			ResponseDTO<BillExplorerStatsDTO> response = new ResponseDTO<BillExplorerStatsDTO>(dto);
//			String json = new Gson().toJson(response);
//			
//			return Response.ok(json, MediaType.APPLICATION_JSON).build();
//		} catch (AuthenticationException e) {
//			return Response.status(Response.Status.UNAUTHORIZED).build();
//		}
//	}
	
	// TODO change path to /be/stats
	@GET
	@Path("/explorer/stats")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getBeStats(
			@QueryParam(ReqParams.PARAM0) String critrJson,
			@Context HttpServletRequest httpRequest) {
		try {
			long accountId = getAuthAccountId(httpRequest);
			
			BillSearchCritrDTO critr = new Gson().fromJson(critrJson, BillSearchCritrDTO.class);
			
			BeStats obj = new BeDAOImpl().getBeStatsByCritr(accountId, critr);
			
			BeStatsDTO dto = new BeStatsDTO();
			dto.setCountOfBills(obj.getCountOfBills());
			dto.setCountOfZipCodes(obj.getCountOfZipCodes());
			dto.setHighestTotalAmount(obj.getHighestTotalAmount() != null ? obj.getHighestTotalAmount().toString() : "");
			dto.setAverageTotalAmount(obj.getAverageTotalAmount() != null ? obj.getAverageTotalAmount().toString() : "");
			dto.setLowestTotalAmount(obj.getLowestTotalAmount() != null ? obj.getLowestTotalAmount().toString() : "");
			
			ResponseDTO<BeStatsDTO> response = new ResponseDTO<BeStatsDTO>(dto);
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
