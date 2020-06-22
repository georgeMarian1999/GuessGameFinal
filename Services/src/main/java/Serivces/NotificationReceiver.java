package Serivces;

public interface NotificationReceiver {
    void start(NotificationSubscriber notificationSubscriber);
    void stop();
    void setSubscriber(NotificationSubscriber notificationSubscriber);
}
