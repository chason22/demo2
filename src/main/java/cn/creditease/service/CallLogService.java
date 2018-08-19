package cn.creditease.service;

import cn.creditease.dao.ICallLogDAO;
import cn.creditease.entity.CallLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallLogService implements ICallLogService {
    @Autowired
    private ICallLogDAO callLogDAO;
    @Override
    public List<CallLog> getAllCallLog() {
        return callLogDAO.getAllCallLog();
    }

    @Override
    public List<CallLog> getCallLogByUuid(String uuid) {
        return callLogDAO.getCallLogByUuid(uuid);
    }

    @Override
    public void addCallLog(CallLog callLog) {
        callLogDAO.addCallLog(callLog);
    }
}
