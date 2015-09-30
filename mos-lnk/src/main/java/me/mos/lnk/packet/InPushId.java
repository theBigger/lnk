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
public class InPushId extends AbstractInPacket {
	
	/** 发起报文的用户的唯一ID */
	@XStreamAlias("mid")
	@XStreamAsAttribute
	private long mid;

	/** 推送ID */
	@XStreamAlias("push-id")
	@XStreamAsAttribute
	private String push_id;

	public InPushId() {
		super(Type.PushId.type);
	}
	
	@Override
	public OutPushId toOutPacket() {
	    OutPushId outPush = new OutPushId();
		outPush.setMid(mid);
		return outPush.ok();
	}

	@Override
	public Type type() {
		return Type.PushId;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	@Override
	public long getMid() {
		return mid;
	}

    public String getPush_id() {
        return push_id;
    }

    public void setPush_id(String push_id) {
        this.push_id = push_id;
    }
}