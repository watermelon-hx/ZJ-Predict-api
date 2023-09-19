package io.renren.modules.match.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.context.TenantContext;
import io.renren.common.page.PageData;
import io.renren.common.service.impl.CrudServiceImpl;
import io.renren.modules.WxUser.dao.WxUserDao;
import io.renren.modules.WxUser.dto.WxUserDTO;
import io.renren.modules.WxUser.service.WxUserService;
import io.renren.modules.match.dao.DuixianMatchDao;
import io.renren.modules.match.dao.MatchScheduleDao;
import io.renren.modules.match.dto.MatchScheduleDTO;
import io.renren.modules.match.dto.resultDto;
import io.renren.modules.match.dto.DuixianMatchDTO;
import io.renren.modules.match.entity.DuixianMatchEntity;
import io.renren.modules.match.entity.MatchScheduleEntity;
import io.renren.modules.match.service.MatchScheduleService;
import io.renren.modules.matchContent.dto.MatchContentDTO;
import io.renren.modules.matchContent.service.MatchContentService;
import io.renren.modules.security.user.SecurityUser;
import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日程
 *
 * @author sss
 * @since 3.0 2023-03-04
 */
@Service
public class MatchScheduleServiceImpl extends CrudServiceImpl<DuixianMatchDao, DuixianMatchEntity, DuixianMatchDTO> implements MatchScheduleService {


    private static final String user = "jxkj";

    private static final String secret = "t80b7b170be597a35729639fa00b27b9";

    private static String home_team_ID_70 = "";
    private static String away_team_ID_70 = "";

    private static String home_team_ID_half = "";
    private static String away_team_ID_half = "";

    private static String token = "";

    private static final String url_70 = "http://api.dui-xian.com:8002/api/oddsindex/minfo/duixian_history/last_10";
    private static final String param_70 = "{\"code\":\"554ddce960039c20a7a4fcd4190d8090\",\"uid\":1545}";
    private static final String url_half = "http://api.dui-xian.com:8002/api/oddsindex/minfo/duixian_history/last_10";
    private static final String param_half = "{\"code\":\"9443a9d982bb1a7a3580998d33a39b16\",\"uid\":1545}";

    private static final String url_checkResult = "http://api.dui-xian.com:8002/api/oddsindex/minfo/list/record";
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String orderId = "O23091914175478673953" ;
    public static String pwd = "b10bf3ea74c14b8da1004eb03e35aca2" ;
    public static String proxyHost = "flow.hailiangip.com" ;
    public static int proxyPort = 14223;

    private static String dynamicIP;

    private static Long beginTime;

    /**
     * 提取ip接口链接
     */
    public static String apiUrl = "http://api.hailiangip.com:8422/api/getIp";
    /**
     * 目标url
     */
    private static String destUrl = "http://api.hailiangip.com:8422/api/myIp";

    /**
     * 提取ip协议类型 1:http/https 2:socks5
     */
    public static int type = 1;

    /**
     * 提取个数 min:1  max:200
     */
    public static int num = 1;

    /**
     * 解绑时长
     */
    public static int unbindTime = 600;

    /**
     * 返回数据格式 0:json  1:txt  2:html
     */
    public static int dataType = 0;

    private static int retryTimes = 5;

//    private static final String date = "20230716";

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private MatchContentService matchContentService;





    public void realTimeStatistics() throws Exception {
        if(retryTimes <= 0){
            retryTimes = 5;
            dynamicIP = null;
            token = null;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String strSentDate = format.format(new Date());
            List<WxUserDTO> list2 = this.wxUserService.getList(strSentDate);
            ArrayList<String> users = new ArrayList<>();
            if(list2 != null){
                for (WxUserDTO wx : list2) {
                    users.add(wx.getOpenId());
                }
            }
//            users.clear();
//            users.add("UID_DuLmBRC1Ec6KDqpBqzYGFHYLIU4c");
//        users.add("UID_v4UdNlzZQxUC8XNQMOsZKLietoTe");
//        users.add("UID_93EqCrB0jAtbrnKK2NWgTCOrGViR");
//        users.add("UID_AAQMe13w7HfVGC0cnd9MbWM2y81A");


            //获取动态ip
            if(dynamicIP == null){
                dynamicIP = getdynamicIP();
                beginTime = System.currentTimeMillis();
                token = null;
            }
            if(System.currentTimeMillis() - beginTime > 10 * 60 * 1000){
                //超时，重新获取
                dynamicIP = getdynamicIP();
                beginTime = System.currentTimeMillis();
                token = null;
            }
            if(dynamicIP == null){
                return;
            }
            System.err.println("------------------------ip时长剩余" + (600-(System.currentTimeMillis() - beginTime)/1000) + "秒 -------------------------------------");
            System.err.println("------------------------开始获取对线数据-------------------------------------");
            System.err.println("------------------------当前重试次数" + (5-retryTimes) +"-------------------------------------");
            //登录取token
            if(token == null || token.equals("")){
                token = loginDuixian();
                if(token.equals("error code")){
                    dynamicIP = null;
                    token = null;
                    return;
                }
                System.out.println("token=" + token);
            }

            if(token == null || token.equals("")){
                retryTimes -= 1;
                return;
            }
            //根据token获取70分钟推荐最新数据及上半场推荐最新数据
            DuixianMatchDTO match_latest_70 = postDuixianData(url_70,param_70);
            System.err.println("当前最新比赛-满足70分钟后有球：");
            System.err.println(match_latest_70);
            if(match_latest_70 == null){
                retryTimes -= 1;
            }else {
                //若是推出的新比赛
                if(match_latest_70.getTeamHomeId() != null && match_latest_70.getTeamAwayId() != null &&
                        !match_latest_70.getTeamHomeId().equals(home_team_ID_70) && !match_latest_70.getTeamAwayId().equals(away_team_ID_70)){
                    boolean init = false;
                    if(home_team_ID_70.equals("") && away_team_ID_70.equals("")){
                        init = true;
                    }
                    home_team_ID_70 = match_latest_70.getTeamHomeId();
                    away_team_ID_70 = match_latest_70.getTeamAwayId();

                    //推出该比赛
                    int totalscore = Integer.parseInt(match_latest_70.getScore().split(":")[0]) + Integer.parseInt(match_latest_70.getScore().split(":")[1]);
                    String summary = match_latest_70.getLeague() + ": " + match_latest_70.getTeamHomeName() + " VS " + match_latest_70.getTeamAwayName() + " 全场" + (totalscore+0.5) + "大";
                    StringBuffer content = new StringBuffer();
                    content.append(match_latest_70.getLeague() + System.getProperty("line.separator"));
                    content.append(match_latest_70.getTeamHomeName() + " VS " + match_latest_70.getTeamAwayName() + System.getProperty("line.separator"));
                    content.append("当前比分：" + match_latest_70.getScore() + System.getProperty("line.separator"));
                    content.append("推荐时间：" + match_latest_70.getSendTime() + "分钟" + System.getProperty("line.separator"));
                    content.append("预测：全场" + (totalscore + 0.5) + "大"+ System.getProperty("line.separator"));
                    content.append(System.getProperty("line.separator"));
                    content.append(System.getProperty("line.separator"));
                    content.append("温馨提示：彩市有风险，投注须谨慎。预测仅供娱乐参考，还需理性购彩!");

                    if(!init){
                        doPostData(summary,content.toString(),users);
                        //保存该场比赛到数据库
                        match_latest_70.setSendDate(new Date());
                        match_latest_70.setRule("下半场");
                        this.save(match_latest_70);
                    }

                }
            }

            DuixianMatchDTO match_latest_half = postDuixianData(url_half,param_half);
            System.err.println("当前最新比赛-上半场至少有一球：");
            System.err.println(match_latest_half);
            if(match_latest_half == null){
                retryTimes -= 1;
            }else{
                //若是推出的新比赛
                if(match_latest_half.getTeamHomeId() != null && match_latest_half.getTeamAwayId() != null &&
                        !match_latest_half.getTeamHomeId().equals(home_team_ID_half) && !match_latest_half.getTeamAwayId().equals(away_team_ID_half)){
                    boolean init = false;
                    if(home_team_ID_half.equals("") && away_team_ID_half.equals("")){
                        init = true;
                    }
                    home_team_ID_half = match_latest_half.getTeamHomeId();
                    away_team_ID_half = match_latest_half.getTeamAwayId();

                    //推出该比赛
                    int totalscore = Integer.parseInt(match_latest_half.getScore().split(":")[0]) + Integer.parseInt(match_latest_half.getScore().split(":")[1]);
                    String summary = match_latest_half.getLeague() + ": " + match_latest_half.getTeamHomeName() + " VS " + match_latest_half.getTeamAwayName();
                    StringBuffer content = new StringBuffer();
                    content.append(match_latest_half.getLeague() + System.getProperty("line.separator"));
                    content.append(match_latest_half.getTeamHomeName() + " VS " + match_latest_half.getTeamAwayName() + System.getProperty("line.separator"));
                    content.append("当前比分：" + match_latest_half.getScore() + System.getProperty("line.separator"));
                    content.append("推荐时间：" + match_latest_half.getSendTime() + "分钟" + System.getProperty("line.separator"));
                    content.append("预测：上半场至少有一球产生" + System.getProperty("line.separator"));
                    content.append(System.getProperty("line.separator"));
                    content.append(System.getProperty("line.separator"));
                    content.append("温馨提示：彩市有风险，投注须谨慎。预测仅供娱乐参考，还需理性购彩!");

                    if(!init){
                        doPostData(summary,content.toString(),users);
                        //保存该场比赛到数据库
                        match_latest_half.setSendDate(new Date());
                        match_latest_half.setRule("上半场");
                        this.save(match_latest_half);
                    }
                }
            }
            //将完场比赛状态更新到表
            JSONObject jsonObject_70 = postLatest10(url_70,param_70);
            if(jsonObject_70 == null){
                retryTimes -= 1;
            }else {
                JSONArray arrays = jsonObject_70.getJSONArray("data");
                for (Object obj : arrays) {
                    if(((JSONObject)obj).getInteger("is_running") == 0){
                        String key = ((JSONObject)obj).getString("KEY");
                        ((DuixianMatchDao)this.baseDao).updateStatusByKey(key);
                    }
                }
            }
            //将完场比赛状态更新到表
            JSONObject jsonObject_half = postLatest10(url_half,param_half);
            if(jsonObject_half == null){
                retryTimes -= 1;
            }else{
                JSONArray arrays = jsonObject_half.getJSONArray("data");
                for (Object obj : arrays) {
                    if(((JSONObject)obj).getInteger("is_running") == 0){
                        String key = ((JSONObject)obj).getString("KEY");
                        ((DuixianMatchDao)this.baseDao).updateStatusByKey(key);
                    }
                }
            }
            //检测完场比赛
            List<DuixianMatchDTO> finishedList = ((DuixianMatchDao)this.baseDao).getFinishedList();
            for (DuixianMatchDTO dto : finishedList) {
                //根据对线key去对比赛果列表
                String duixian_key = dto.getDuixianKey();
                if(duixian_key == null || duixian_key.equals("")) continue;
                //查比赛日期，从头查起
                Date date = dto.getBeginDate();
                SimpleDateFormat sdf = new SimpleDateFormat("M-dd");
                String queryDate = sdf.format(date).toString();
                System.err.println("查询日期：" + queryDate);
                JSONObject jsonObject = checkResult(1,queryDate);
                if(jsonObject == null) {
                    retryTimes -= 1;
                }else {
                    int totalPages = jsonObject.getJSONObject("data").getInteger("pages");
                    boolean iffind = false;
                    for (int i = 1; i <= totalPages; i++) {
                        if(!iffind) break;
                        JSONObject queryJson = checkResult(i,queryDate);
                        if(queryJson != null){
                            JSONArray arrays = queryJson.getJSONObject("data").getJSONArray("list");
                            for (Object obj : arrays) {
                                String KEY = ((JSONObject) obj).getString("KEY");
                                if(KEY != null && duixian_key.equals(KEY)){
                                    String whole_score = ((JSONObject) obj).getJSONObject("NCN").getString("SCORE");
                                    String half_score = ((JSONObject) obj).getJSONObject("NCN").getString("H:SCORE");
                                    dto.setWholeScore(whole_score);
                                    dto.setHalfScore(half_score);
                                    int dtoscores = Integer.parseInt(dto.getScore().split(":")[0]) + Integer.parseInt(dto.getScore().split(":")[1]);
                                    if(dto.getRule().equals("上半场")){
                                        //判断中场比分和>推荐比分和
                                        int halfscores = Integer.parseInt(half_score.split(":")[0]) + Integer.parseInt(half_score.split(":")[1]);
                                        if(halfscores > dtoscores){
                                            dto.setResult("命中");
                                        }else{
                                            dto.setResult("未命中");
                                        }
                                    }else{
                                        //判断全场比分和>推荐比分和
                                        int wholescores = Integer.parseInt(whole_score.split(":")[0]) + Integer.parseInt(whole_score.split(":")[1]);
                                        if(wholescores > dtoscores){
                                            dto.setResult("命中");
                                        }else{
                                            dto.setResult("未命中");
                                        }
                                    }
                                    break;
                                }
                            }
                            iffind = true;
                        }
                    }
                    //更新数据
                    this.update(dto);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            retryTimes -= 1;
        }

        }

    private String getdynamicIP() {
        //构造api连接地址
        Map<String, Object> params = getParamMap();
        StringBuffer sb = new StringBuffer(apiUrl);
        sb.append("?");
        params.entrySet().stream().forEach(param -> {
            sb.append(param.getKey()).append("=").append(param.getValue()).append("&");
        });
        String url = sb.toString();

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("Accept-Encoding", "gzip");
            CloseableHttpResponse response = httpclient.execute(httpget);
            System.out.println(response.getStatusLine());
            //获取到的结果
            String proxyHostStr = EntityUtils.toString(response.getEntity());
            JSONObject ipjson = JSONObject.parseObject(proxyHostStr);
            String ip = ipjson.getJSONArray("data").getJSONObject(0).getString("ip");
            String port = ipjson.getJSONArray("data").getJSONObject(0).getString("port");
            System.out.println("提取到的代理ip:" + ip + ":" + port);
            return ip + ":" + port;


        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private JSONObject postLatest10(String url, String param) {
        try {
            HttpHost proxyHost = HttpHost.create(dynamicIP);
            CloseableHttpClient client = HttpClients.custom().build();
            HttpPost httppost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(6000)
                    .setSocketTimeout(6000).setProxy(proxyHost).build();
            httppost.setConfig(requestConfig);
            httppost.addHeader("Authorization","Token " + token);
            httppost.setEntity(new StringEntity(param));

            HttpResponse httpResponse = client.execute(httppost);

            int responseCode = httpResponse.getStatusLine().getStatusCode();
            System.err.println("获取最近10场比赛-状态码：" + responseCode);
            if(responseCode != 200){
                return null;
            }

            String responseData =  EntityUtils.toString(httpResponse.getEntity());
            JSONObject jsonObject = JSONObject.parseObject(responseData);
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject checkResult(int pages,String queryDate) {
        try {
            // 发送请求参数
            String param = "{\"page\":" + pages + ",\"date\":" + '"' + queryDate + '"' + ",\"lids\":[],\"uid\":1619}";
            HttpHost proxyHost = HttpHost.create(dynamicIP);
            CloseableHttpClient client = HttpClients.custom().build();
            HttpPost httppost = new HttpPost(url_checkResult);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(6000)
                    .setSocketTimeout(6000).setProxy(proxyHost).build();
            httppost.setConfig(requestConfig);
            httppost.addHeader("Authorization","Token " + token);
            httppost.setEntity(new StringEntity(param));

            HttpResponse httpResponse = client.execute(httppost);

            int responseCode = httpResponse.getStatusLine().getStatusCode();
            System.err.println("检查赛果-状态码：" + responseCode);
            if(responseCode != 200){
                return null;
            }

            String responseData =  EntityUtils.toString(httpResponse.getEntity());
            JSONObject jsonObject = JSONObject.parseObject(responseData);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public int insertMatchInfo(DuixianMatchDTO duixianMatchDTO) {
        int result = ((DuixianMatchDao)this.baseDao).insert(duixianMatchDTO);
        return result;
    }





    private DuixianMatchDTO postDuixianData(String url, String param) {
        DuixianMatchDTO latest = new DuixianMatchDTO();
        try {
            HttpHost proxyHost = HttpHost.create(dynamicIP);
            CloseableHttpClient client = HttpClients.custom().build();
            HttpPost httppost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(6000)
                    .setSocketTimeout(6000).setProxy(proxyHost).build();
            httppost.setConfig(requestConfig);
            httppost.addHeader("Authorization","Token " + token);
            httppost.setEntity(new StringEntity(param));

            HttpResponse httpResponse = client.execute(httppost);

            int responseCode = httpResponse.getStatusLine().getStatusCode();
            System.err.println("获取最新比赛-状态码：" + responseCode);
            if(responseCode != 200){
                return null;
            }

            String responseData =  EntityUtils.toString(httpResponse.getEntity());
//            System.err.println("登录响应内容：" + responseData);
            JSONObject jsonObject = JSONObject.parseObject(responseData);
            if(jsonObject != null && jsonObject.getString("msg").equals("返回最近10场比赛")){
                //获取最新场次比赛
                JSONObject match = jsonObject.getJSONArray("data").getJSONObject(0);
                latest.setRunning((Integer) match.get("is_running"));
                latest.setDuixianId(match.getInteger("DUIXIAN_ID"));
                latest.setDuixianKey(match.getString("KEY"));
                JSONObject NCN = match.getJSONObject("NCN");
                latest.setLeagueId(NCN.getString("LID"));
                latest.setLeague(NCN.getString("LEAGUE"));
                latest.setTeamHomeId(NCN.getString("TEAM_H_ID"));
                latest.setTeamHomeName(NCN.getString("TEAM_H"));
                latest.setTeamAwayId(NCN.getString("TEAM_C_ID"));
                latest.setTeamAwayName(NCN.getString("TEAM_C"));
                latest.setBeginDate(sdf.parse(NCN.getString("DATE")));
                latest.setScore(NCN.getString("SCORE"));
                latest.setSendTime(NCN.getInteger("TIMER").toString());
            }
            return latest;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private String loginDuixian() throws MalformedURLException {
        String params = "{\"username\":\"18086820125\",\"password\":\"123456\"}";
        try {
            String url = "http://api.dui-xian.com:8002/api/oddsindex/login";
            HttpHost proxyHost = HttpHost.create(dynamicIP);
            CloseableHttpClient client = HttpClients.custom().build();
            HttpPost httppost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(6000)
                    .setSocketTimeout(6000).setProxy(proxyHost).build();
            httppost.setConfig(requestConfig);
            httppost.setEntity(new StringEntity(params));

            HttpResponse httpResponse = client.execute(httppost);

            int responseCode = httpResponse.getStatusLine().getStatusCode();
            System.err.println("登录状态码：" + responseCode);
            if(responseCode != 200){
                return "error code";
            }

            String responseData =  EntityUtils.toString(httpResponse.getEntity());
            System.err.println("登录响应内容：" + responseData);
            JSONObject jsonObject = JSONObject.parseObject(responseData);
            return jsonObject.get("token").toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }



    }

    private JSONObject queryDuiXianData() {
        JSONObject json = new JSONObject();
        //构造cmd指令
        Runtime runtime = Runtime.getRuntime();
        try{
            Process pr = runtime.exec("C:\\Users\\Administrator\\AppData\\Local\\Programs\\Python\\Python310-32\\python.exe C:\\Users\\Administrator\\Desktop\\scrawler.py");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            pr.getInputStream(),
                            "UTF-8"
                    )
            );
            String line;
//            StringBuilder b = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if(line.equals("json:")) {
                    line = br.readLine();
                    break;
                }
            }
            System.out.println(line);
            json = JSON.parseObject(line);
            br.close();
            pr.waitFor();
            return json;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public static String createPwd() {
        String passwordTemplate = "pwd={pwd}&pid=-1&cid=-1&uid=&sip=0&nd=1" ;
        return passwordTemplate.replace("{pwd}", pwd);
    }

    public static Map<String, Object> getParamMap() {
        /****
         * 详细参数参照产品文档：
         ****/
        Map<String, Object> map = new HashMap(10);
        map.put("type", type);
        map.put("num", num);
        map.put("unbindTime", unbindTime);
        map.put("dataType", dataType);
        map.put("orderId", orderId);
        map.put("time", System.currentTimeMillis() / 1000);
        map.put("sign", getSign(orderId, pwd));
        return map;
    }

    public static String getSign(String orderId, String secret) {
        long time = System.currentTimeMillis() / 1000;
        String str1 = String.format("orderId=%s&secret=%s&time=%s", orderId, secret, time);
        String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(str1).toLowerCase();
        return sign;
    }

    /**
     * 获取实时指数
     *
     * @throws IOException
     * @throws ParseException
     */
//    public void getTimeRate(MatchScheduleDTO matchScheduleDTO, int num, MatchContentDTO contentDTO) throws IOException, ParseException {
//        System.err.println("------------------------开始获取赔率数据-------------------------------------");
//
//
//        if (num == 5) {
//            String addr = "https://open.sportnanoapi.com/api/v5/football/odds/history?user=" + user + "&secret=" + secret + "&id=" + matchScheduleDTO.getMatchId();
//            URL url = new URL(addr);
//            StringBuffer buffer = new StringBuffer();
//            //http协议传输
//            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
//            httpUrlConn.setDoOutput(true);
//            httpUrlConn.setDoInput(true);
//            httpUrlConn.setUseCaches(false);
//            httpUrlConn.connect();
//            //将返回的输入流转换成字符串
//            InputStream inputStream = httpUrlConn.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String str = null;
//            while ((str = bufferedReader.readLine()) != null) {
//                buffer.append(str);
//            }
//            bufferedReader.close();
//            inputStreamReader.close();
//            //释放资源
//            inputStream.close();
//            inputStream = null;
//            httpUrlConn.disconnect();
//            //saveMatch(buffer.toString());
//            JSONObject json = JSONObject.parseObject(buffer.toString());
//            Object results = json.get("results");
//            if (results != null) {
//                JSONObject jsonObject = JSONObject.parseObject(results.toString());
//                Object o = jsonObject.get("2");
//                if (o == null) {
//                    return;
//                }
//                JSONObject bet365 = JSONObject.parseObject(o.toString());
//
//
//                Object bs = bet365.get("bs");
//                JSONArray arry = JSONArray.parseArray(bs.toString());
//                //倒敘 取到初盘结束循环
//                for (int i = arry.size() - 1; i >= 0; i--) {
//                    Object o1 = arry.get(i);
//                    JSONArray j = JSONArray.parseArray(o1.toString());
//                    if (i == arry.size() - 1) {
//                        Object inirate = j.get(3);
//
//                        matchScheduleDTO.setIniRate(Double.valueOf(inirate + ""));
//                    }
//                    if (StringUtils.isNotEmpty(j.get(1) + "")) {
//                        Object o2 = arry.get(i + 1);
//                        JSONArray start = JSONArray.parseArray(o2.toString());
//                        Object startRate = start.get(3);
//                        matchScheduleDTO.setStartRate(Double.valueOf(startRate + ""));
//                        break;
//
//                    }
//
//                }
//
//
//                Object asia = bet365.get("asia");
//                JSONArray asiaarry = JSONArray.parseArray(asia.toString());
//                //倒敘 取到初盘结束循环
//                for (int i = asiaarry.size() - 1; i >= 0; i--) {
//                    Object o1 = asiaarry.get(i);
//                    JSONArray j = JSONArray.parseArray(o1.toString());
////                    if (i == asiaarry.size() - 1) {
////                        Object inirate = j.get(3);
////
////                        matchScheduleDTO.setStartLetPan(Double.valueOf(inirate + ""));
////                    }
//                    if (StringUtils.isNotEmpty(j.get(1) + "")) {
//                        Object o2 = asiaarry.get(i + 1);
//                        JSONArray start = JSONArray.parseArray(o2.toString());
//                        Object startRate = start.get(3);
//                        matchScheduleDTO.setStartLetPan(Double.valueOf(startRate + ""));
//                        break;
//
//                    }
//
//                }
//
//
//            }
//        } else if (num == 45) {
//            String addr = "https://open.sportnanoapi.com/api/v5/football/odds/history?user=" + user + "&secret=" + secret + "&id=" + matchScheduleDTO.getMatchId();
//            URL url = new URL(addr);
//            StringBuffer buffer = new StringBuffer();
//            //http协议传输
//            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
//            httpUrlConn.setDoOutput(true);
//            httpUrlConn.setDoInput(true);
//            httpUrlConn.setUseCaches(false);
//            httpUrlConn.connect();
//            //将返回的输入流转换成字符串
//            InputStream inputStream = httpUrlConn.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String str = null;
//            while ((str = bufferedReader.readLine()) != null) {
//                buffer.append(str);
//            }
//            bufferedReader.close();
//            inputStreamReader.close();
//            //释放资源
//            inputStream.close();
//            inputStream = null;
//            httpUrlConn.disconnect();
//            //saveMatch(buffer.toString());
//            JSONObject json = JSONObject.parseObject(buffer.toString());
//            Object results = json.get("results");
//            if (results != null) {
//                JSONObject jsonObject = JSONObject.parseObject(results.toString());
//                Object o = jsonObject.get("2");
//                if (o == null) {
//                    return;
//                }
//                JSONObject bet365 = JSONObject.parseObject(o.toString());
//                Object bs = bet365.get("bs");
//                JSONArray arry = JSONArray.parseArray(bs.toString());
//                Object now = arry.get(0);//当前最新指数
//                JSONArray nowRate = JSONArray.parseArray(now.toString());
//                Object o1 = nowRate.get(3);
//                matchScheduleDTO.setMidRate(Double.valueOf(o1 + ""));
//
//            }
//        } else if (num == 80) {
//
//            String addr = "https://open.sportnanoapi.com/api/v5/football/odds/history?user=" + user + "&secret=" + secret + "&id=" + matchScheduleDTO.getMatchId();
//            URL url = new URL(addr);
//            StringBuffer buffer = new StringBuffer();
//            //http协议传输
//            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
//            httpUrlConn.setDoOutput(true);
//            httpUrlConn.setDoInput(true);
//            httpUrlConn.setUseCaches(false);
//            httpUrlConn.connect();
//            //将返回的输入流转换成字符串
//            InputStream inputStream = httpUrlConn.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String str = null;
//            while ((str = bufferedReader.readLine()) != null) {
//                buffer.append(str);
//            }
//            bufferedReader.close();
//            inputStreamReader.close();
//            //释放资源
//            inputStream.close();
//            inputStream = null;
//            httpUrlConn.disconnect();
//            //saveMatch(buffer.toString());
//            JSONObject json = JSONObject.parseObject(buffer.toString());
//            Object results = json.get("results");
//            if (results != null) {
//                JSONObject jsonObject = JSONObject.parseObject(results.toString());
//                Object o = jsonObject.get("2");
//                if (o == null) {
//                    return;
//                }
//                JSONObject bet365 = JSONObject.parseObject(o.toString());
//                Object bs = bet365.get("bs");
//                JSONArray arry = JSONArray.parseArray(bs.toString());
//
//
//                Object now = arry.get(0);//当前最新指数
//                JSONArray nowRate = JSONArray.parseArray(now.toString());
//                Object o2 = nowRate.get(2);
//                Object o3 = nowRate.get(3);
//                Object o4 = nowRate.get(4);
//                Object o7 = nowRate.get(7);
//                String s = o7.toString();
//                String zd = s.substring(0, s.indexOf("-"));
//                String kd = s.substring(s.indexOf("-") + 1);
//
//
//                matchScheduleDTO.setBig80(Double.valueOf(o3 + ""));
//                matchScheduleDTO.setBigRate80(Double.valueOf(o2 + ""));
//                matchScheduleDTO.setSmallRate80(Double.valueOf(o4 + ""));
//
//
//                Object asia = bet365.get("asia");
//
//                JSONArray arry2 = JSONArray.parseArray(asia.toString());
//
//                Object now2 = arry2.get(0);
//                JSONArray asiaarry = JSONArray.parseArray(now2.toString());
//                //倒敘 取到初盘结束循环
//                JSONArray nowasiaRate = JSONArray.parseArray(asiaarry.toString());
//
//                matchScheduleDTO.setLetPan80((Double.valueOf(nowasiaRate.get(3) + "")));
//
//
//            }
//
//        } else if (num == 68) {
//
//            String addr = "https://open.sportnanoapi.com/api/v5/football/odds/history?user=" + user + "&secret=" + secret + "&id=" + matchScheduleDTO.getMatchId();
//            URL url = new URL(addr);
//            StringBuffer buffer = new StringBuffer();
//            //http协议传输
//            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
//            httpUrlConn.setDoOutput(true);
//            httpUrlConn.setDoInput(true);
//            httpUrlConn.setUseCaches(false);
//            httpUrlConn.connect();
//            //将返回的输入流转换成字符串
//            InputStream inputStream = httpUrlConn.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String str = null;
//            while ((str = bufferedReader.readLine()) != null) {
//                buffer.append(str);
//            }
//            bufferedReader.close();
//            inputStreamReader.close();
//            //释放资源
//            inputStream.close();
//            inputStream = null;
//            httpUrlConn.disconnect();
//            //saveMatch(buffer.toString());
//            JSONObject json = JSONObject.parseObject(buffer.toString());
//            Object results = json.get("results");
//            if (results != null) {
//                JSONObject jsonObject = JSONObject.parseObject(results.toString());
//                Object o = jsonObject.get("2");
//                if (o == null) {
//                    return;
//                }
//                JSONObject bet365 = JSONObject.parseObject(o.toString());
//                Object bs = bet365.get("bs");
//                JSONArray arry = JSONArray.parseArray(bs.toString());
//
//
//                Object now = arry.get(0);//当前最新指数
//                JSONArray nowRate = JSONArray.parseArray(now.toString());
//                Object o2 = nowRate.get(2);
//                Object o3 = nowRate.get(3);
//                Object o4 = nowRate.get(4);
//                Object o7 = nowRate.get(7);
//                String s = o7.toString();
//                String zd = s.substring(0, s.indexOf("-"));
//                String kd = s.substring(s.indexOf("-") + 1);
//
//                BigDecimal o3_ = new BigDecimal(Double.valueOf(o3 + ""));
//
//                BigDecimal zd_ = new BigDecimal(Double.valueOf(zd));
//                BigDecimal kd_ = new BigDecimal(Double.valueOf(kd));
//
//                matchScheduleDTO.setBig70(Double.valueOf(o3 + ""));
//                matchScheduleDTO.setBigRate70(Double.valueOf(o2 + ""));
//                matchScheduleDTO.setSmallRate70(Double.valueOf(o4 + ""));
//
//
//                Object asia = bet365.get("asia");
//
//                JSONArray arry2 = JSONArray.parseArray(asia.toString());
//
//                Object now2 = arry2.get(0);
//                JSONArray asiaarry = JSONArray.parseArray(now2.toString());
//                //倒敘 取到初盘结束循环
//                JSONArray nowasiaRate = JSONArray.parseArray(asiaarry.toString());
//
//                matchScheduleDTO.setLetPan70((Double.valueOf(nowasiaRate.get(3) + "")));
//
//
//            }
//
//        } else if (num == 0) {
//            String addr = "https://open.sportnanoapi.com/api/v5/football/odds/history?user=" + user + "&secret=" + secret + "&id=" + matchScheduleDTO.getMatchId();
//            URL url = new URL(addr);
//            StringBuffer buffer = new StringBuffer();
//            //http协议传输
//            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
//            httpUrlConn.setDoOutput(true);
//            httpUrlConn.setDoInput(true);
//            httpUrlConn.setUseCaches(false);
//            httpUrlConn.connect();
//            //将返回的输入流转换成字符串
//            InputStream inputStream = httpUrlConn.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String str = null;
//            while ((str = bufferedReader.readLine()) != null) {
//                buffer.append(str);
//            }
//            bufferedReader.close();
//            inputStreamReader.close();
//            //释放资源
//            inputStream.close();
//            inputStream = null;
//            httpUrlConn.disconnect();
//            //saveMatch(buffer.toString());
//            JSONObject json = JSONObject.parseObject(buffer.toString());
//            Object results = json.get("results");
//            if (results != null) {
//                matchScheduleDTO.setContentRate("已存");
//                contentDTO.setContentRate(results.toString());
//
//            }
//
//
//        } else if (num == 69) {
//
//            String addr = "https://open.sportnanoapi.com/api/v5/football/odds/history?user=" + user + "&secret=" + secret + "&id=" + matchScheduleDTO.getMatchId();
//            URL url = new URL(addr);
//            StringBuffer buffer = new StringBuffer();
//            //http协议传输
//            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
//            httpUrlConn.setDoOutput(true);
//            httpUrlConn.setDoInput(true);
//            httpUrlConn.setUseCaches(false);
//            httpUrlConn.connect();
//            //将返回的输入流转换成字符串
//            InputStream inputStream = httpUrlConn.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String str = null;
//            while ((str = bufferedReader.readLine()) != null) {
//                buffer.append(str);
//            }
//            bufferedReader.close();
//            inputStreamReader.close();
//            //释放资源
//            inputStream.close();
//            inputStream = null;
//            httpUrlConn.disconnect();
//            //saveMatch(buffer.toString());
//            JSONObject json = JSONObject.parseObject(buffer.toString());
//            Object results = json.get("results");
//            if (results != null) {
//                JSONObject jsonObject = JSONObject.parseObject(results.toString());
//                Object o = jsonObject.get("2");
//                if (o == null) {
//                    return;
//                }
//                JSONObject bet365 = JSONObject.parseObject(o.toString());
//                Object bs = bet365.get("bs");
//                JSONArray arry = JSONArray.parseArray(bs.toString());
//
//
//                Object now = arry.get(0);//当前最新指数
//                JSONArray nowRate = JSONArray.parseArray(now.toString());
//
//                Object o7 = nowRate.get(7);
//                String s = o7.toString();
//                String zd = s.substring(0, s.indexOf("-"));
//                String kd = s.substring(s.indexOf("-") + 1);
//
//                int nowS = Integer.parseInt(zd) + Integer.parseInt(kd);//当前比分
//
//                //取最近5条
//                for (int i = 1; i < 5; i++) {
//                    Object o1 = arry.get(i);
//                    JSONArray oi = JSONArray.parseArray(o1.toString());
//                    Object o3 = oi.get(3);
//                    int i1 = new BigDecimal(Double.valueOf(o3 + "")).compareTo(new BigDecimal(nowS));
//                    if (i1 == 0) {
//
//                        matchScheduleDTO.setRateFor(Double.valueOf(o3 + ""));
//
//                        break;
//
//                    }
//
//                }

//                //取最近5条
//                for (int i = 1; i < 25; i++) {
//                    Object o1 = arry.get(i);
//                    JSONArray oi = JSONArray.parseArray(o1.toString());
//                    Object o3 = oi.get(3);
//                    int i1 = new BigDecimal(Double.valueOf(o3 + "")).compareTo(new BigDecimal(nowS));
//                    if (i1 == 0) {
//
//                        matchScheduleDTO.setRateFor2(Double.valueOf(o3 + ""));
//
//                        break;
//
//                    }
//
//                }


//            }
//
//
//        }
//
//    }


    /**
     * 70分钟进行一次推荐检测
     *
     * @param dto
     * @return
     * @throws Exception
     */
//    public MatchScheduleDTO checkMatch(MatchScheduleDTO dto, int num, String remark) throws Exception {
//
//        List<String> competitionRate = new ArrayList<>();
//        competitionRate = this.baseDao.getCompetitionRate("");
//
//
//        List<String> competitionBigRate = new ArrayList<>();
//        competitionBigRate = this.baseDao.getCompetitionBigRate();
//
//        boolean isYY = dto.getCompetitionName().contains("球会友谊赛");
//        if (isYY) {
//            return dto;
//        }
//
//
//        int Scores70 = dto.getHomeScores70() + dto.getAwayScores70();
//        if (num == 70) {
//
//            if (StringUtils.isEmpty(remark) || !remark.contains("70")) {
//                dto.setRemark(dto.getRemark() + "70;");
//                Boolean f70 = false;
//                try {
//                    //規則A
//                    if (dto.getIniRate() != null && dto.getMidRate() != null) {
//                        BigDecimal iniRate = new BigDecimal(dto.getIniRate());
//                        BigDecimal halfScores = new BigDecimal(dto.getHomeHalfScores() + dto.getAwayHalfScores());
//
//                        BigDecimal midRate = new BigDecimal(dto.getMidRate());
//                        if ((iniRate.compareTo(new BigDecimal(2)) == 1 || iniRate.compareTo(new BigDecimal(2)) == 0) && ((midRate.subtract(halfScores)).compareTo(new BigDecimal(1)) == 0 || (midRate.subtract(halfScores)).compareTo(new BigDecimal(1.25)) == 0) && Scores70 - dto.getHomeHalfScores() - dto.getAwayHalfScores() == 1) {
//
//                            if (dto.getHomeScores70() < dto.getAwayScores70() && dto.getHomeHalfScores() + dto.getAwayHalfScores() > 0 && (dto.getCompetitionName().contains("英格兰") && dto.getCompetitionName().contains("联赛"))) {
//                                dto.setRules(dto.getRules() + "A1");
//                                f70 = true;
//                            }
//
//                            if (dto.getHomeHalfScores() + dto.getAwayHalfScores() > 0
//                                    && dto.getHomeShootStraight70() + dto.getHomeShootBias70() + dto.getAwayShootStraight70() + dto.getAwayShootBias70() - dto.getHomeShootStraight60() - dto.getHomeShootBias60() - dto.getAwayShootStraight60() - dto.getAwayShootBias60() >= 3
//                            ) {
//                                if (competitionBigRate.contains(dto.getCompetitionName())) {
//                                    // TODO: 2023/8/23
//                                    // dto.setRules(dto.getRules() + "A2");
//                                    //  f70 = true;
//
//                                }
//
//
//                            }
//
//
//                        }
//                    }
//
//                } catch (Exception e) {
//                    System.err.println("比赛id" + dto.getId() + "检测规则A，出现异常，请检查！！！！！！！！！！！！！！！");
//                    e.printStackTrace();
//                }
//
//
//                //规则G
//                try {
//                    if (dto.getRateFor() != null) {
//                        BigDecimal s70 = new BigDecimal(Scores70);
//                        BigDecimal rf = new BigDecimal(dto.getRateFor());
//
//                        if (s70.compareTo(rf) == 0 && Scores70 - dto.getHomeHalfScores() - dto.getAwayHalfScores() == 1) {
//                            BigDecimal iniRate = new BigDecimal(dto.getIniRate());
//
//                            if ((iniRate.compareTo(new BigDecimal(2)) == 0 || iniRate.compareTo(new BigDecimal(2.25)) == 0) && dto.getHomeHalfScores() + dto.getAwayHalfScores() >= 1) {
//                                if (dto.getHomeScores70() < dto.getAwayScores70()) {
//                                    dto.setRules(dto.getRules() + "G1");
//                                    f70 = true;
//                                }
//
//
//                                if (dto.getHomeShootStraight70() + dto.getHomeShootBias70() + dto.getAwayShootStraight70() + dto.getAwayShootBias70() - dto.getHomeShootStraight60() - dto.getHomeShootBias60() - dto.getAwayShootStraight60() - dto.getAwayShootBias60() >= 1) {
//                                    if (competitionBigRate.contains(dto.getCompetitionName())) {
//                                        dto.setRules(dto.getRules() + "G2");
//                                        f70 = true;
//
//                                    }
//                                }
//
//
//                            } else if (iniRate.compareTo(new BigDecimal(2.5)) == 1 || iniRate.compareTo(new BigDecimal(2.5)) == 0) {
//                                if (dto.getHomeScores70() < dto.getAwayScores70()) {
//                                    dto.setRules(dto.getRules() + "G1");
//                                    f70 = true;
//                                }
//
//                                if (dto.getHomeShootStraight70() + dto.getHomeShootBias70() + dto.getAwayShootStraight70() + dto.getAwayShootBias70() - dto.getHomeShootStraight60() - dto.getHomeShootBias60() - dto.getAwayShootStraight60() - dto.getAwayShootBias60() >= 1) {
//                                    if (competitionBigRate.contains(dto.getCompetitionName())) {
//                                        dto.setRules(dto.getRules() + "G2");
//                                        f70 = true;
//
//                                    }
//                                }
//
//
//                            }
//
//
//                        }
//
//                    }
//
//                } catch (Exception e) {
//                    System.err.println("比赛id" + dto.getId() + "检测规则G， 出现异常，请检查！！！！！！！！！！！！！！！");
//
//                }


                //规则B
             /*   try {

                    if (Scores70 - dto.getHomeHalfScores() - dto.getAwayHalfScores() >= 1) {

                        if (dto.getHomeShootStraight70() + dto.getHomeShootBias70() + dto.getAwayShootStraight70() + dto.getAwayShootBias70() - dto.getHomeShootStraight60() - dto.getHomeShootBias60() - dto.getAwayShootStraight60() - dto.getAwayShootBias60() >= 5) {
                            if (competitionBigRate.contains(dto.getCompetitionName())) {
                                dto.setRules(dto.getRules() + "B");
                                f70 = true;
                            }
                        }

                    }

                } catch (Exception e) {
                    System.err.println("比赛id" + dto.getId() + "检测规则B， 出现异常，请检查！！！！！！！！！！！！！！！");

                }*/


//                try {
//                    //規則X
//                    if (dto.getHomeShootStraight70() + dto.getAwayShootStraight70() >= 15) {
//                        dto.setRules(dto.getRules() + "X");
//                        //  f70 = true;
//                    } else if (dto.getHomeShootStraight70() + dto.getHomeShootBias70() + dto.getAwayShootStraight70() + dto.getAwayShootBias70() >= 29) {
//                        dto.setRules(dto.getRules() + "X");
//                        //  f70 = true;
//                    } else if (dto.getHomeShootStraight70() + dto.getHomeShootBias70() >= 22 || dto.getAwayShootStraight70() + dto.getAwayShootBias70() >= 22) {
//
//                        dto.setRules(dto.getRules() + "X");
//                        //   f70 = true;
//                    } else if (dto.getHomeDangerAttack70() + dto.getAwayDangerAttack70() >= 195 || dto.getHomeDangerAttack70() >= 115 || dto.getAwayDangerAttack70() >= 110) {
//                        dto.setRules(dto.getRules() + "X");
//                        // f70 = true;
//                    } else if (dto.getHomeCorner70() + dto.getAwayCorner70() > 18) {
//
//                        dto.setRules(dto.getRules() + "X");
//                        // f70 = true;
//                    }
//                } catch (Exception e) {
//                    System.err.println("比赛id" + dto.getId() + "检测规则X，出现异常，请检查！！！！！！！！！！！！！！！");
//                    e.printStackTrace();
//                }
//
//                if (f70) {
//                    sendMsg(dto, 70, dto.getRules());
//                }
//
//
//            }
//        } else if (num == 75) {
//            if (StringUtils.isEmpty(remark) || !remark.contains("75")) {
//                dto.setRemark(dto.getRemark() + "75;");
//                try {
//                    //規則F
//                    if (dto.getHomeShootStraight75() - dto.getHomeShootStraight60() >= 1
//                            &&
//                            dto.getAwayShootStraight75() - dto.getAwayShootStraight60() >= 1
//                            &&
//                            dto.getHomeShootBias75() - dto.getHomeShootBias60() > 1
//                            &&
//                            dto.getAwayShootBias75() - dto.getAwayShootBias60() > 1
//                            &&
//                            dto.getHomeScores75() + dto.getAwayScores75() - dto.getHomeHalfScores() - dto.getAwayHalfScores() > 0
//
//                    ) {
//
//                        dto.setRules(dto.getRules() + "F");
//
//                        sendMsg(dto, 75, "F");
//
//
//                    }
//                } catch (Exception e) {
//                    System.err.println("比赛id" + dto.getId() + "检测规则F，出现异常，请检查！！！！！！！！！！！！！！！");
//                    e.printStackTrace();
//                }
//
//
//            }

//        }
//
//        return dto;
//    }
//
//    public void sendMsg(MatchScheduleDTO dto, int num, String rules) throws Exception {
//
//        try {
//
//
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            String strSentDate = format.format(new Date());
//            List<WxUserDTO> list2 = this.wxUserService.getList(strSentDate);
//            if (list2 != null && list2.size() > 0) {
//                List<String> ids = new ArrayList<>();
//                for (WxUserDTO d : list2) {
//                    System.out.println(d.getUserName() + ":" + d.getOpenId());
//                    ids.add(d.getOpenId());
//                }
//                StringBuffer sb = new StringBuffer();
//                sb.append(dto.getCompetitionName() + System.getProperty("line.separator"));
//                sb.append(dto.getHomeTeamName() + " VS " + dto.getAwayTeamName() + System.getProperty("line.separator"));
//                Float a = Float.valueOf(dto.getHomeScores() + dto.getAwayScores()) + 0.5f;
//                sb.append("推荐: 大 " + a + System.getProperty("line.separator"));
//                sb.append("推荐时间：" + num + "分钟" + System.getProperty("line.separator"));
//                sb.append("满足：" + rules + System.getProperty("line.separator"));
//
//
//                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//                Date date = new Date();
//                Calendar calendar = new GregorianCalendar();
//                calendar.setTime(date);
//                calendar.add(calendar.DATE, -1);//前一天
//                String tDay = sf.format(date);
//                String yDay = sf.format(calendar.getTime());
//                // 规则A统计
//                resultDto tDayA = baseDao.getReA(tDay);
//                resultDto yDayA = baseDao.getReA(yDay);
//                resultDto hDayA = baseDao.getReAHis();
//
//                sb.append("A1：" + System.getProperty("line.separator"));
//                sb.append("今日：" + tDayA.getAllnum() + "中" + tDayA.getMznum() + System.getProperty("line.separator"));
//                sb.append("昨日：" + yDayA.getAllnum() + "中" + yDayA.getMznum() + System.getProperty("line.separator"));
//                sb.append("历史：" + hDayA.getAllnum() + "中" + hDayA.getMznum() + System.getProperty("line.separator"));


//                resultDto tDayA2 = baseDao.getReA2(tDay);
//                resultDto yDayA2 = baseDao.getReA2(yDay);
//                resultDto hDayA2 = baseDao.getReA2His();
//
//                sb.append("A2：" + System.getProperty("line.separator"));
//                sb.append("今日：" + tDayA2.getAllnum() + "中" + tDayA2.getMznum() + System.getProperty("line.separator"));
//                sb.append("昨日：" + yDayA2.getAllnum() + "中" + yDayA2.getMznum() + System.getProperty("line.separator"));
//                sb.append("历史：" + hDayA2.getAllnum() + "中" + hDayA2.getMznum() + System.getProperty("line.separator"));
//

                // 规则G统计


//                resultDto tDayG = baseDao.getReG(tDay);
//                resultDto yDayG = baseDao.getReG(yDay);
//                resultDto hDayG = baseDao.getReGHis();
//                sb.append("G1：" + System.getProperty("line.separator"));
//                sb.append("今日：" + tDayG.getAllnum() + "中" + tDayG.getMznum() + System.getProperty("line.separator"));
//                sb.append("昨日：" + yDayG.getAllnum() + "中" + yDayG.getMznum() + System.getProperty("line.separator"));
//                sb.append("历史：" + hDayG.getAllnum() + "中" + hDayG.getMznum() + System.getProperty("line.separator"));
//
//                resultDto tDayG2 = baseDao.getReG2(tDay);
//                resultDto yDayG2 = baseDao.getReG2(yDay);
//                resultDto hDayG2 = baseDao.getReG2His();
//                sb.append("G2：" + System.getProperty("line.separator"));
//                sb.append("今日：" + tDayG2.getAllnum() + "中" + tDayG2.getMznum() + System.getProperty("line.separator"));
//                sb.append("昨日：" + yDayG2.getAllnum() + "中" + yDayG2.getMznum() + System.getProperty("line.separator"));
//                sb.append("历史：" + hDayG2.getAllnum() + "中" + hDayG2.getMznum() + System.getProperty("line.separator"));
//
////                // 规则F统计
////                resultDto tDayF = baseDao.getReF(tDay);
////                resultDto yDayF = baseDao.getReF(yDay);
////                resultDto hDayF = baseDao.getReFHis();
////                sb.append("F：" + System.getProperty("line.separator"));
////                sb.append("今日：" + tDayF.getAllnum() + "中" + tDayF.getMznum() + System.getProperty("line.separator"));
////                sb.append("昨日：" + yDayF.getAllnum() + "中" + yDayF.getMznum() + System.getProperty("line.separator"));
////                sb.append("历史：" + hDayF.getAllnum() + "中" + hDayF.getMznum() + System.getProperty("line.separator"));
//
//
//                // 规则X统计
////                resultDto tDayX = baseDao.getReX(tDay);
////                resultDto yDayX = baseDao.getReX(yDay);
////                resultDto hDayX = baseDao.getReXHis();
////                sb.append("X：" + System.getProperty("line.separator"));
////                sb.append("今日：" + tDayX.getAllnum() + "中" + tDayX.getMznum() + System.getProperty("line.separator"));
////                sb.append("昨日：" + yDayX.getAllnum() + "中" + yDayX.getMznum() + System.getProperty("line.separator"));
////                sb.append("历史：" + hDayX.getAllnum() + "中" + hDayX.getMznum() + System.getProperty("line.separator"));
//
//
//                sb.append("温馨提示：彩市有风险，投注须谨慎。预测仅供娱乐参考，还需理性购彩!");
//                doPostData(dto.getCompetitionName() + "：" + dto.getHomeTeamName() + " VS " + dto.getAwayTeamName() + " 大" + a, sb.toString(), ids);
//
//            }
//        } catch (Exception e) {
//            System.err.println("微信消息推送异常！！！！！！！");
//            e.printStackTrace();
//
//        }
//    }


//    @Override
//    public MatchScheduleDTO getByMatchId(Integer matchId) {
//        System.err.println("================" + matchId);
//        return this.baseDao.getByMatchId(matchId);
//    }
//
//
//    public List<MatchScheduleDTO> getByMatchTime(Integer time) {
//        return this.baseDao.getByMatchTime(time);
//    }


//    @Override
//    public void copyMatch() throws Exception {
//        //将前一天完成的比赛，复制到历史表。
//        //并删除当前表的数据
//        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
//        Date date = new Date();
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(date);
//        calendar.add(calendar.DATE, -2);//前2天
//        date = calendar.getTime();
//        this.baseDao.copyInfo(sf.format(date));
//        this.baseDao.deleInfo(sf.format(date));
//    }


    /**
     * 发送消息
     *
     * @param summary
     * @param content
     * @param UIDS
     * @return
     * @throws Exception
     */
    public String doPostData(String summary, String content, List<String> UIDS) throws Exception {
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
                return HttpStatus.SC_OK + "";
            }
        } catch (Exception e) {
            if (res == null) {
                return "HttpResponse 为 null!";
            }
            throw new RuntimeException(e);
        }
        if (res == null || res.getStatusLine() == null) {
            return "无响应";
        }
        return res.getStatusLine().getStatusCode() + "";
    }

    @Override
    public QueryWrapper<DuixianMatchEntity> getWrapper(Map<String, Object> params) {
        return null;
    }
}