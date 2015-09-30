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
@XStreamAlias(Alias.GROUP_NAME)
public class OutGroup extends AbstractOutPacket {

	private static final byte ERR = 2;

	private static final byte OK = 1;
    
    /** 聊天组ID */
    @XStreamAlias("group-id")
    @XStreamAsAttribute
    private long groupId;
	
	@XStreamAlias("status")
	@XStreamAsAttribute
	private byte status;
	
	public OutGroup() {
		super(Type.Group.type);
	}

	public OutGroup ok() {
		status = OK;
		return this;
	}
	
	public OutGroup err() {
		status = ERR;
		return this;
	}

	@Override
	public Type type() {
		return Type.Group;
	}

	public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
}