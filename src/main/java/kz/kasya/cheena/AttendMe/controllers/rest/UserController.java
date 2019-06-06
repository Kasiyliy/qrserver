package kz.kasya.cheena.AttendMe.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kz.kasya.cheena.AttendMe.controllers.BaseController;
import kz.kasya.cheena.AttendMe.exceptions.ServiceException;
import kz.kasya.cheena.AttendMe.models.dtos.UserDto;
import kz.kasya.cheena.AttendMe.models.entities.Role;
import kz.kasya.cheena.AttendMe.models.entities.User;
import kz.kasya.cheena.AttendMe.models.mappers.UserMapper;
import kz.kasya.cheena.AttendMe.services.UserService;
import kz.kasya.cheena.AttendMe.shared.utils.codes.ErrorCode;
import kz.kasya.cheena.AttendMe.shared.utils.responses.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Api(description = "Точка входа для пользователя")
@CrossOrigin("*")
public class UserController extends BaseController {

    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    @ApiOperation("Получить все")
    public ResponseEntity<?> getAll() {
        return buildResponse(userMapper.toDtoList(userService.findAll()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ApiOperation("Получить по индентификатору")
    public ResponseEntity<?> getOne(@ApiParam("идентификатор") @PathVariable Long id) throws ServiceException {
        return buildResponse(userMapper.toDto(userService.findById(id)), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Добавить")
    public ResponseEntity<?> add(@ApiParam("модель") @RequestBody UserDto userDto) throws ServiceException {
        User user = userMapper.toEntity(userDto);
        Role role = new Role();
        role.setId(Role.ROLE_STUDENT_ID);
        user.setRole(role);
        user = userService.save(user);
        return buildResponse(userMapper.toDto(user), HttpStatus.OK);
    }

    @PostMapping("/validate")
    @ApiOperation("Валидировать токен")
    public ResponseEntity<?> validate(@ApiParam("логин") @RequestParam String login) throws ServiceException {
        User user = userService.findByLogin(login);
        if (user != null) {
            throw ServiceException.builder().message("Login exists").errorCode(ErrorCode.ALREADY_EXISTS).build();
        } else {
            return buildResponse(SuccessResponse.builder().message("OK").build(), HttpStatus.OK);
        }

    }


    @DeleteMapping("{id}")
    @ApiOperation("Удалить")
    public ResponseEntity<?> delete(@ApiParam("идентификатор") @PathVariable Long id) throws ServiceException {
        userService.deleteById(id);
        return buildResponse(SuccessResponse.builder().message("deleted").build(), HttpStatus.OK);
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT})
    @ApiOperation("Обновить")
    public ResponseEntity<?> update(@ApiParam("модель") @RequestBody UserDto userDto) throws ServiceException {
        User user = userService.update(userMapper.toEntity(userDto));
        return buildResponse(SuccessResponse.builder()
                .message("updated")
                .data(userMapper.toDto(user))
                .build(), HttpStatus.OK);
    }

    @PostMapping("/current")
    @ApiOperation("Получить модель текущего человека")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) throws ServiceException {
        String login = authentication.getName();
        User user = userService.findByLogin(login);
        return buildResponse(SuccessResponse.builder()
                .message("found")
                .data(userMapper.toDto(user))
                .build(), HttpStatus.OK);
    }

}
