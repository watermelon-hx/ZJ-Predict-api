package io.renren.modules.match;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.modules.match.controller.MatchScheduleController;
import io.renren.modules.match.dto.MatchScheduleDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class test {
//    @Autowired
//    private static io.renren.modules.match.service.MatchScheduleService matchScheduleService;

    private static final String user = "jxkj";

    private static final String secret = "t80b7b170be597a35729639fa00b27b9";

    private static final String date = "20230629";

    public static void main(String[] args) throws IOException, ParseException {

        String str = "四川成都大一那会";

        boolean s = str.contains("成都");
        System.err.println(s);
    }


    public static String getMatchList() throws IOException, ParseException {

        String addr = "https://open.sportnanoapi.com/api/v5/football/match/schedule/diary?user=" + user + "&secret=" + secret + "&date=" + date;

        URL url = new URL(addr);
        StringBuffer buffer = new StringBuffer();
        //http协议传输
        HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
        httpUrlConn.setDoOutput(true);
        httpUrlConn.setDoInput(true);
        httpUrlConn.setUseCaches(false);
        httpUrlConn.connect();
        //将返回的输入流转换成字符串
        InputStream inputStream = httpUrlConn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        bufferedReader.close();
        inputStreamReader.close();
        //释放资源
        inputStream.close();
        inputStream = null;
        httpUrlConn.disconnect();
        saveMatch(buffer.toString());
        return "";

    }

    /**
     * 保存数据
     *
     * @param str
     * @return
     */
    public static String saveMatch(String str) throws ParseException {
        MatchScheduleController scheduleService = new MatchScheduleController();

        JSONObject json = JSONObject.parseObject(str);
        JSONObject results = json.getJSONObject("results");
        JSONArray match = results.getJSONArray("match");
        JSONArray competition = results.getJSONArray("competition");
        JSONArray team = results.getJSONArray("team");
        Map<Integer, String> competitionMap = new HashMap<>();
        for (int i = 0; i < competition.size(); i++) {
            Object obj = competition.get(i);
            Map entity = (Map) obj;
            competitionMap.put(Integer.parseInt(entity.get("id") + ""), (String) entity.get("name"));
        }
        Map<Integer, String> teamMap = new HashMap<>();
        for (int i = 0; i < team.size(); i++) {
            Object obj = team.get(i);
            Map entity = (Map) obj;
            teamMap.put(Integer.parseInt(entity.get("id") + ""), (String) entity.get("name"));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");


        for (int i = 0; i < match.size(); i++) {
            Object obj = match.get(i);
            Map entity = (Map) obj;

            MatchScheduleDTO dto = new MatchScheduleDTO();
            dto.setMatchId(Long.valueOf(entity.get("id") + ""));
            dto.setCompetitionId(Integer.parseInt(entity.get("competition_id") + ""));
            dto.setCompetitionName(competitionMap.get(dto.getCompetitionId()));
            dto.setHomeTeamId(Integer.parseInt(entity.get("home_team_id") + ""));
            dto.setHomeTeamName(teamMap.get(dto.getHomeTeamId()));
            dto.setAwayTeamId(Integer.parseInt(entity.get("away_team_id") + ""));
            dto.setAwayTeamName(teamMap.get(dto.getAwayTeamId()));
            dto.setStatusId(Integer.parseInt(entity.get("status_id") + ""));

            long date_temp = Long.valueOf(entity.get("match_time") + "");
            String date_string = sdf.format(new Date(date_temp * 1000L));
            Date parse = sdf.parse(date_string);
            dto.setMatchTime(parse);
            Object home_scores = entity.get("home_scores");
            dto.setHomeScores(Integer.valueOf(JSONArray.parseArray(home_scores.toString()).get(0) + ""));//主队比分
            dto.setHomeHalfScores(Integer.valueOf(JSONArray.parseArray(home_scores.toString()).get(1) + ""));//主队半場比分
            Object away_scores = entity.get("away_scores");
            dto.setAwayScores(Integer.valueOf(JSONArray.parseArray(away_scores.toString()).get(0) + ""));//客队比分
            dto.setAwayHalfScores(Integer.valueOf(JSONArray.parseArray(away_scores.toString()).get(1) + ""));//客队半场比分
//            scheduleService.save(dto);




        }


        return null;
    }

}
