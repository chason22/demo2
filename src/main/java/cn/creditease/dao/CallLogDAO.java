package cn.creditease.dao;

import cn.creditease.entity.CallLog;
import cn.creditease.entity.CallLogRowMapper;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class CallLogDAO implements ICallLogDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CallLog> getAllCallLog() {
        String sql = "SELECT * FROM call_record";
        RowMapper<CallLog> rowMapper = new BeanPropertyRowMapper<CallLog>(CallLog.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<CallLog> getCallLogByUuid(String uuid) {
        String sql = "SELECT * FROM call_record WHERE uuid = ?";
        RowMapper<CallLog> rowMapper = new CallLogRowMapper();
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql, new Object[]{uuid}, rowMapper);
        List<CallLog> rs = new ArrayList<CallLog>();
        for(Map item: ls){
            CallLog cl = new CallLog();
            cl.setCreatedAt((Timestamp) item.get("createdAt"));
            cl.setDuration((Integer) item.get("duration"));
            cl.setDeviceId((String) item.get("deviceId"));
            cl.setPhone((String) item.get("phone"));
            cl.setCallLogId((String) item.get("callLogId"));
            cl.setCallTime((Timestamp) item.get("callTime"));
            cl.setCallType((Integer) item.get("callType"));
            cl.setUuid((String) item.get("uuid"));
            rs.add(cl);
        }
        return rs;
    }

    @Override
    public void addCallLog(CallLog callLog) {
        String sql = "INSERT INTO call_record (uuid, callType, callTime, callLogId, phone, deviceId, duration, createdAt) values (?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println("phone: " + callLog.getPhone());
        jdbcTemplate.update(sql, callLog.getUuid(), callLog.getCallType(), callLog.getCallTime(), callLog.getCallLogId(), callLog.getPhone(), callLog.getDeviceId(), callLog.getDuration(), callLog.getCreatedAt());
    }

    @Override
    public void updateCallLog(CallLog callLog) {

    }
}
