package io.renren.modules.match.dto;

import lombok.Data;

import java.util.List;

@Data
public class MatchDTO {
    private result results;

    public class result {
        private List<matchChild> match;
        private List<competitionChild> competition;
        private List<teamChild> team;
    }

    public class matchChild {
        private Integer id;
        private Integer season_id;
        private Integer home_team_id;
        private Integer away_team_id;
        private Integer status_id;
        private Integer match_time;
        private int[] home_scores;
        private int[] away_scores;
    }

    public class competitionChild {
        private Integer id;
        private Integer name;
    }

    public class teamChild {
        private Integer id;
        private Integer name;
    }


}
