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
public class InGroup extends AbstractInPacket {
    
    /** 创建者唯一ID */
    @XStreamAlias("mid")
    @XStreamAsAttribute
    private long mid;
    
    /** 聊天组名称 */
    @XStreamAlias("name")
    private String name;
    
    /** 聊天组标签 */
    @XStreamAlias("tags")
    private String tags;

	public InGroup() {
		super(Type.Group.type);
	}
	
	@Override
	public OutGroup toOutPacket() {
	    OutGroup outGroup = new OutGroup();
	    outGroup.setMid(mid);
		return outGroup.ok();
	}

	@Override
	public Type type() {
		return Type.Group;
	}

	public void setMid(long mid) {
        this.mid = mid;
    }

    @Override
    public long getMid() {
        return mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}