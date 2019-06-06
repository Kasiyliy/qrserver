package kz.kasya.cheena.AttendMe.models.entities;

import kz.kasya.cheena.AttendMe.models.audits.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session extends AuditModel {

    @ManyToOne
    @NotNull(message = "user is required")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private User user;

    @ManyToOne
    @NotNull(message = "qr is required")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Qr qr;

    @NotNull(message = "longitude is required")
    private String longitude;

    @NotNull(message = "latitude is required")
    private String latitude;

    @NotNull(message = "altitude is required")
    private String altitude;
}
