package cn.yusiwen.spring.commons.security.core.jwt;

import java.util.Map;

public class JwtPayLoad {

    private String userId;
    private String account;
    private Map<String, Object> extra;

    public JwtPayLoad() {
    }

    public JwtPayLoad(String userId, String account) {
        this.userId = userId;
        this.account = account;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }
}
