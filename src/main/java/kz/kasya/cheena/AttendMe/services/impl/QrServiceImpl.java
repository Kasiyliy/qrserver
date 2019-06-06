package kz.kasya.cheena.AttendMe.services.impl;


import kz.kasya.cheena.AttendMe.exceptions.ServiceException;
import kz.kasya.cheena.AttendMe.models.entities.Qr;
import kz.kasya.cheena.AttendMe.repositories.QrRepository;
import kz.kasya.cheena.AttendMe.services.QrService;
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
public class QrServiceImpl implements QrService {

    private QrRepository qrRepository;

    @Autowired
    public QrServiceImpl(QrRepository qrRepository) {
        this.qrRepository = qrRepository;
    }

    @Override
    public Qr findById(Long id) throws ServiceException {
        Optional<Qr> qrOptional = qrRepository.findById(id);
        return qrOptional.orElseThrow(() -> ServiceException.builder()
                .errorCode(ErrorCode.RESOURCE_NOT_FOUND)
                .message("qr not found")
                .build());
    }

    @Override
    public List<Qr> findAll() {
        return qrRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public List<Qr> findAllWithDeleted() {
        return qrRepository.findAll();
    }

    @Override
    public Qr update(Qr qr) throws ServiceException {
        if(qr.getId() == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("qr is null")
                    .build();
        }
        return  qrRepository.save(qr);
    }

    @Override
    public Qr save(Qr qr) throws ServiceException {
        if(qr.getId() != null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .message("qr already exists")
                    .build();
        }
        return  qrRepository.save(qr);
    }

    @Override
    public void delete(Qr qr) throws ServiceException {
        if(qr.getId() == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("qr is null")
                    .build();
        }
        qr = findById(qr.getId());
        qr.setDeletedAt(new Date());
        qrRepository.save(qr);
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        if(id == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        Qr qr = findById(id);
        qr.setDeletedAt(new Date());
        qrRepository.save(qr);
    }
}
