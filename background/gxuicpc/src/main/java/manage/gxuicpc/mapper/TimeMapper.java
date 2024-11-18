package manage.gxuicpc.mapper;

import manage.gxuicpc.entity.TimeInterval;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TimeMapper {
    @Update("update time_interval set start_time = #{startTime}, end_time = #{endTime}")
    int update(TimeInterval timeInterval);

    @Select("select * from time_interval")
    TimeInterval getTime();
}
