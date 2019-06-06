package kz.kasya.cheena.AttendMe.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kz.kasya.cheena.AttendMe.controllers.BaseController;
import kz.kasya.cheena.AttendMe.exceptions.ServiceException;
import kz.kasya.cheena.AttendMe.models.mappers.RoleMapper;
import kz.kasya.cheena.AttendMe.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@Api(description = "Точка входа для роли")
@CrossOrigin("*")
public class RoleController extends BaseController {

     private RoleService roleService;
     private RoleMapper roleMapper;

     @Autowired
     public RoleController(RoleService roleService, RoleMapper roleMapper) {
          this.roleService = roleService;
          this.roleMapper = roleMapper;
     }

     @GetMapping
     @ApiOperation("Получить все")
     public ResponseEntity<?> getAll(){
          return buildResponse(roleMapper.toDtoList(roleService.findAll()), HttpStatus.OK);
     }

     @GetMapping("{id}")
     @ApiOperation("Получить по индентификатору")
     public ResponseEntity<?> getOne(@ApiParam("идентификатор") @PathVariable Long id) throws ServiceException {
          return buildResponse(roleMapper.toDto(roleService.findById(id)), HttpStatus.OK);
     }

}
