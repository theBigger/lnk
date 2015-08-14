package me.mos.lnk.packet;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 用户在线状态心跳检测消息报文.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 上午1:06:02
 */
@XStreamAlias(Alias.IQ_NAME)
public class InIQ extends AbstractInPacket {
	
	/** 发起报文的用户的唯一ID */
	@XStreamAlias("mid")
	@XStreamAsAttribute
	private long mid;

	public InIQ() {
		super(Type.IQ.type);
	}

	@Override
	public OutIQ toOutPacket() {
		OutIQ outIQ = new OutIQ();
		outIQ.setMid(mid);
		return outIQ;
	}

	@Override
	public Type type() {
		return Type.IQ;
	}

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}
}