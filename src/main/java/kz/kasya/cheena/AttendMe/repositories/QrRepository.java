package kz.kasya.cheena.AttendMe.repositories;

import kz.kasya.cheena.AttendMe.models.entities.Qr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QrRepository extends JpaRepository<Qr, Long>{
    List<Qr> findAllByDeletedAtIsNull();
}
