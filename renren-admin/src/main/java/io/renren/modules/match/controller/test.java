package io.renren.modules.match.controller;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class test {

    public static void main(String[] args) {

        String summary = "测试消息 "+new Date();
        String content = "此条为测试消息";
        List<String> UIDS = new ArrayList<>();
        UIDS.add("UID_XEZVDJpfb1VcgQMnLZ3TiZlLFMAE");
//        UIDS.add("UID_DuLmBRC1Ec6KDqpBqzYGFHYLIU4c");
//        UIDS.add("UID_v4UdNlzZQxUC8XNQMOsZKLietoTe");
//        UIDS.add("UID_93EqCrB0jAtbrnKK2NWgTCOrGViR");


        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://wxpusher.zjiecode.com/api/send/message");
        String result = "";
        HttpResponse res = null;
        try {
            org.json.JSONObject object = new org.json.JSONObject();
            object.put("appToken", "AT_sjuJNPwbnJNardEtKqwLuCp5JihyCcLY");
            object.put("summary", summary);
            object.put("content", content);
            object.put("contentType", 1);
            object.put("uids", UIDS);
            StringEntity s = new StringEntity(object.toString(), "UTF-8");
            s.setContentType("application/json");
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json; charset=utf-8");
            post.setEntity(s);
            res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(res.getEntity());
            }
        } catch (Exception e) {
            if (res == null) {
            }
            throw new RuntimeException(e);
        }
        if (res == null || res.getStatusLine() == null) {
        }

    }
}
