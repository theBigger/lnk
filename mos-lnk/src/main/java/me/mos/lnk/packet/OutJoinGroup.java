package me.mos.lnk.packet;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 用户加入群聊.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 下午11:21:27
 */
@XStreamAlias(Alias.JOIN_GROUP_NAME)
public class OutJoinGroup extends AbstractOutPacket {

	private static final byte ERR = 2;

	private static final byte OK = 1;
	
	@XStreamAlias("status")
	@XStreamAsAttribute
	private byte status;
	
	public OutJoinGroup() {
		super(Type.JoinGroup.type);
	}

	public OutJoinGroup ok() {
		status = OK;
		return this;
	}
	
	public OutJoinGroup err() {
		status = ERR;
		return this;
	}

	@Override
	public Type type() {
		return Type.JoinGroup;
	}

    public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
}