package com.example.System.Repository;

import com.example.System.DTO.Student.Dashboard.NotificationsDTO;
import com.example.System.Entity.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("""
SELECT new com.example.System.DTO.Student.Dashboard.NotificationsDTO(
    n.id,
    n.title,
    n.message,
    n.createdAt
)
FROM Notification n
ORDER BY n.createdAt DESC
""")
    List<NotificationsDTO> getNotificationDTOs(Pageable pageable);
}
