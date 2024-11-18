package manage.gxuicpc.mapper;

import manage.gxuicpc.entity.Contest;
import manage.gxuicpc.entity.ContestPage;
import manage.gxuicpc.entity.vo.ContestVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContestMapper {

    List<ContestVO> getContest(ContestPage contestPage);

    @Insert("insert into contest(description, url, author,start_time,end_time,role_id,weight, create_time) " +
            "values " +
            "(#{description},#{url},#{author},#{startTime},#{endTime},#{roleId},#{weight},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    long addContest(Contest contest);

    @Delete("delete from contest where id=#{id}")
    void deleteContest(Long id);


    List<Contest> getContestByTime(int roleId, Long start, Long end);

    @Select("select count(*) from contest where id = #{contestId}")
    int getPeople(Long contestId);

    @Select("select role_id from contest where id = #{contestId}")
    Contest getContestById(Integer contestId);
}
