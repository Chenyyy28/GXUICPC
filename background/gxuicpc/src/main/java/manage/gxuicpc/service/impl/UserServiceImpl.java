package manage.gxuicpc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.annotation.DeleteRedis;
import manage.gxuicpc.annotation.QueryRedis;
import manage.gxuicpc.common.RoleId;
import manage.gxuicpc.entity.UserPage;
import manage.gxuicpc.entity.User;
import manage.gxuicpc.entity.dto.UserDTO;
import manage.gxuicpc.entity.vo.UserVO;
import manage.gxuicpc.mapper.UserMapper;
import manage.gxuicpc.service.AccountService;
import manage.gxuicpc.service.ProblemService;
import manage.gxuicpc.service.UserService;
import manage.gxuicpc.utils.RedisUtils;
import manage.gxuicpc.utils.ThreadLocalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private AccountService accountService;
    @Value("${gxuicpc.default-password}")
    private String password;

    private static final String redisKey = RedisUtils.KEY + "User";

    @Override
    public boolean checkLogin(User user) {
        List<User> list = userMapper.queryByUsernameAndPassword(user.getAccount(), user.getPassword());
        if (list.isEmpty()) {
            return false;
        }
        return true;
//        return false;
    }

    @Override
    public Long getUserId(User user) {
        Long id = userMapper.getUserId(user);
        return id;
    }

    @QueryRedis(key = redisKey, id = false, page = true, resType = PageInfo.class)
    @Override
    public PageInfo<UserVO> getUserPage(UserPage userPage) {
        List<UserVO> userVOS = getAllUsers(userPage);
        PageInfo<UserVO> pageInfo = new PageInfo<>(userVOS);
        pageInfo.setTotal(userMapper.getAll(userPage).size());
        return pageInfo;
    }

    /**
     * 添加用户
     * TODO 可能造成雪崩击穿
     *
     * @param user
     * @return
     */
    @Override
    @DeleteRedis(key = "gxuicpc", id = false)
    @Transactional
    public Long addUser(User user) {
        user.setPassword(password);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.addUser(user);
        problemService.addUser(user.getId());
        accountService.addAccount(user.getId());
        return user.getId();
    }

    @DeleteRedis(key = "gxuicpc", id = false)
    @Override
    @Transactional
    public int deleteById(Long id) {
        problemService.deleteById(id);
        accountService.deleteById(id);
        return userMapper.deleteById(id);
    }

    @Override
    @Transactional
    @DeleteRedis(key = redisKey, id = false)
    public int updateById(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        switch (userDTO.getRoleId()) {
            case "管理员":
                user.setRoleId((short) 0);
                break;
            case "现役成员":
                user.setRoleId((short) 1);
                break;
            case "退役成员":
                user.setRoleId((short) 2);
                break;
            case "预备役":
                user.setRoleId((short) 3);
                break;
        }
        Long id = user.getId();
        User user1 = userMapper.getUserById(id);
        userMapper.deleteById(id);
        user.setCreateTime(user1.getCreateTime());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.addUserWithId(user);
        return 0;
    }

    @Override
    public User getUserById(Long id) {
        User user = userMapper.getUserById(id);
        log.info("The query user is: {}", user);
        return user;
    }

    @Override
    @DeleteRedis(key = redisKey, id = false)
    public int updatePwd(String prePwd, String nowPwd) {
        User user = getUserById(ThreadLocalUtil.getCurrentUser());
        if (StringUtils.equals(user.getPassword(), prePwd)) {
            userMapper.updateNowPwd(user.getId(), nowPwd);
            return 1;
        }
        return -1;
    }

    @Override
    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    @Override
    @DeleteRedis(key = "gxuicpc",id = false)
    public int updateUser(User user) {
        return userMapper.update(user);
    }

    private List<UserVO> getAllUsers(UserPage userPage) {
        PageHelper.startPage(userPage.getCurrentPage(), userPage.getPageSize());
        List<User> users = userMapper.getAll(userPage);
        return userToUserVO(users);
    }

    private List<UserVO> userToUserVO(List<User> users) {
        List<UserVO> userVOS = new ArrayList<>();
        users.forEach(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            switch (user.getRoleId()) {
                case 0:
                    userVO.setRoleId(RoleId.MANAGER.getRole());
                    break;
                case 1:
                    userVO.setRoleId(RoleId.ACTIVE.getRole());
                    break;
                case 2:
                    userVO.setRoleId(RoleId.RETIRE.getRole());
                    break;
                case 3:
                    userVO.setRoleId(RoleId.PREPARATION.getRole());
                    break;
            }
            userVO.setCreateTime(user.getCreateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
            userVO.setUpdateTime(user.getUpdateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
            userVOS.add(userVO);
        });
        return userVOS;
    }

}

