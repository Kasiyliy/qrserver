package kz.kasya.cheena.AttendMe.models.mappers;

import kz.kasya.cheena.AttendMe.models.dtos.SessionDto;
import kz.kasya.cheena.AttendMe.models.entities.Session;
import kz.kasya.cheena.AttendMe.shared.utils.mappers.AbstractModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class SessionMapper extends AbstractModelMapper<Session, SessionDto> {

    private ModelMapper modelMapper;

    @Autowired
    public SessionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SessionDto toDto(Session session) {
        return modelMapper.map(session, SessionDto.class);
    }

    @Override
    public Session toEntity(SessionDto sessionDto) {
        return modelMapper.map(sessionDto, Session.class);
    }

    @Override
    public List<SessionDto> toDtoList(List<Session> sessions) {
        return sessions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Session> toEntityList(List<SessionDto> sessionDtos) {
        return sessionDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
