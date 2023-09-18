package io.renren.modules.zj;

import io.renren.modules.match.service.MatchScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulingLiveScore {

    @Autowired
    private MatchScheduleService Screenshot;

    @Scheduled(cron = "*/5 * * * * ?")
    public void scheduler3() throws Exception {
           Screenshot.realTimeStatistics();

    }

    // 每天晚上11 点执行一次
//    @Scheduled(cron = "0 0 23 * * ?")
//    public void scheduler2() throws Exception {
//            Screenshot.getMatchList();
//    }

    // 每天3 点执行一次
    @Scheduled(cron = "0 0 3 * * ?")
    public void scheduler1() throws Exception {
//        Screenshot.copyMatch();
    }



}
