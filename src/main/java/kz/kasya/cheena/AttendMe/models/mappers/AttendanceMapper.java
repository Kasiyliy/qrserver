package kz.kasya.cheena.AttendMe.models.mappers;

import kz.kasya.cheena.AttendMe.models.dtos.AttendanceDto;
import kz.kasya.cheena.AttendMe.models.entities.Attendance;
import kz.kasya.cheena.AttendMe.shared.utils.mappers.AbstractModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class AttendanceMapper extends AbstractModelMapper<Attendance, AttendanceDto> {

    private ModelMapper modelMapper;

    @Autowired
    public AttendanceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AttendanceDto toDto(Attendance attendance) {
        return modelMapper.map(attendance, AttendanceDto.class);
    }

    @Override
    public Attendance toEntity(AttendanceDto attendanceDto) {
        return modelMapper.map(attendanceDto, Attendance.class);
    }

    @Override
    public List<AttendanceDto> toDtoList(List<Attendance> attendances) {
        return attendances.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Attendance> toEntityList(List<AttendanceDto> attendanceDtos) {
        return attendanceDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
