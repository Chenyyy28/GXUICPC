package manage.gxuicpc.mapper;

import manage.gxuicpc.entity.Rank;
import manage.gxuicpc.entity.RankFactors;
import manage.gxuicpc.entity.RankInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RankMapper {
    @Insert("insert into rank_factors(contest_id,user_id,race_num,ranking,supplement) values (#{contestId},#{userId},#{raceNum},#{ranking},#{supplement})")
    int addRankFactor(RankFactors rankFactors);

    @Delete("delete from rank_factors where contest_id = #{id}")
    void deleteFactors(Long id);

    @Select("select count(*) from rank_factors where user_id=#{userId} and contest_id = #{contestId}")
    int checkExist(RankFactors rankFactors);

    @Update("update rank_factors set race_num = #{raceNum},ranking = #{ranking}, supplement = #{supplement} where user_id=#{userId} and contest_id = #{contestId}")
    int updateFactors(RankFactors rankFactors);

    @Select("select * from rank_factors where contest_id = #{contestId} and user_id = #{userId}")
    RankFactors getFactors(Long userId, Long contestId);

    @Update("update score set score=#{score} where id = #{id}")
    void updateRank(Long id, int score);


    @Select("select u.username,r.ranking,r.race_num,r.supplement from user u,rank_factors r where r.contest_id=#{contestId} and r.user_id = u.id")
    List<RankInfo> getContestInfo(Long contestId);
}
