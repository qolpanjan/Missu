package com.missu.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Friend info including account ,nick,avater,sex
 */
@Entity
public class ContactInfo {
	// "account": 101,
	// "avatar": 0,
	@Id
	public Long id=0L;
	@Property
	public String account;
	@Property
	public String nick = "";
	@Property
	public String avatar;
	@Property
	public String sex = "";
	@Generated(hash = 291751710)
	public ContactInfo(Long id, String account, String nick, String avatar,
			String sex) {
		this.id = id;
		this.account = account;
		this.nick = nick;
		this.avatar = avatar;
		this.sex = sex;
	}
	@Generated(hash = 2019856331)
	public ContactInfo() {
	}
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccount() {
		return this.account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getNick() {
		return this.nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getAvatar() {
		return this.avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getSex() {
		return this.sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

}
