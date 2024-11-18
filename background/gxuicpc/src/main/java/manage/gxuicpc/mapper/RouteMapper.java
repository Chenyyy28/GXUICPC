package manage.gxuicpc.mapper;

import manage.gxuicpc.entity.Route;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RouteMapper {

    @Select("select * from route")
    List<Route> getAll();
}
