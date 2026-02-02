package org.liu.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.liu.api.dto.UserDTO;
import org.liu.api.vo.UserVO;
import org.liu.common.result.Result;
import org.liu.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器（Spring Boot3适配：jakarta.servlet）
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户新增、查询、修改、删除接口")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 根据ID查询用户
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询用户", description = "根据用户ID查询用户详情（支持Redis缓存获取用户）")
    public Result<UserVO> getById(@PathVariable Long id) {
        UserVO userVO = userService.getById(id);
        return Result.success(userVO);
    }

    /**
     * 新增用户
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody UserDTO userDTO) {
        boolean result = userService.save(userDTO);
        return Result.success(result);
    }

    /**
     * 根据手机号查询用户
     */
    @GetMapping("/phone/{phone}")
    public Result<UserVO> getByPhone(@PathVariable String phone) {
        UserVO userVO = userService.getByPhone(phone);
        return Result.success(userVO);
    }
}