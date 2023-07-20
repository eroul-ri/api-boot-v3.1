package com.eroulri.api.common.handler.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import com.eroulri.api.contant.MEMBER_TYPE;

@MappedTypes(MEMBER_TYPE.class)
public class MemberTypeHandler implements TypeHandler<MEMBER_TYPE> {
	@Override
	public void setParameter(PreparedStatement ps, int i, MEMBER_TYPE parameter, JdbcType jdbcType) throws
		SQLException {
		ps.setString(i, parameter.getCode());
	}

	@Override
	public MEMBER_TYPE getResult(ResultSet rs, String columnName) throws SQLException {
		return of(rs.getString(columnName));
	}

	@Override
	public MEMBER_TYPE getResult(ResultSet rs, int columnIndex) throws SQLException {
		return of(rs.getString(columnIndex));
	}

	@Override
	public MEMBER_TYPE getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return of(cs.getString(columnIndex));
	}

	private MEMBER_TYPE of(String code) {
		return Arrays.stream(MEMBER_TYPE.values())
					 .filter(memberType -> memberType.getCode().equals(code))
					 .findFirst()
					 .get();
	}
}
