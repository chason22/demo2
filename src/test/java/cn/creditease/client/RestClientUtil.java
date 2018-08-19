package cn.creditease.client;

import cn.creditease.entity.Article;
import cn.creditease.entity.CallLog;
import cn.creditease.entity.User;
import cn.creditease.entity.UserCallLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.assertj.core.util.Maps;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RestClientUtil {
    public void getArticleByIdDemo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/user/article/{id}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Article> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Article.class, 1);
        Article article = responseEntity.getBody();
        System.out.println("Id:"+article.getArticleId()+", Title:"+article.getTitle()
                +", Category:"+article.getCategory());
    }
    public void getAllArticlesDemo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/user/articles";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Article[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Article[].class);
        Article[] articles = responseEntity.getBody();
        for(Article article : articles) {
            System.out.println("Id:"+article.getArticleId()+", Title:"+article.getTitle()
                    +", Category: "+article.getCategory());
        }
    }
    public void addArticleDemo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/user/article";
        Article objArticle = new Article();
        objArticle.setTitle("12 Spring REST Security using Hibernate");
        objArticle.setCategory("Spring");
        HttpEntity<Article> requestEntity = new HttpEntity<Article>(objArticle, headers);
        URI uri = restTemplate.postForLocation(url, requestEntity);
        System.out.println(uri.getPath());
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


    public void updateArticleDemo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/user/article";
        Article objArticle = new Article();
        objArticle.setArticleId(1);
        objArticle.setTitle("Update:Java Concurrency");
        objArticle.setCategory("Java");
        HttpEntity<Article> requestEntity = new HttpEntity<Article>(objArticle, headers);
        restTemplate.put(url, requestEntity);
    }
    public void deleteArticleDemo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/user/article/{id}";
        HttpEntity<Article> requestEntity = new HttpEntity<Article>(headers);
        restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, 1);
    }

    public static void main(String args[]) throws Exception {
        RestClientUtil util = new RestClientUtil();
//        //util.getArticleByIdDemo();
//        util.addArticleDemo();
//        //util.updateArticleDemo();
//        //util.deleteArticleDemo();
//        util.getAllArticlesDemo();
//        Gson gs = new Gson();
//        User ur = new User("hcc", "bj");
//        String str = gs.toJson(ur);
//        User u1 = (User) gs.fromJson(str, User.class);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String nowTime = "2018-03-22 21:25:33";
//        Date dt = new Date(sdf.parse(nowTime).getTime());

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
