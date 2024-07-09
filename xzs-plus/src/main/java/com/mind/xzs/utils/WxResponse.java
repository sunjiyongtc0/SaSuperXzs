package com.mind.xzs.utils;


import java.io.Serializable;


public class WxResponse implements Serializable {
    private static final long serialVersionUID = -8496869159673561976L;
    private String session_key;
    private String openid;

    /**
     * Gets serial version uid.
     *
     * @return the serial version uid
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * Gets session key.
     *
     * @return the session key
     */
    public String getSession_key() {
        return session_key;
    }

    /**
     * Sets session key.
     *
     * @param session_key the session key
     */
    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    /**
     * Gets openid.
     *
     * @return the openid
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * Sets openid.
     *
     * @param openid the openid
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
