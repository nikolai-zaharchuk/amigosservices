package com.amigos.notification.service.access;

import com.amigos.notification.dao.NotificationDao;
import com.amigos.notification.entity.Notification;
import com.amigos.notification.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationDaoAccessService implements NotificationDao {
    private final NotificationRepository notificationRepository;
    @Override
    public void addNotification(Notification notification) {
        notificationRepository.save(notification);
    }
}
