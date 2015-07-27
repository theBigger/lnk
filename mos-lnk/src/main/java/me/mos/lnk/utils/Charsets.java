package me.mos.lnk.utils;

import java.nio.charset.Charset;

/**
 * 
 * @author 刘飞
 * 
 * @version 1.0.0
 * @since 2015年3月24日 下午3:49:09
 */
public interface Charsets {
	String ISO_8859_1_NAME = "ISO-8859-1";
	
	Charset ISO_8859_1 = Charset.forName(ISO_8859_1_NAME);

	String US_ASCII_NAME = "US-ASCII";
	
	Charset US_ASCII = Charset.forName(US_ASCII_NAME);

	String UTF_16_NAME = "UTF-16";
	
	Charset UTF_16 = Charset.forName(UTF_16_NAME);

	String UTF_16BE_NAME = "UTF-16BE";
	
	Charset UTF_16BE = Charset.forName(UTF_16BE_NAME);

	String UTF_16LE_NAME = "UTF-16LE";
	
	Charset UTF_16LE = Charset.forName(UTF_16LE_NAME);

	String UTF_8_NAME = "UTF-8";
	
	Charset UTF_8 = Charset.forName(UTF_8_NAME);

	String GB2312_NAME = "GB2312";
	
	Charset GB2312 = Charset.forName(GB2312_NAME);

	String GBK_NAME = "GBK";
	
	Charset GBK = Charset.forName(GBK_NAME);

	String GB18030_NAME = "GB18030";
	
	Charset GB18030 = Charset.forName(GB18030_NAME);
}