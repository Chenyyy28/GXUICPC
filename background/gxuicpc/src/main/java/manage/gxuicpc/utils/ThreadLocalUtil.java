package manage.gxuicpc.utils;

public class ThreadLocalUtil {
    /**
     * 保存用户对象的ThreadLocal  在拦截器操作 添加、删除相关用户数据
     */
    private static final ThreadLocal<Long> userIdThreadLocal = new ThreadLocal<Long>();

    /**
     * 添加当前登录用户方法  在拦截器方法执行前调用设置获取用户
     * @param id
     */
    public static void addCurrentUser(Long id){
        userIdThreadLocal.set(id);
    }

    /**
     * 获取当前登录用户方法
     * @return
     */
    public static final Long getCurrentUser(){
        return userIdThreadLocal.get();
    }


    /**
     * 删除当前登录用户方法  在拦截器方法执行后 移除当前用户对象
     * return
     */
    public static void remove(){
        userIdThreadLocal.remove();
    }
}
