package me.mos.lnk.packet;

/**
 * 消息类型.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 上午12:52:54
 */
public enum Type {
	IQ((byte) 1), 
	Message((byte) 2), 
	Presence((byte) 3), 
	Register((byte) 4), 
	Acknowledge((byte) 5), 
	Revise((byte) 6),
	PushId((byte) 7),
    Group((byte) 8),
    GroupMessage((byte) 9)
	;

	public final byte type;

	private Type(byte type) {
		this.type = type;
	}

	public static Type parse(byte type) {
		for (Type t : values()) {
			if (t.type == type) {
				return t;
			}
		}
		return null;
	}
}