package cn.creditease.service;

import cn.creditease.entity.CallLog;

import java.util.List;

public interface ICallLogService {
    List<CallLog> getAllCallLog();
    List<CallLog> getCallLogByUuid(String uuid);
    void addCallLog(CallLog callLog);
}
