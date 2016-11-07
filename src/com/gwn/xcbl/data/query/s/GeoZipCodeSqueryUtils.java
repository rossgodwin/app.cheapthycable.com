package com.gwn.xcbl.data.query.s;

import com.gwn.xcbl.data.entity.GeoZipCodeTableMetadata;
import com.gwn.xcbl.data.model.HaversineFormulaCritr;

public class GeoZipCodeSqueryUtils {

	public static String buildHaversineTable(HaversineFormulaCritr critr) {
		StringBuilder qsb = new StringBuilder();
		
		qsb.append("select");
		{
			qsb.append(" z.").append(GeoZipCodeTableMetadata.COL_ID).append(" as zip_code_id,");
			qsb.append(" p.radius as radius").append(",");
			qsb.append(" p.distance_unit * DEGREES(ACOS(COS(RADIANS(p.latpoint)) * COS(RADIANS(z.").append(GeoZipCodeTableMetadata.COL_LATITUDE).append(")) * COS(RADIANS(p.longpoint - z.").append(GeoZipCodeTableMetadata.COL_LONGITUDE).append(")) + SIN(RADIANS(p.latpoint)) * SIN(RADIANS(z.").append(GeoZipCodeTableMetadata.COL_LATITUDE).append(")))) AS distance");
		}
		
		qsb.append(" from ").append(GeoZipCodeTableMetadata.TABLE_NAME).append(" as z join (");
		{
			qsb.append("select ").append(critr.getLatitude()).append(" as latPoint");
			qsb.append(", ").append(critr.getLongitude()).append(" as longPoint");
			qsb.append(", ").append(critr.getRadius()).append(" as radius");
			qsb.append(", ").append(critr.getDistanceUnit()).append(" as distance_unit");
		}
		qsb.append(") as p on 1 = 1");
		
		qsb.append(" where");
		{
			qsb.append(" z.").append(GeoZipCodeTableMetadata.COL_LATITUDE).append(" between p.latPoint - (p.radius / p.distance_unit) and p.latPoint + (p.radius / p.distance_unit)");
			qsb.append(" and z.").append(GeoZipCodeTableMetadata.COL_LONGITUDE).append(" between p.longpoint - (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint)))) and p.longpoint + (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))");
		}
		
		return qsb.toString();
	}
}
