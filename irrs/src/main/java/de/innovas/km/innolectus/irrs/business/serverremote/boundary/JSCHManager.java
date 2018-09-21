package de.innovas.km.innolectus.irrs.business.serverremote.boundary;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

import de.innovas.km.innolectus.irrs.applicationconfiguration.ConfigProperties;

/**
 * 
 * @author janasd
 * Manager/Facade für JSCH, prepariert die Connection und ruft ein command auf dem Remote rechner auf
 */

public class JSCHManager {

	private final static Logger LOGGER = LogManager.getLogger(JSCHManager.class.getName());

	ChannelExec channel;
	Session session;
	
	private Session getSession() {
		return session;
	}

	private void setSession(Session session) {
		this.session = session;
	}

	private ChannelExec getChannel() {
		return channel;
	}

	private void setChannel(ChannelExec channel) {
		this.channel = channel;
	}

	/**
	 * 
	 * @param user
	 * @param host
	 * @return this
	 */
	public static JSCHManager prepareCommand(String user, String host) {

		String privateKey = ConfigProperties.getPropertyValue(ConfigProperties.PRIVATE_RSA_KEY_FILE);

		JSch jsch = new JSch();

		JSCHManager result = new JSCHManager();

		try {
			jsch.addIdentity(privateKey);
		} catch (JSchException e) {
			e.printStackTrace();
			LOGGER.error("addPrivateKey: " + e.getMessage());
		}

		try {
			result.setSession(jsch.getSession(user, host, 22));
		} catch (JSchException e) {
			e.printStackTrace();
			result = null;
			LOGGER.error("getSession: " + user + " " + host + e.getMessage());
		}

		UserInfo ui = new MyUserInfo();
		result.getSession().setUserInfo(ui);

		Properties prop = new Properties();
		prop.put("StrictHostKeyChecking", "no");
		result.getSession().setConfig(prop);

		try {
			result.getSession().connect();
		} catch (JSchException e) {
			e.printStackTrace();
			result = null;
			LOGGER.error("session.connect: " + e.getMessage());
		}
		try {
			result.setChannel((ChannelExec) result.getSession().openChannel("exec"));
		} catch (JSchException e) {
			e.printStackTrace();
			result = null;
			LOGGER.error("openChannel set channel: " + e.getMessage());
		}

		return result;
	}	

	/**
	 * 
	 * @param command  zb aufruf eines skriptes  "lmaa.sh"
	 * @return
	 */
	public int callCommand(String command) {

		LOGGER.info("callCommand: " + command);

		int channelReturnStatus;
		/*
		 * need ready command
		 */
		channel.setCommand(command.getBytes());
		channel.setPty(false);
		channel.setInputStream(null);
		channel.setErrStream(System.err);

		if (channel.isClosed()) {
			LOGGER.error("channel ist geschlossen");
		}

		try {
			channel.connect();
			if (channel.isConnected()) {
				LOGGER.info("channel ist verbunden");
			}
		} catch (JSchException e) {
			e.printStackTrace();
			LOGGER.error("channel.connect not possible: " + e.getMessage());
			channelReturnStatus = channel.getExitStatus();
		}
		
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			LOGGER.info("JSCH calling FUCKED: " + e.getMessage());
		}

		channel.disconnect();
		session.disconnect();
		LOGGER.info("exit-status: " + channel.getExitStatus());
		
		channelReturnStatus = channel.getExitStatus();
		return channelReturnStatus;
	}
	
	public static class MyUserInfo implements UserInfo, UIKeyboardInteractive {

		@Override
		public String getPassphrase() {
			return null;
		}

		@Override
		public String getPassword() {
			return null;
		}

		@Override
		public boolean promptPassphrase(String arg0) {
			return false;
		}

		@Override
		public boolean promptPassword(String arg0) {
			return false;
		}

		@Override
		public boolean promptYesNo(String arg0) {
			return false;
		}

		@Override
		public void showMessage(String arg0) {
		}

		@Override
		public String[] promptKeyboardInteractive(String arg0, String arg1, String arg2, String[] arg3,
				boolean[] arg4) {
			return null;
		}
	}

}
