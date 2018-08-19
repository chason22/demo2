package cn.creditease.dao;

import cn.creditease.entity.CallLog;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ICallLogDAO {
    List<CallLog> getAllCallLog();
    List<CallLog> getCallLogByUuid(String uuid);
    void addCallLog(CallLog callLog);
    void updateCallLog(CallLog callLog);
}
