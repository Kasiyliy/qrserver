package kz.kasya.cheena.AttendMe.services;

import kz.kasya.cheena.AttendMe.exceptions.ServiceException;
import kz.kasya.cheena.AttendMe.models.entities.Qr;

import java.util.List;


public interface QrService {

    Qr findById(Long id) throws ServiceException;
    List<Qr> findAll();
    List<Qr> findAllWithDeleted();
    Qr update(Qr qr) throws ServiceException ;
    Qr save(Qr qr) throws ServiceException ;
    void delete(Qr qr) throws ServiceException ;
    void deleteById(Long id) throws ServiceException ;

}
