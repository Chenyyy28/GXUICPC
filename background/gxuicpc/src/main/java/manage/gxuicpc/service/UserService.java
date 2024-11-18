package manage.gxuicpc.service;

import com.github.pagehelper.PageInfo;
import manage.gxuicpc.entity.UserPage;
import manage.gxuicpc.entity.User;
import manage.gxuicpc.entity.dto.UserDTO;
import manage.gxuicpc.entity.vo.UserVO;

public interface UserService {

    boolean checkLogin(User user);

    Long getUserId(User user);

    PageInfo<UserVO> getUserPage(UserPage userPage);

    Long addUser(User user);

    int deleteById(Long id);

    int updateById(UserDTO UserDTO);

    User getUserById(Long id);

    int updatePwd(String prePwd, String nowPwd);

    User getUserByName(String username);

    int updateUser(User user);
}
