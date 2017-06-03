package com.missu.Bean;


import com.missu.Util.MyTime;
import com.missu.Util.ProtocalObj;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;


@Entity
public class QQMessage extends ProtocalObj {
    @Property
	public String type = QQMessageType.MSG_TYPE_CHAT_P2P;
    @Property
	public String from = "0L";// sender account
    @Property
	public String fromNick = "";// sender nick name
    @Property
	public String fromAvatar = "1";// sendenr avater
    @Property
	public String to = "0L"; // reveiver account
    @Property
	public String content = ""; // missage content
    @Property
	public String sendTime = MyTime.geTime(); // sending time
				@Generated(hash = 277108919)
				public QQMessage(String type, String from, String fromNick, String fromAvatar, String to,
						String content, String sendTime) {
					this.type = type;
					this.from = from;
					this.fromNick = fromNick;
					this.fromAvatar = fromAvatar;
					this.to = to;
					this.content = content;
					this.sendTime = sendTime;
				}
				@Generated(hash = 1844318247)
				public QQMessage() {
				}
				public String getType() {
					return this.type;
				}
				public void setType(String type) {
					this.type = type;
				}
				public String getFrom() {
					return this.from;
				}
				public void setFrom(String from) {
					this.from = from;
				}
				public String getFromNick() {
					return this.fromNick;
				}
				public void setFromNick(String fromNick) {
					this.fromNick = fromNick;
				}
				public String getFromAvatar() {
					return this.fromAvatar;
				}
				public void setFromAvatar(String fromAvatar) {
					this.fromAvatar = fromAvatar;
				}
				public String getTo() {
					return this.to;
				}
				public void setTo(String to) {
					this.to = to;
				}
				public String getContent() {
					return this.content;
				}
				public void setContent(String content) {
					this.content = content;
				}
				public String getSendTime() {
					return this.sendTime;
				}
				public void setSendTime(String sendTime) {
					this.sendTime = sendTime;
				}
}
