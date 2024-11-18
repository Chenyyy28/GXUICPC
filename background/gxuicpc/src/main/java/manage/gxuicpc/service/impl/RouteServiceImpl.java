package manage.gxuicpc.service.impl;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.annotation.QueryRedis;
import manage.gxuicpc.entity.Route;
import manage.gxuicpc.entity.User;
import manage.gxuicpc.mapper.RouteMapper;
import manage.gxuicpc.mapper.UserMapper;
import manage.gxuicpc.service.RouteService;
import manage.gxuicpc.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteMapper routeMapper;
    @Autowired
    private UserMapper userMapper;
    private static final String redisKey = RedisUtils.KEY + "routes:";

    @QueryRedis(key = redisKey, id = true, resType = List.class)
    @Override
    public List<Route> getAllById(Long id) {
        List<Route> routes = routeMapper.getAll();
        List<Route> resRoutes = new ArrayList<>();
        //获取用户身份：
        int role = userMapper.getUserRoleById(id);
        routes.forEach(route -> {
            if (routeContainId(route, role)) {
                resRoutes.add(route);
            }
        });

        return resRoutes;
    }

    private boolean routeContainId(Route route, Integer id) {
        if (StringUtils.contains(route.getRole(), String.valueOf(id))) {
            return true;
        }
        return false;
    }
}
