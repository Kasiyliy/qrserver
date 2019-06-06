package kz.kasya.cheena.AttendMe.repositories;

import kz.kasya.cheena.AttendMe.models.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
    List<Attendance> findAllByDeletedAtIsNull();

    List<Attendance> findAllByDeletedAtIsNullAndSessionId(Long id);

    Optional<Attendance> findOneByDeletedAtIsNullAndSessionIdAndDeviceId(Long sessionId, String deviceId);
    
}
