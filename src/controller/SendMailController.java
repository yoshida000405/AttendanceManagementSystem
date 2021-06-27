package controller;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.entity.Staff;

/**
 * Controller implementation class SendMailController
 * メール送信をするクラス。
 */
public class SendMailController {
	// 送信者アドレスと送信者名(SESコンソールで認証したメールアドレス)
	static final String FROM = "rigarejapan.managed@gmail.com";
	static final String FROMNAME = "RigareJapan";

	// SESコンソールで作成したSMTPユーザー名とパスワード
	static final String SMTP_USERNAME = "AKIASEZOMPQVHFHXOX5S";
	static final String SMTP_PASSWORD = "BFUDO7QhmKGEGHkhWQIcvms7juU2CDQCRj9ubzaEOPbC";

	// SESコンソールで作成したConfig Setを指定。メールログの保存をするときなどに使用する。今回は不要なのでコメントアウト
	// static final String CONFIGSET = "ConfigSet";

	// Amazon SES SMTPのエンドポイント(リージョンがオレゴンの場合はus-west-2)
	static final String HOST = "email-smtp.ap-northeast-1.amazonaws.com";

	// Amazon SES SMTPのエンドポイントのポート番号.
	static final int PORT = 587;

	/**
	 * @param subject - 件名
	 * @param content - 本文
	 * @param address - 送信アドレス
	 * @param staff - スタッフのモデルクラスのインスタンス
	 * @param index - 送信件数判定
	 * @return 送信が成功し場合true、失敗した場合false。
	 * 指定されたyearとmonthから月末日を取得する。
	 * @throws MessagingException
	 * @throws IOException
	 * @throws SecurityException
	 */
	public boolean send(String[] subject, String[] content, String[] address, Staff staff, int index, Logger logger)
			throws MessagingException, SecurityException, IOException {

		// SMTPサーバーを定義
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		// メールセッションの確立
		Session session = Session.getDefaultInstance(props);

		// メール作成
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(FROM, FROMNAME));

		// Configuration Setを設定。今回は使用しないのでコメントアウト
		//msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

		if (index == 0) {
			index = staff.getNumbersRecords();
		}

		for (int i = 0; i < index; i++) {

			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(address[i]));
			msg.setSubject(subject[i], "iso-2022-jp");
			msg.setText(content[i], "iso-2022-jp");

			Transport transport = session.getTransport();

			// メール送信
			try {
				System.out.println("Sending...");

				// SMTPサーバに接続
				transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

				// メール送信
				transport.sendMessage(msg, msg.getAllRecipients());

			} catch (Exception e) {
				logger.log(Level.WARNING, "[SendMailController.java]" + e.toString(), e.getMessage());
				return false;
			} finally {
				// 接続終了
				transport.close();
			}
		}
		return true;
	}

	/**
	 * @param subject - 件名
	 * @param content - 本文
	 * @param address - 送信アドレス
	 * @param staff - スタッフのモデルクラスのインスタンス
	 * @param index - 送信件数判定
	 * @return 送信が成功し場合true、失敗した場合false。
	 * 指定されたyearとmonthから月末日を取得する。
	 * @throws MessagingException
	 * @throws IOException
	 * @throws SecurityException
	 */
	public boolean send(String subject, String content, String[] address, Staff staff, int index, Logger logger)
			throws MessagingException, SecurityException, IOException {

		// SMTPサーバーを定義
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		// メールセッションの確立
		Session session = Session.getDefaultInstance(props);

		// メール作成
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(FROM, FROMNAME));
		msg.setSubject(subject, "iso-2022-jp");
		msg.setText(content, "iso-2022-jp");

		// Configuration Setを設定。今回は使用しないのでコメントアウト
		//msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

		if (index == 0) {
			index = staff.getNumbersRecords();
		}

		for (int i = 0; i < index; i++) {

			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(address[i]));
			msg.setHeader("Content-Transfer-Encoding", "7bit");

			Transport transport = session.getTransport();

			// メール送信
			try {
				// SMTPサーバに接続
				transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
				// メール送信
				transport.sendMessage(msg, msg.getAllRecipients());
			} catch (Exception e) {
				logger.log(Level.WARNING, "[SendMailController.java]" + e.toString(), e.getMessage());
				return false;
			} finally {
				// 接続終了
				transport.close();
			}
		}
		return true;
	}

}
