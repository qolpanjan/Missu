package com.missu.Bean;


import com.missu.Utils.Mytime;
import com.missu.Utils.ProtocalObj;
/**
 * Created by alimj on 2017/4/21.
 */

public class MessageBean extends ProtocalObj{



    public void setType(String type) {
        this.type = type;
    }

    public void setFrom(String from) {
        this.from = from;
    }



    public void setTo(String to) {
        this.to = to;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }


    public String getType() {
        return type;
    }

    public String getFrom() {
        return from;
    }




    public String getTo() {
        return to;
    }

    public String getContent() {
        return content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public MessageBean(){

    }

    public MessageBean(String type, String from, String to, String content, String sendTime){

        this.type = type;
        this.from = from;
        this.to = to;
        this.content = content;
        this.sendTime = sendTime;
    }

    private String type = MessageType.MSG_TYPE_CHAT_P2P;// 类型的数据 chat login
    private String from = "0";// 发送者 account
    private String to = "0"; // 接收者 account
    private String content = ""; // 消息的内容 :约不?
    private String sendTime = Mytime.geTime(); // 发送时间
}
