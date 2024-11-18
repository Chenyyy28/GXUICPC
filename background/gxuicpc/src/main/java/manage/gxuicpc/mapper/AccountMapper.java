package manage.gxuicpc.mapper;

import manage.gxuicpc.entity.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {

    @Select("select * from id_platform where user_id = #{id}")
    List<Account> getAccountById(Long id);

    @Select("select * from id_platform where user_id=#{id} and platform=#{platform}")
    Account getAccountByIdAndPlat(Long id, String platform);

    @Update("update id_platform set uid= #{uid} where user_id = #{id} and platform=#{platform}")
    int updateAccount(Long id, String uid, String platform);

    @Insert("insert into id_platform(user_id,uid,platform) values (#{id},#{uid},#{platform})")
    int insertAccount(Long id, String uid, String platform);

    @Insert("insert into id_platform(user_id,platform) values (#{userId},#{platform})")
    void addUserId(Long userId, String platform);

    @Delete("delete from id_platform where user_id=#{id}")
    int deleteById(Long id);
}
