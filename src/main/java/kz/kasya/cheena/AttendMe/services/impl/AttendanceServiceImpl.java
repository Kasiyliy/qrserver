package kz.kasya.cheena.AttendMe.services.impl;


import kz.kasya.cheena.AttendMe.exceptions.ServiceException;
import kz.kasya.cheena.AttendMe.models.entities.Attendance;
import kz.kasya.cheena.AttendMe.repositories.AttendanceRepository;
import kz.kasya.cheena.AttendMe.services.AttendanceService;
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
public class AttendanceServiceImpl implements AttendanceService {

    private AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public Attendance findById(Long id) throws ServiceException {
        Optional<Attendance> attendanceOptional = attendanceRepository.findById(id);
        return attendanceOptional.orElseThrow(() -> ServiceException.builder()
                .errorCode(ErrorCode.RESOURCE_NOT_FOUND)
                .message("attendance not found")
                .build());
    }

    @Override
    public List<Attendance> findAll() {
        return attendanceRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public List<Attendance> findAllWithDeleted() {
        return attendanceRepository.findAll();
    }

    @Override
    public Attendance update(Attendance attendance) throws ServiceException {
        if(attendance.getId() == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("attendance is null")
                    .build();
        }
        return  attendanceRepository.save(attendance);
    }

    @Override
    public Attendance findByDeviceIdAndSessionId(String deviceId,Long sessionId) throws ServiceException {
        Optional<Attendance> attendanceOptional = attendanceRepository
                .findOneByDeletedAtIsNullAndSessionIdAndDeviceId(sessionId, deviceId);
        return attendanceOptional.orElseThrow(() -> ServiceException.builder()
                .errorCode(ErrorCode.RESOURCE_NOT_FOUND)
                .message("attendance not found")
                .build());
    }

    @Override
    public Attendance save(Attendance attendance) throws ServiceException {
        if(attendance.getId() != null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .message("attendance already exists")
                    .build();
        }
        return  attendanceRepository.save(attendance);
    }

    @Override
    public void delete(Attendance attendance) throws ServiceException {
        if(attendance.getId() == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("attendance is null")
                    .build();
        }
        attendance = findById(attendance.getId());
        attendance.setDeletedAt(new Date());
        attendanceRepository.save(attendance);
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        if(id == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        Attendance attendance = findById(id);
        attendance.setDeletedAt(new Date());
        attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> findAllByDeletedAtIsNullAndSessionId(Long id) {
        return attendanceRepository.findAllByDeletedAtIsNullAndSessionId(id);
    }
}
