package manage.gxuicpc.common;

import org.apache.ibatis.annotations.Update;

public enum ErrorMessage {

    LOGIN_EXPIRED(403, "登录信息无效，请重新登录"),
    DELETE_FAILED(402, "删除失败"),
    ADD_FAILED(402, "添加失败"),
    UPDATE_FAILED(402,"修改失败"),
    UPDATE_PWD_FAILED(400,"修改密码错误：原密码错误")
    ;


    private int code;
    private String msg;

    ErrorMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
