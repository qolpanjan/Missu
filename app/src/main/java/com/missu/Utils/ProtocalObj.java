package com.missu.Utils;

import com.thoughtworks.xstream.XStream;

/**
 * Created by alimj on 2017/4/21.
 */


public class ProtocalObj {
    /**
     * 生成xml
     *
     * @return
     */
    public String toXML() {
        XStream x = new XStream();
        // 设置别名，默认是类的全路径名
        x.alias(getClass().getSimpleName(), getClass());
        String xml = x.toXML(this);// 将本类转换成xml返回
        return xml;

    }

    /**
     * xml-->实体类
     *
     * @param xml
     * @return
     */
    public Object fromXML(String xml) {
        XStream x = new XStream();
        x.alias(getClass().getSimpleName(), getClass());
        return x.fromXML(xml);

    }
}
