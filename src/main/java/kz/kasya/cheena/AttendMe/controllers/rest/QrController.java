package kz.kasya.cheena.AttendMe.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kz.kasya.cheena.AttendMe.controllers.BaseController;
import kz.kasya.cheena.AttendMe.exceptions.ServiceException;
import kz.kasya.cheena.AttendMe.models.dtos.QrDto;
import kz.kasya.cheena.AttendMe.models.entities.Qr;
import kz.kasya.cheena.AttendMe.models.mappers.QrMapper;
import kz.kasya.cheena.AttendMe.services.QrService;
import kz.kasya.cheena.AttendMe.shared.utils.codes.ErrorCode;
import kz.kasya.cheena.AttendMe.shared.utils.responses.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qrs")
@Api(description = "Точка входа для qr")
@CrossOrigin("*")
public class QrController extends BaseController {

     private QrService qrService;
     private QrMapper qrMapper;

     @Autowired
     public QrController(QrService qrService, QrMapper qrMapper) {
          this.qrService = qrService;
          this.qrMapper = qrMapper;
     }

     @GetMapping
     @ApiOperation("Получить все")
     public ResponseEntity<?> getAll() {
          return buildResponse(qrMapper.toDtoList(qrService.findAll()), HttpStatus.OK);
     }

     @GetMapping("{id}")
     @ApiOperation("Получить по индентификатору")
     public ResponseEntity<?> getOne(@ApiParam("идентификатор") @PathVariable Long id) throws ServiceException {
          return buildResponse(qrMapper.toDto(qrService.findById(id)), HttpStatus.OK);
     }

     @PostMapping
     @ApiOperation("Добавить")
     public ResponseEntity<?> add(@ApiParam("Модель")  @RequestBody QrDto qrDto) throws ServiceException {
          return buildResponse(qrMapper.toDto(qrService.save(qrMapper.toEntity(qrDto))), HttpStatus.OK);
     }


     @DeleteMapping("{id}")
     @ApiOperation("Удалить")
     public ResponseEntity<?> delete(@ApiParam("идентификатор") @PathVariable Long id) throws ServiceException {
          qrService.deleteById(id);
          return buildResponse(SuccessResponse.builder().message("deleted").build(), HttpStatus.OK);
     }

     @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT})
     @ApiOperation("Обновить")
     public ResponseEntity<?> update(@ApiParam("Модель") @RequestBody QrDto qrDto) throws ServiceException {
          Qr qr = qrService.update(qrMapper.toEntity(qrDto));
          return buildResponse(SuccessResponse.builder()
                  .message("updated")
                  .data(qrMapper.toDto(qr))
                  .build(), HttpStatus.OK);
     }

}
