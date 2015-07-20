package org.mos.lnk.srv;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年6月12日 上午10:28:28
 */
public enum Version {

	VERSION_NULL((byte) 0, "无效版本"),

	VERSION_1_0_0((byte) 1, "version-1.0.0")
	;

	private final byte id;

	private final String name;

	private Version(byte id, String name) {
		this.id = id;
		this.name = name;
	}

	public static Version parse(byte id) {
		for (Version v : values()) {
			if (v.id == id) {
				return v;
			}
		}
		return VERSION_NULL;
	}

	public byte getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}