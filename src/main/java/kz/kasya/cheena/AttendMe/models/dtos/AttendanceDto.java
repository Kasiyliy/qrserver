package kz.kasya.cheena.AttendMe.models.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.kasya.cheena.AttendMe.models.audits.AuditModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(description = "Сущность которая описывает модель посещения")
public class AttendanceDto extends AuditModel {

    @ApiModelProperty(notes = "Пользователь", readOnly = true)
    private UserDto user;

    @ApiModelProperty(notes = "Сессия", readOnly = true)
    private SessionDto session;

    @ApiModelProperty(notes = "Долгота", readOnly = true)
    private String longitude;

    @ApiModelProperty(notes = "Широта", readOnly = true)
    private String latitude;

    @ApiModelProperty(notes = "Высота", readOnly = true)
    private String altitude;

    @ApiModelProperty(notes = "Уникальный идентификатор устройства", readOnly = true)
    private String deviceId;
}
