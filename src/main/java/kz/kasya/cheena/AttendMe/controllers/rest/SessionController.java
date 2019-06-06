package kz.kasya.cheena.AttendMe.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kz.kasya.cheena.AttendMe.controllers.BaseController;
import kz.kasya.cheena.AttendMe.exceptions.ServiceException;
import kz.kasya.cheena.AttendMe.models.dtos.SessionDto;
import kz.kasya.cheena.AttendMe.models.entities.Session;
import kz.kasya.cheena.AttendMe.models.entities.User;
import kz.kasya.cheena.AttendMe.models.mappers.SessionMapper;
import kz.kasya.cheena.AttendMe.models.mappers.UserMapper;
import kz.kasya.cheena.AttendMe.services.SessionService;
import kz.kasya.cheena.AttendMe.services.UserService;
import kz.kasya.cheena.AttendMe.shared.utils.responses.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
@Api(description = "Точка входа для сессий")
@CrossOrigin("*")
public class SessionController extends BaseController {

     private SessionService sessionService;
     private SessionMapper sessionMapper;
     private UserService userService;
     private UserMapper userMapper;

     @Autowired
     public SessionController(SessionService sessionService,
                              SessionMapper sessionMapper,
                              UserService userService,
                              UserMapper userMapper) {
          this.sessionService = sessionService;
          this.sessionMapper = sessionMapper;
          this.userService = userService;
          this.userMapper = userMapper;
     }

     @GetMapping
     @ApiOperation("Получить все")
     public ResponseEntity<?> getAll() {
          return buildResponse(sessionMapper.toDtoList(sessionService.findAll()), HttpStatus.OK);
     }

     @GetMapping("{id}")
     @ApiOperation("Получить по индентификатору")
     public ResponseEntity<?> getOne(@ApiParam("идентификатор") @PathVariable Long id) throws ServiceException {
          return buildResponse(sessionMapper.toDto(sessionService.findById(id)), HttpStatus.OK);
     }

     @PostMapping
     @ApiOperation("Добавить")
     public ResponseEntity<?> add(@ApiParam("Модель") @RequestBody SessionDto sessionDto, Authentication authentication) throws ServiceException {
          String login  = authentication.getName();
          User user = userService.findByLogin(login);
          sessionDto.setUser(userMapper.toDto(user));
          return buildResponse(sessionMapper.toDto(sessionService.save(sessionMapper.toEntity(sessionDto))), HttpStatus.OK);
     }


     @DeleteMapping("{id}")
     @ApiOperation("Удалить")
     public ResponseEntity<?> delete(@ApiParam("идентификатор") @PathVariable Long id) throws ServiceException {
          sessionService.deleteById(id);
          return buildResponse(SuccessResponse.builder().message("deleted").build(), HttpStatus.OK);
     }

     @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT})
     @ApiOperation("Обновить")
     public ResponseEntity<?> update(@ApiParam("Модель")  @RequestBody SessionDto sessionDto) throws ServiceException {
          Session session = sessionService.update(sessionMapper.toEntity(sessionDto));
          return buildResponse(SuccessResponse.builder()
                  .message("updated")
                  .data(sessionMapper.toDto(session))
                  .build(), HttpStatus.OK);
     }


     @GetMapping("/qr/{id}")
     @ApiOperation("Получить по индентификатору qr сессию")
     public ResponseEntity<?> getSessionByQr(@ApiParam("идентификатор") @PathVariable Long id) throws ServiceException {
          return buildResponse(sessionMapper.toDto(sessionService.findFirstByQrId(id)), HttpStatus.OK);
     }
}
