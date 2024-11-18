package manage.gxuicpc.mapper;

import manage.gxuicpc.entity.Problem;
import manage.gxuicpc.entity.Rank;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProblemMapper {

    @Select("select * from score where id=#{id}")
    Problem getProblemAmount(Long id);

    @Insert("insert into score(id) values (#{id})")
    int addUser(Long id);

    @Delete("delete from score where id = #{id}")
    int deleteUser(Long id);

    @Select("select user.id, user.username,user.ranking,s.score,s.atcoder_problem as atcoder," +
            "s.cf_problem as codeforces,s.luogu_problem as luogu,s.nk_problem as nowcoder,s.vjudge_problem as vjudge " +
            "from user, score s " +
            "where #{id} = (select u.role_id from user u where u.id = s.id) and user.id = s.id")
    List<Rank> getUser(int id);
}
