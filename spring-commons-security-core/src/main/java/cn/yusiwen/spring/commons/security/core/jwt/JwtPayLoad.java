package cn.yusiwen.spring.commons.security.core.jwt;

import java.util.Map;
import java.util.UUID;

public class JwtPayLoad {

    private String userId;
    private String account;
    private String uuid;
    private Map<String, Object> extra;

    public JwtPayLoad() {
    }

    public JwtPayLoad(String userId, String account) {
        this.userId = userId;
        this.account = account;
        this.uuid = UUID.randomUUID().toString();
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }
}
