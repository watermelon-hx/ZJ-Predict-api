package io.renren.modules.match.service.impl;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class msgTest {

    public static void main(String[] args) {



        String content ="测试消息内容" + new Date();
        StringBuffer sb = new StringBuffer();
        String competitionName = "阿青联";
        String homeTeamName = "男生队U20";
        String awayTeamName = "艾度思维U20";
        String sendTime = "21分钟";
        String curScore = "0:0";
        String rule = "上半场0.5大";
//        String rule = "全场大";
        String summary = competitionName + "：" + homeTeamName + " VS " + awayTeamName;
        sb.append(competitionName + System.getProperty("line.separator"));
        sb.append(homeTeamName + " VS " + awayTeamName + System.getProperty("line.separator"));
//        Float a = Float.valueOf(dto.getHomeScores() + dto.getAwayScores()) + 0.5f;
//        sb.append("推荐: 大 " + a + System.getProperty("line.separator"));
        sb.append("推荐时间：" + sendTime + System.getProperty("line.separator"));
        sb.append("当前比分：" + curScore);
        sb.append("满足：" + rule + System.getProperty("line.separator"));

        List<String> UIDS = new ArrayList<>();
//        UIDS.add("UID_77XJK9xQMKf5dizw8Gmp82Tgl2AE");
//        UIDS.add("UID_73AnqE0bawZQPef9M6EMmCuyJaNQ");

        UIDS.add("UID_DuLmBRC1Ec6KDqpBqzYGFHYLIU4c");  //韩旭
        UIDS.add("UID_93EqCrB0jAtbrnKK2NWgTCOrGViR");  //人间山河晚
        UIDS.add("UID_v4UdNlzZQxUC8XNQMOsZKLietoTe");  //冯鑫
        UIDS.add("UID_AAQMe13w7HfVGC0cnd9MbWM2y81A");  //言飞
        UIDS.add("UID_73AnqE0bawZQPef9M6EMmCuyJaNQ");  //来球乐


        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://wxpusher.zjiecode.com/api/send/message");
        String result = "";
        HttpResponse res = null;
        try {
            org.json.JSONObject object = new org.json.JSONObject();
            object.put("appToken", "AT_sjuJNPwbnJNardEtKqwLuCp5JihyCcLY");
            object.put("summary", summary);
            object.put("content", sb.toString());
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
