package kz.kasya.cheena.AttendMe.services;

import kz.kasya.cheena.AttendMe.exceptions.ServiceException;
import kz.kasya.cheena.AttendMe.models.entities.Attendance;

import java.util.List;


public interface AttendanceService {

    Attendance findById(Long id) throws ServiceException;
    List<Attendance> findAll();
    List<Attendance> findAllWithDeleted();
    Attendance update(Attendance attendance) throws ServiceException ;
    Attendance save(Attendance attendance) throws ServiceException ;
    void delete(Attendance attendance) throws ServiceException ;
    void deleteById(Long id) throws ServiceException ;
    List<Attendance> findAllByDeletedAtIsNullAndSessionId(Long id);
    public Attendance findByDeviceIdAndSessionId(String deviceId,Long sessionId) throws ServiceException;

}
