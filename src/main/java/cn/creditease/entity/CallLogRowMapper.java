package cn.creditease.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CallLogRowMapper implements RowMapper<CallLog> {
    @Override
    public CallLog mapRow(ResultSet row, int rowNum) throws SQLException {
        CallLog callLog = new CallLog();
        callLog.setCallType(row.getInt("callType"));
        callLog.setCallTime(row.getTimestamp("callTime"));
        callLog.setCallLogId(row.getString("callLogId"));
        callLog.setPhone(row.getString("phone"));
        callLog.setDeviceId(row.getString("deviceId"));
        callLog.setDuration(row.getInt("duration"));
        callLog.setCreatedAt(row.getTimestamp("createdAt"));
        return callLog;
    }
}
