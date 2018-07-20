package com.trs.core.util;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * Created by epro on 2018/5/2.
 */
public class EmailUtil {

    private final static String emailUser = Config.getKey("email.user");
    private final static  String emailPass = Config.getKey("email.pass");
    private final static  String emailHost = Config.getKey("email.host");
    private final static  String emailPort = Config.getKey("email.port");


    /**
     * 邮箱发送
     * @param sendEmail 收件人
     * @param sendMsg  信息
     * @param title 标题
     * @return
     */
    public static boolean sendEmail(String sendEmail,String sendMsg, String title){
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", emailHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
//        //端口配置信息
//        props.setProperty("mail.smtp.port", emailPort);
//        props.setProperty("mail.smtp.ssl.enable", "true");
//        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.setProperty("mail.smtp.socketFactory.fallback", "false");
//        props.setProperty("mail.smtp.socketFactory.port", emailPort);


        Session session = Session.getInstance(props);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(emailUser));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(sendEmail, "UTF-8"));
            message.setSubject(title, "UTF-8");
            message.setContent(sendMsg, "text/html;charset=UTF-8");
            message.setSentDate(new Date());
            message.saveChanges();
            Transport transport = session.getTransport();
            transport.connect(emailUser, emailPass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return false;
    }

}
