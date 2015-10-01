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
public class InJoinGroup extends AbstractInPacket {
    
    /** 创建者唯一ID */
    @XStreamAlias("mid")
    @XStreamAsAttribute
    private long mid;
    
    /** 聊天组ID */
    @XStreamAlias("group-id")
    @XStreamAsAttribute
    private long group_id;
    
    /** 聊天组动作 1:加入 0:退出 */
    @XStreamAlias("action")
    @XStreamAsAttribute
    private byte action;
    
	public InJoinGroup() {
		super(Type.JoinGroup.type);
	}
	
	@Override
	public OutJoinGroup toOutPacket() {
	    OutJoinGroup outJoinGroup = new OutJoinGroup();
	    outJoinGroup.setMid(mid);
		return outJoinGroup.ok();
	}

	@Override
	public Type type() {
		return Type.JoinGroup;
	}

	public byte getAction() {
        return action;
    }

    public void setAction(byte action) {
        this.action = action;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    @Override
    public long getMid() {
        return mid;
    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }
}