package io.renren.modules.match.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.match.dto.DuixianMatchDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

public class tets {

    private static final String url_70 = "http://api.dui-xian.com:8002/api/oddsindex/minfo/duixian_history/last_10";
    private static final String param_70 = "{\"code\":\"554ddce960039c20a7a4fcd4190d8090\",\"uid\":1619}";
    private static final String url_half = "http://api.dui-xian.com:8002/api/oddsindex/minfo/duixian_history/last_10";
    private static final String param_half = "{\"code\":\"9443a9d982bb1a7a3580998d33a39b16\",\"uid\":1619}";

    private static String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTUwMTY0NzAsImlhdCI6MTY5NDkzMDA3MCwiZGF0YSI6eyJ1c2VybmFtZSI6IjE1OTI4NjUxMDI0In19.oC_vChpPtdXqE9o29xVfWQ-rcyPi0xNXTtO1b4NOJfc";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args) throws IOException {
        StringBuffer buffer = new StringBuffer();
        OutputStreamWriter out;
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url_70);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            //设置超时时间
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(15000);
            //设置请求属性
            conn.setRequestMethod("POST");
            conn.addRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Authorization", "Token " + token);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");// utf-8编码
            // 发送请求参数
            out.write(param_70);

            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf8"));
            String line;
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            JSONObject jsonObject = JSONObject.parseObject(buffer.toString());
            System.err.println(jsonObject);
            DuixianMatchDTO latest = new DuixianMatchDTO();
            JSONObject match = jsonObject.getJSONArray("data").getJSONObject(0);
//            latest.setIs_running((Integer) match.get("is_running"));
//            latest.setDUIXIAN_ID(match.getLong("DUIXIAN_ID"));
//            latest.setKEY(match.getString("KEY"));
//            JSONObject NCN = match.getJSONObject("NCN");
//            latest.setLID(NCN.getString("LID"));
//            latest.setLEAGUE(NCN.getString("LEAGUE"));
//            latest.setTEAM_H_ID(NCN.getString("TEAM_H_ID"));
//            latest.setTEAM_H(NCN.getString("TEAM_H"));
//            latest.setTEAM_C_ID(NCN.getString("TEAM_C_ID"));
//            latest.setTEAM_C(NCN.getString("TEAM_C"));
//            latest.setDATE(sdf.parse(NCN.getString("DATE")));
//            latest.setSCORE(NCN.getString("SCORE"));
//            latest.setTIMER(NCN.getInteger("TIMER"));
            System.err.println(latest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
