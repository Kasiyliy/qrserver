package kz.kasya.cheena.AttendMe.repositories;

import kz.kasya.cheena.AttendMe.models.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SessionRepository extends JpaRepository<Session,Long>{
    List<Session> findAllByDeletedAtIsNull();

    Session findFirstByQrIdOrderByCreatedAtDesc(Long qrId);
}
