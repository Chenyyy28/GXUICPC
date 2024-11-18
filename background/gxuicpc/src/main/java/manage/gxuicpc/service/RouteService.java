package manage.gxuicpc.service;

import manage.gxuicpc.entity.Route;

import java.util.List;

public interface RouteService {
//    List<Route> getAll();

    List<Route> getAllById(Long currentUser);
}
