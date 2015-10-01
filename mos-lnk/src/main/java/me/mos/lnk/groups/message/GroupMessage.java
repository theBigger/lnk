package me.mos.lnk.groups.message;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年9月30日 下午11:46:54
 */
public class GroupMessage {

	private long id;
	
	/** 发起报文的用户的唯一ID */
	private long mid;
	
	/** 第三方系统账号ID */
	private String party_id;

	/** 用户昵称 */
	private String nick;

	/** 用户头像 */
	private String avatar;
	
	/** 消息到达方的组的唯一ID */
	private long group_id;
    
    /** 消息到达方的组的成员的唯一ID */
    private long tid;

	/** 消息内容体 */
	private String body;

	/** 消息发送时间 */
	private long gmt_created;

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public String getParty_id() {
        return party_id;
    }

    public void setParty_id(String party_id) {
        this.party_id = party_id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getGmt_created() {
        return gmt_created;
    }

    public void setGmt_created(long gmt_created) {
        this.gmt_created = gmt_created;
    }
}