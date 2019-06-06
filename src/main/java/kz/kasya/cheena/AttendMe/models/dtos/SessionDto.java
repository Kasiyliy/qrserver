package kz.kasya.cheena.AttendMe.models.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.kasya.cheena.AttendMe.models.dtos.base.BaseDto;
import kz.kasya.cheena.AttendMe.models.entities.Qr;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(description = "Сущность которая описывает модель Сессии")
public class SessionDto extends BaseDto {

    @ApiModelProperty(notes = "Пользователь", readOnly = true)
    private UserDto user;

    @ApiModelProperty(notes = "QR", readOnly = true)
    private Qr qr;

    @ApiModelProperty(notes = "Долгота", readOnly = true)
    private String longitude;

    @ApiModelProperty(notes = "Широта", readOnly = true)
    private String latitude;

    @ApiModelProperty(notes = "Высота", readOnly = true)
    private String altitude;
}
