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
public class InGroupMessage extends AbstractInPacket {
	
	/** 发起报文的用户的唯一ID */
	@XStreamAlias("mid")
	@XStreamAsAttribute
	private long mid;
	
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

	public InGroupMessage() {
		super(Type.GroupMessage.type);
	}

	@Override
	public OutGroupMessage toOutPacket() {
	    OutGroupMessage outMessage = new OutGroupMessage();
		outMessage.setBody(body);
		outMessage.setGmt_created(gmt_created);
		outMessage.setMid(mid);
		outMessage.setGroupId(groupId);
		return outMessage.ok();
	}

	@Override
	public Type type() {
		return Type.GroupMessage;
	}

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
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
}