package Implementations;

import Notification.Notification;
import Notification.NotificationType;
import Serivces.INotificationService;
import org.springframework.jms.core.JmsOperations;

import java.sql.SQLOutput;

public class NotificationService implements INotificationService {
    private JmsOperations jmsOperations;

    public NotificationService(JmsOperations operations){
        this.jmsOperations=operations;
    }
    @Override
    public void startJoc(String litera) {
        System.out.println("Sending start joc notification");
        Notification notification=new Notification(NotificationType.STARTGAME,litera);
        jmsOperations.convertAndSend(notification);
        System.out.println("Notification about start game sent to activemq");

    }

    @Override
    public void getPropuneri() {
        Notification notification=new Notification(NotificationType.GETPROPTAB);
        jmsOperations.convertAndSend(notification);
    }

    @Override
    public void punctaj() {
        System.out.println("Sending punctaj notification");
        Notification notification=new Notification(NotificationType.PUNCTAJ);
        jmsOperations.convertAndSend(notification);
        System.out.println("Notification about punctaj sent to activemq");
    }

    @Override
    public void stopJoc() {
        System.out.println("Sending stop joc notification");
        Notification notification=new Notification(NotificationType.STOPGAME);
        jmsOperations.convertAndSend(notification);
        System.out.println("Notification about stop game sent");
    }

    @Override
    public void leaver() {
        System.out.println("Sending leaver notification");
        Notification notification=new Notification(NotificationType.LEAVER);
        jmsOperations.convertAndSend(notification);
        System.out.println("Notification about leaver sent");

    }
}
