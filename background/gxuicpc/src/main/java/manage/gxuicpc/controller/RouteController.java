package manage.gxuicpc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import manage.gxuicpc.common.Result;
import manage.gxuicpc.entity.Route;
import manage.gxuicpc.service.RouteService;
import manage.gxuicpc.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/route")
@Slf4j
@Tag(name = "动态路由接口", description = "获取路由和菜单信息")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping
    @Operation(summary = "获取路由列表")
    public Result<List<Route>> route() {
        log.info("the user: {} will get the routes",ThreadLocalUtil.getCurrentUser());
        List<Route> routes = routeService.getAllById(ThreadLocalUtil.getCurrentUser());
        return Result.success(routes);
    }
}
