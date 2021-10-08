package com.bhavath.tracker.data.repos;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.bhavath.tracker.utils.DeviceCommunicationSummaryVO;
import com.bhavath.tracker.utils.DeviceCommunicationVehicleModeSummaryVO;
import com.bhavath.tracker.utils.SummaryActionEnum;

@SuppressWarnings("deprecation")
@Repository
public class DeviceCommunicationSummarySQLRepository {

	@PersistenceContext
	private EntityManager manager;

	public List<DeviceCommunicationSummaryVO> readSummary(String[] actions) {
		DeviceCommunicationSummaryVO deviceCommunicationSummaryVO = null;
		List<DeviceCommunicationSummaryVO> listOfDeviceCommunicationSummaries = new ArrayList<>();
		for (String action : actions) {
			StringBuilder sql = new StringBuilder();
			sql.append("select A.status As status, A.count As count FROM (select CASE ");
			SummaryActionEnum summaryActionEnum = SummaryActionEnum.valueOf(action);
			if (!ObjectUtils.isEmpty(summaryActionEnum)) {
				String offStatus = summaryActionEnum.getStatus().split(",")[0];
				String onStatus = summaryActionEnum.getStatus().split(",")[1];
				sql.append(" WHEN " + summaryActionEnum.getColumnName() + summaryActionEnum.getValues().split(",")[0]
						+ " THEN '" + offStatus + "'");
				sql.append(" WHEN " + summaryActionEnum.getColumnName() + summaryActionEnum.getValues().split(",")[1]
						+ " THEN '" + onStatus + "'");

				sql.append(" END As status ");
				sql.append("from device_communication) A WHERE A.status <> 'Other'");
				sql.append("Group BY A.status");
				deviceCommunicationSummaryVO = new DeviceCommunicationSummaryVO();
				Session session = (Session) manager.getDelegate();
				Query query = session.createSQLQuery(sql.toString());

				query.setHint("org.hibernate.cacheable", Boolean.TRUE);
				List<Object[]> rows = query.list();
				deviceCommunicationSummaryVO.setAction(action);

				try {
					String status = null;
					BigInteger on = BigInteger.ZERO;
					BigInteger off = BigInteger.ZERO;
					if (rows.size() > 0) {
						status = (String) rows.get(0)[0];
						if (offStatus.equals(status)) {
							off = (BigInteger) rows.get(0)[1];
						}
						if (onStatus.equals(status)) {
							on = (BigInteger) rows.get(0)[1];
						}
						if (rows.size() == 2) {
							status = (String) rows.get(1)[0];
							if (offStatus.equals(status)) {
								off = (BigInteger) rows.get(1)[1];
							}
							if (onStatus.equals(status)) {
								on = (BigInteger) rows.get(1)[1];
							}
						}
						BigInteger total = on.add(off);
						deviceCommunicationSummaryVO.setOn(on);
						deviceCommunicationSummaryVO.setOff(off);
						deviceCommunicationSummaryVO.setTotal(total);
						listOfDeviceCommunicationSummaries.add(deviceCommunicationSummaryVO);
					} else {
						BigInteger total = on.add(off);
						deviceCommunicationSummaryVO.setOn(on);
						deviceCommunicationSummaryVO.setOff(off);
						deviceCommunicationSummaryVO.setTotal(total);
						listOfDeviceCommunicationSummaries.add(deviceCommunicationSummaryVO);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return listOfDeviceCommunicationSummaries;
	}

	public List<DeviceCommunicationVehicleModeSummaryVO> readVehicleModeSummary() {
		List<DeviceCommunicationVehicleModeSummaryVO> listOfDeviceCommunicationSummaries = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("select A.status As status, A.count As count FROM (select CASE ");
		sql.append(" WHEN vehicle_mode = 'PARKING MODE' THEN 'Parking' ");
		sql.append(" WHEN vehicle_mode = 'IDLE MODE' THEN 'Idle' ");
		sql.append(" WHEN vehicle_mode = 'SLEEP MODE' THEN 'Sleep' ");
		sql.append(" WHEN vehicle_mode = 'RUNNING MODE' THEN 'Running' ");
		sql.append(" END As status from device_communication) A ");
		sql.append(" WHERE A.status <> '[null]' ");
		sql.append(" Group BY A.status");

		Session session = (Session) manager.getDelegate();
		Query query = session.createSQLQuery(sql.toString());

		query.setHint("org.hibernate.cacheable", Boolean.TRUE);
		List<Object[]> rows = query.list();
		try {
			if (rows.size() > 0) {
				for (int loop = 0; loop < rows.size(); loop++) {
					DeviceCommunicationVehicleModeSummaryVO deviceCommunicationVehicleModeSummaryVO = new DeviceCommunicationVehicleModeSummaryVO();
					String status = (String) rows.get(loop)[0];
					deviceCommunicationVehicleModeSummaryVO.setStatus(status);
					BigInteger count = (BigInteger) rows.get(loop)[1];
					deviceCommunicationVehicleModeSummaryVO.setCount(count);
					listOfDeviceCommunicationSummaries.add(deviceCommunicationVehicleModeSummaryVO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listOfDeviceCommunicationSummaries;
	}

}
