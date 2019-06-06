package kz.kasya.cheena.AttendMe.services;

import kz.kasya.cheena.AttendMe.exceptions.ServiceException;
import kz.kasya.cheena.AttendMe.models.entities.Qr;
import kz.kasya.cheena.AttendMe.models.entities.Session;

import java.util.List;


public interface SessionService {

    Session findById(Long id) throws ServiceException;
    List<Session> findAll();
    Session findFirstByQrId(Long qrId) throws ServiceException;
    List<Session> findAllWithDeleted();
    Session update(Session session) throws ServiceException ;
    Session save(Session session) throws ServiceException ;
    void delete(Session session) throws ServiceException ;
    void deleteById(Long id) throws ServiceException ;

}
