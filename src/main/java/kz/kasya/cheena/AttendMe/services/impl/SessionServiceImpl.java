package kz.kasya.cheena.AttendMe.services.impl;


import kz.kasya.cheena.AttendMe.exceptions.ServiceException;
import kz.kasya.cheena.AttendMe.models.entities.Session;
import kz.kasya.cheena.AttendMe.repositories.SessionRepository;
import kz.kasya.cheena.AttendMe.services.SessionService;
import kz.kasya.cheena.AttendMe.shared.utils.codes.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Assylkhan
 * on 10.04.2019
 * @project logistic_server
 */
@Service
public class SessionServiceImpl implements SessionService {

    private SessionRepository sessionRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session findById(Long id) throws ServiceException {
        Optional<Session> sessionOptional = sessionRepository.findById(id);
        return sessionOptional.orElseThrow(() -> ServiceException.builder()
                .errorCode(ErrorCode.RESOURCE_NOT_FOUND)
                .message("session not found")
                .build());
    }

    @Override
    public List<Session> findAll() {
        return sessionRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public List<Session> findAllWithDeleted() {
        return sessionRepository.findAll();
    }

    @Override
    public Session update(Session session) throws ServiceException {
        if(session.getId() == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("session is null")
                    .build();
        }
        return  sessionRepository.save(session);
    }

    @Override
    public Session save(Session session) throws ServiceException {
        if(session.getId() != null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .message("session already exists")
                    .build();
        }
        return  sessionRepository.save(session);
    }

    @Override
    public void delete(Session session) throws ServiceException {
        if(session.getId() == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("session is null")
                    .build();
        }
        session = findById(session.getId());
        session.setDeletedAt(new Date());
        sessionRepository.save(session);
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        if(id == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        Session session = findById(id);
        session.setDeletedAt(new Date());
        sessionRepository.save(session);
    }

    @Override
    public Session findFirstByQrId(Long qrId) throws ServiceException {
        return sessionRepository.findFirstByQrIdOrderByCreatedAtDesc(qrId);
    }
}
