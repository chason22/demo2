package cn.creditease.client;

import cn.creditease.entity.CallLog;
import cn.creditease.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RestClientUtil {

    public void getUserByUuid(String uuid) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/db/user/{uuid}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<User> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, User.class, 1);
        User user = responseEntity.getBody();
        System.out.println("uuid:"+user.getUuid()+", Name:"+user.getName());
    }

    public void getAllCallLogs() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/db/calllog";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<CallLog[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, CallLog[].class);
        CallLog[] callLogs = responseEntity.getBody();
        for(CallLog callLog : callLogs) {
            System.out.println("uuid:"+callLog.getUuid()+", createdAt:"+callLog.getCreatedAt());
        }
    }

    public void addCallLog(CallLog callLog){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/db/calllog";
        HttpEntity<CallLog> requestEntity = new HttpEntity<CallLog>(callLog, headers);
        URI uri = restTemplate.postForLocation(url, requestEntity);
    }

    public void addUser(User user){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/db/user";
        HttpEntity<User> requestEntity = new HttpEntity<User>(user, headers);
        URI uri = restTemplate.postForLocation(url, requestEntity);
    }

    public static void main(String args[]) throws Exception {
        RestClientUtil util = new RestClientUtil();
//        util.getAllCallLogs();
//        util.getUserByUuid("1");
        try {
            util.loadUser2DB("/Users/hcc/Downloads/bootcamp/BootCamp_users_info.csv");
            util.loadCallLog2DB("/Users/hcc/Downloads/bootcamp/BootCamp_call_log.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUser2DB(String filepath) throws IOException {
        List<User> users = readCSV(filepath);
        for(User user: users){
            addUser(user);
        }
    }

    public void loadCallLog2DB(String filePath) throws IOException {
        // read file content from file
        StringBuffer sb= new StringBuffer("");
        FileReader reader = null;
        BufferedReader br = null;

        try {
            reader = new FileReader(filePath);
            br = new BufferedReader(reader);
            String str = null;
            Gson gs=  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            CallLog cg = new CallLog();
            //int ct = 0;
            while((str = br.readLine()) != null) {
                UserCallLog ucl = (UserCallLog) gs.fromJson(str, UserCallLog.class);
                List<CallLog> callLogList = new ArrayList<CallLog>();
                for(UserCallLog.CallLog cl: ucl.getCall_log()){

                    cg.setCallType(cl.getCallType());
                    cg.setCallTime(cl.getCallTime());
                    cg.setCallLogId(cl.getCallLogId());
                    cg.setPhone(cl.getPhone());
                    cg.setUuid(ucl.getUuid());
                    cg.setCreatedAt(cl.getCreatedAt());
                    cg.setDuration(cl.getDuration());
                    cg.setDeviceId(cl.getDeviceId());
                    addCallLog(cg);
                }
//                ct++;
//                if(ct == 10) break;
                System.out.println(str);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            br.close();
            reader.close();
        }

    }

    public List<User> readCSV(String filepath) throws IOException {

        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(filepath));
            ((BufferedReader) reader).readLine();//跳过表头这一行
        } catch (IOException e) {
            e.printStackTrace();
        }

        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withHeader("uuid", "created_at", "phone", "name", "id_no")//表头
                    .withIgnoreHeaderCase()
                    .withTrim());
        List<User> users = new ArrayList<User>();
        for (CSVRecord csvRecord : csvParser) {
            // Accessing values by the names assigned to each column
            User us = new User();
            us.setUuid(csvRecord.get("uuid"));
            us.setCreated_at(Timestamp.valueOf(csvRecord.get("created_at")));
            us.setPhone(csvRecord.get("phone"));
            us.setName(csvRecord.get("name"));
            us.setId_no(csvRecord.get("id_no"));
            users.add(us);
        }
        return users;
    }

}
