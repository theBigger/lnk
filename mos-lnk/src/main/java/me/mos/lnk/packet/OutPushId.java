package me.mos.lnk.packet;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 用户上传Push ID.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 下午11:21:27
 */
@XStreamAlias(Alias.PUSH_ID_NAME)
public class OutPushId extends AbstractOutPacket {

	private static final byte ERR = 2;

	private static final byte OK = 1;
	
	@XStreamAlias("status")
	@XStreamAsAttribute
	private byte status;
	
	public OutPushId() {
		super(Type.PushId.type);
	}

	public OutPushId ok() {
		status = OK;
		return this;
	}
	
	public OutPushId err() {
		status = ERR;
		return this;
	}

	@Override
	public Type type() {
		return Type.PushId;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
}