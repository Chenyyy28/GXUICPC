package manage.gxuicpc.mapper;

import manage.gxuicpc.entity.UnContest;
import manage.gxuicpc.entity.UnContestPage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface UnContestMapper {
    List<UnContest> getUnContestList(UnContestPage unContestPage);

    @Insert("insert into un_contest(contest_name, url, user_id) values (#{contestName},#{url},#{userId})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Long addContest(UnContest unContest);

    @Delete("delete from un_contest where id=#{id}")
    void deleteContest(Long id);
}
