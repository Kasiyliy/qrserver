package kz.kasya.cheena.AttendMe.models.mappers;

import kz.kasya.cheena.AttendMe.models.dtos.QrDto;
import kz.kasya.cheena.AttendMe.models.entities.Qr;
import kz.kasya.cheena.AttendMe.shared.utils.mappers.AbstractModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class QrMapper extends AbstractModelMapper<Qr, QrDto> {

    private ModelMapper modelMapper;

    @Autowired
    public QrMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public QrDto toDto(Qr qr) {
        return modelMapper.map(qr, QrDto.class);
    }

    @Override
    public Qr toEntity(QrDto qrDto) {
        return modelMapper.map(qrDto, Qr.class);
    }

    @Override
    public List<QrDto> toDtoList(List<Qr> qrs) {
        return qrs.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Qr> toEntityList(List<QrDto> qrDtos) {
        return qrDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
