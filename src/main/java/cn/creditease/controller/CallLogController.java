package cn.creditease.controller;

import cn.creditease.entity.CallLog;
import cn.creditease.service.ICallLogService;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("db")
public class CallLogController {
    @Autowired
    private ICallLogService callLogService;

    @GetMapping("calllog/{uuid}")
    public ResponseEntity<List<CallLog>> getCallLogByUuid(@PathVariable("uuid") String uuid) {
        List<CallLog> callLogList = callLogService.getCallLogByUuid(uuid);
        return new ResponseEntity<List<CallLog>>(callLogList, HttpStatus.OK);
    }

    @GetMapping("calllog")
    public ResponseEntity<List<CallLog>> getAllCallLog() {
        List<CallLog> list = callLogService.getAllCallLog();
        return new ResponseEntity<List<CallLog>>(list, HttpStatus.OK);
    }

    @PostMapping("calllog")
    public ResponseEntity<Void> addCallLog(@RequestBody CallLog callLog) {
        callLogService.addCallLog(callLog);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
