package cn.creditease.client;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class UserCallLog {

    public class CallLog{

        private int callType;
        private Timestamp callTime;
        private String callLogId;
        private String phone;
        private String deviceId;
        private int duration;
        private Timestamp createdAt;


        public int getCallType() {
            return callType;
        }

        public void setCallType(int callType) {
            this.callType = callType;
        }

        public Timestamp getCallTime() {
            return callTime;
        }

        public void setCallTime(Timestamp callTime) {
            this.callTime = callTime;
        }

        public String getCallLogId() {
            return callLogId;
        }

        public void setCallLogId(String callLogId) {
            this.callLogId = callLogId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public Timestamp getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Timestamp createdAt) {
            this.createdAt = createdAt;
        }
    }

    private List<CallLog> call_log;
    private String uuid;

    public List<CallLog> getCall_log() {
        return call_log;
    }

    public void setCall_log(List<CallLog> call_log) {
        this.call_log = call_log;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
