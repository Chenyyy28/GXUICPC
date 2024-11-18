package manage.gxuicpc.mapper;

import manage.gxuicpc.entity.UserPage;
import manage.gxuicpc.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user where account = #{account} and password = #{password}")
    List<User> queryByUsernameAndPassword(String account, String password);

    @Select("select id from user where account = #{account}")
    Long getUserId(User user);

    @Select("select role_id from user where id = #{id}")
    int getUserRoleById(Long id);

    //    @Select("select * from user")
    List<User> getAll(UserPage userPage);

    @Insert("insert into user(username,account,password, email, role_id, create_time, update_time)" +
            " value (#{username},#{account},#{password}, #{email}, #{roleId}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long addUser(User user);

    @Delete("delete from user where id = #{id}")
    int deleteById(Long id);

    @Insert("insert into user(id,username,account,password, email,ranking, role_id, create_time, update_time)" +
            " value (#{id},#{username},#{account},#{password}, #{email},#{ranking}, #{roleId}, #{createTime}, #{updateTime})")
    void addUserWithId(User user);

    @Select("select * from user where id = #{id}")
    User getUserById(Long id);

    @Update("update user set ranking=#{rank} where id = #{id}")
    void updateRank(Long id, int rank);

    @Update("update user set password=#{nowPwd} where id = #{id}")
    int updateNowPwd(Long id, String nowPwd);

    List<String> getUserEmail(Integer role);

    @Select("select * from user where username = #{username}")
    User getUserByName(String username);

    @Update("update user set account=#{account},email=#{email},role_id=#{roleId} where id = #{id}")
    int update(User user);

//    List<User> getUserByName(String name);

//    List<User> getUserByCondition(Page page);
}
