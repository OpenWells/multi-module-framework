package org.liu.web.controller.user;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.liu.api.dto.user.UserLoginDTO;
import org.liu.api.vo.user.UserLoginVO;
import org.liu.biz.service.user.SysUserBizService;
import org.liu.common.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口控制器（原有框架类，复用，仅做请求接入）
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private SysUserBizService sysUserBizService;

    /**
     * 用户登录接口
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@Valid @RequestBody UserLoginDTO dto) {
        UserLoginVO vo = sysUserBizService.login(dto);
        return Result.success(vo);
    }

    /**
     * 用户注册接口
     */
    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody UserLoginDTO dto) {
        sysUserBizService.register(dto);
        return Result.success();
    }
}