package kz.kasya.cheena.AttendMe.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kz.kasya.cheena.AttendMe.controllers.BaseController;
import kz.kasya.cheena.AttendMe.exceptions.ServiceException;
import kz.kasya.cheena.AttendMe.models.dtos.AttendanceDto;
import kz.kasya.cheena.AttendMe.models.entities.Attendance;
import kz.kasya.cheena.AttendMe.models.entities.User;
import kz.kasya.cheena.AttendMe.models.mappers.AttendanceMapper;
import kz.kasya.cheena.AttendMe.models.mappers.UserMapper;
import kz.kasya.cheena.AttendMe.services.AttendanceService;
import kz.kasya.cheena.AttendMe.services.UserService;
import kz.kasya.cheena.AttendMe.shared.utils.responses.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/attendances")
@Api(description = "Точка входа для посещения")
@CrossOrigin("*")
public class AttendanceController extends BaseController {

     private AttendanceService attendanceService;
     private AttendanceMapper attendanceMapper;
     private UserService userService;
     private UserMapper userMapper;

     @Autowired
     public AttendanceController(AttendanceService attendanceService,
                                 AttendanceMapper attendanceMapper,
                                 UserService userService,
                                 UserMapper userMapper) {
          this.attendanceService = attendanceService;
          this.attendanceMapper = attendanceMapper;
          this.userService = userService;
          this.userMapper = userMapper;
     }

     @GetMapping
     @ApiOperation("Получить все")
     public ResponseEntity<?> getAll() {
          return buildResponse(attendanceMapper.toDtoList(attendanceService.findAll()), HttpStatus.OK);
     }

     @GetMapping("session/{id}")
     @ApiOperation("Получить все")
     public ResponseEntity<?> getAllBySessionId(@PathVariable Long id) {
          return buildResponse(attendanceMapper.toDtoList(attendanceService.findAllByDeletedAtIsNullAndSessionId(id)), HttpStatus.OK);
     }

     @GetMapping("{id}")
     @ApiOperation("Получить по индентификатору")
     public ResponseEntity<?> getOne(@ApiParam("Идентификатор")  @PathVariable Long id) throws ServiceException {
          return buildResponse(attendanceMapper.toDto(attendanceService.findById(id)), HttpStatus.OK);
     }


     @PostMapping
     @ApiOperation("Добавить")
     public ResponseEntity<?> add(@ApiParam("Модель посещения") @RequestBody AttendanceDto attendanceDto, Authentication authentication) throws ServiceException {
          String login  = authentication.getName();
          User user = userService.findByLogin(login);
          attendanceDto.setUser(userMapper.toDto(user));

          List<Attendance> attendanceList = attendanceService.findAllByDeletedAtIsNullAndSessionId(attendanceDto.getSession().getId());
          for(Attendance attendance : attendanceList){
              if(attendance.getUser().getId().equals(attendanceDto.getUser().getId()) || attendance.getDeviceId().equals(attendanceDto.getDeviceId())){
                  return buildResponse( HttpStatus.BAD_REQUEST);
              }
          }
          return buildResponse(attendanceMapper.toDto(attendanceService.save(attendanceMapper.toEntity(attendanceDto))), HttpStatus.OK);
     }


     @DeleteMapping("{id}")
     @ApiOperation("Удалить")
     public ResponseEntity<?> delete(@ApiParam("Идентификатор")   @PathVariable Long id) throws ServiceException {
          attendanceService.deleteById(id);
          return buildResponse(SuccessResponse.builder().message("deleted").build(), HttpStatus.OK);
     }

     @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT})
     @ApiOperation("Обновить")
     public ResponseEntity<?> update(@ApiParam("Модель") @RequestBody AttendanceDto attendanceDto) throws ServiceException {
          Attendance attendance = attendanceService.update(attendanceMapper.toEntity(attendanceDto));
          return buildResponse(SuccessResponse.builder()
                  .message("updated")
                  .data(attendanceMapper.toDto(attendance))
                  .build(), HttpStatus.OK);
     }

}
