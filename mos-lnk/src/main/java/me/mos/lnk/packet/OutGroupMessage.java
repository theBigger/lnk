package me.mos.lnk.packet;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 聊天组通讯消息报文定义.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午9:46:53
 */
@XStreamAlias(Alias.GROUP_MESSAGE_NAME)
public class OutGroupMessage extends AbstractOutPacket {
	
	private static final byte ERR = 2;

	private static final byte OK = 1;

	/** 第三方系统账号ID */
	@XStreamAlias("party-id")
	@XStreamAsAttribute
	private String party_id;

	/** 用户昵称 */
	@XStreamAlias("nick")
	private String nick;

	/** 用户头像 */
	@XStreamAlias("avatar")
	private String avatar;
	
	/** 消息到达方的聊天组的唯一ID */
    @XStreamAlias("group-id")
    @XStreamAsAttribute
    private long groupId;

	/** 消息内容体 */
	@XStreamAlias("body")
	private String body;

	/** 消息发送时间 */
	@XStreamAlias("gmt-created")
	@XStreamAsAttribute
	private long gmt_created;
	
	@XStreamAlias("status")
	@XStreamAsAttribute
	private byte status;
	
	public OutGroupMessage() {
		super(Type.GroupMessage.type);
	}

	public OutGroupMessage ok() {
		status = OK;
		return this;
	}
	
	public OutGroupMessage err() {
		status = ERR;
		return this;
	}

	@Override
	public Type type() {
		return Type.GroupMessage;
	}

	public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
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

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
}