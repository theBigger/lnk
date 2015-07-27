package test;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import me.mos.lnk.channel.Channels;
import me.mos.lnk.packet.Acknowledge;
import me.mos.lnk.packet.InIQ;
import me.mos.lnk.packet.InMessage;
import me.mos.lnk.packet.InPresence;
import me.mos.lnk.packet.InRegister;
import me.mos.lnk.packet.InRevise;
import me.mos.lnk.packet.OutIQ;
import me.mos.lnk.packet.OutMessage;
import me.mos.lnk.packet.OutPresence;
import me.mos.lnk.packet.OutRegister;
import me.mos.lnk.packet.OutRevise;
import me.mos.lnk.server.Server;
import me.mos.lnk.utils.Charsets;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午3:17:13
 */
public class Client {

	private static final String PASSWD = "123456";
	
	public static void main21212(String[] args) throws Exception {
		Pipe pipe = Pipe.open();
		Pipe.SinkChannel sinkChannel = pipe.sink();
		String newData = "New String to write to file..." + System.currentTimeMillis();
		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();
		buf.put(newData.getBytes());
		buf.flip();
		while(buf.hasRemaining()) {
		    sinkChannel.write(buf);
		}
		
		Pipe.SourceChannel sourceChannel = pipe.source();
		
		ByteBuffer r = ByteBuffer.allocate(48);

		int bytesRead = sourceChannel.read(r);
		
//		java.nio.channels.Channels.newChannel(null);
	}

	public static InMessage newInMessage() {
		InMessage inMessage = new InMessage();
		inMessage.setMid(2);
		inMessage.setTid(1);
		inMessage.setBody("你好啊");
		inMessage.setGmt_created(new Date().getTime());
		System.out.println("Sent : " + inMessage);
		return inMessage;
	}

	public static InPresence newInPresence() {
		InPresence inPresence = new InPresence();
		inPresence.setMid(2);
		inPresence.setPasswd(PASSWD);
		System.out.println("Sent : " + inPresence);
		return inPresence;
	}
	
	public static void main(String[] args) {
		System.out.println(newInIQ());
	}

	public static InIQ newInIQ() {
		InIQ inIQ = new InIQ();
		inIQ.setMid(1);
		return inIQ;
	}

	public static InRegister newInRegister() {
		InRegister inRegister = new InRegister();
		inRegister.setNick("大飞哥儿1");
		inRegister.setParty_id("3");
		inRegister.setPasswd(PASSWD);
		inRegister.setAvatar("头像地址");
		inRegister.setEmail("email");
		inRegister.setPhone("固话");
		inRegister.setQq("QQ");
		inRegister.setTelephone("手机号码");
		inRegister.setWeixin("微信");
		return inRegister;
	}

	public static InRevise newInRevise() {
		InRevise inRevise = new InRevise();
		inRevise.setMid(123L);
		inRevise.setNick("大飞哥儿1");
		inRevise.setParty_id("3");
		inRevise.setPasswd(PASSWD);
		inRevise.setAvatar("头像地址");
		inRevise.setEmail("email");
		inRevise.setPhone("固话");
		inRevise.setQq("QQ");
		inRevise.setTelephone("手机号码");
		inRevise.setWeixin("微信");
		return inRevise;
	}

	public static void main0ok(String[] args) {
		InRevise inRevise = newInRevise();
		System.out.println("inRevise : " + inRevise);
		OutRevise outRevise = inRevise.toOutPacket();
		System.out.println("outRevise : " + outRevise);
		InRegister inRegister = newInRegister();
		System.out.println("inRegister : " + inRegister);
		OutRegister outRegister = inRegister.toOutPacket();
		outRegister.setGmt_created(new Date().getTime());
		outRegister.setMid(123);
		System.out.println("outRegister : " + outRegister);
		InIQ inIQ = newInIQ();
		System.out.println("inIQ : " + inIQ);
		OutIQ outIQ = inIQ.toOutPacket();
		outIQ.online();
		System.out.println("outIQ : " + outIQ);
		InPresence inPresence = newInPresence();
		System.out.println("inPresence : " + inPresence);
		OutPresence outPresence = inPresence.toOutPacket();
		outPresence.ok();
		System.out.println("outPresence : " + outPresence);
		InMessage inMessage = newInMessage();
		System.out.println("inMessage : " + inMessage);
		System.out.println("ack : " + new Acknowledge(1).ok());
		OutMessage outMessage = inMessage.toOutPacket();
		outMessage.setAvatar("发送消息的人的头像");
		outMessage.setNick("发送消息的人的昵称");
		outMessage.setParty_id("发送消息的人的第三方绑定账号");
		System.out.println("outMessage : " + outMessage);
	}
}