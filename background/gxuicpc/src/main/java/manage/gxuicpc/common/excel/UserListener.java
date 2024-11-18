package manage.gxuicpc.common.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.annotation.DeleteRedis;
import manage.gxuicpc.entity.User;
import manage.gxuicpc.entity.dto.UserExcelDTO;
import manage.gxuicpc.mapper.UserMapper;
import manage.gxuicpc.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class UserListener implements ReadListener {
    @Autowired
    UserService userService;

    @Override
//    @DeleteRedis(key = "gxuicpc", id = false)
    public void invoke(Object o, AnalysisContext analysisContext) {
        UserExcelDTO userExcelDTO = (UserExcelDTO) o;
        User user1 = userService.getUserByName(userExcelDTO.getUsername());
        User user = new User();
        if (user1 != null) {
            BeanUtils.copyProperties(user1, user);
            if (StringUtils.equals(userExcelDTO.getRole(), "现役")) {
                user.setRoleId((short) 1);
            } else if (StringUtils.equals(userExcelDTO.getRole(), "预备役")) {
                user.setRoleId((short) 3);
            } else {
                user.setRoleId((short) 2);
            }
            user.setUpdateTime(LocalDateTime.now());
            user.setAccount(userExcelDTO.getAccount());
            user.setEmail(userExcelDTO.getEmail());
            userService.updateUser(user);
            log.info("the user: {} is exited, it will be update", user1.getUsername());
        } else {
            BeanUtils.copyProperties(userExcelDTO, user);
            if (StringUtils.equals(userExcelDTO.getRole(), "现役")) {
                user.setRoleId((short) 1);
            } else if (StringUtils.equals(userExcelDTO.getRole(), "预备役")) {
                user.setRoleId((short) 3);
            } else {
                user.setRoleId((short) 2);
            }
            user.setUpdateTime(LocalDateTime.now());
            user.setCreateTime(LocalDateTime.now());
            userService.addUser(user);
            log.info("the user: {} is not exit, it will be insert", user.getUsername());
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
