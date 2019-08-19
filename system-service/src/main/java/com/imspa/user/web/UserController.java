package com.imspa.user.web;

import com.imspa.api.order.OrderDTO;
import com.imspa.api.user.SearchUserRequest;
import com.imspa.api.user.SearchUserResponse;
import com.imspa.api.user.UserVO;
import com.imspa.user.service.UserService;
import com.imspa.util.Publisher;
import com.imspa.web.auth.TokenAuthenticationService;
import com.imspa.web.pojo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-12 17:47
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private Publisher userMessagePublisher;
    @Autowired
    private Publisher orderMessagePublisher;

    @PostMapping("/login")
    public ResponseEntity login(String username,String password) {
        User user;
        try {

            user = userService.login(username, password);
        } catch (Exception e) {
            logger.debug("login error,user:{},message:{}",username,e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("user", user.getName());
        claimsMap.put("role", user.getRoleId());
        return ResponseEntity.ok().header("Authorization", TokenAuthenticationService.getAuthenticationToken(claimsMap)).body("");
    }

    @GetMapping("/get")
    public SearchUserResponse get(@Validated SearchUserRequest request, BindingResult result) {
        if (result.hasErrors()) {
            logger.warn("Binding error:{}",result);

        }
        return userService.search(request);
    }

    @GetMapping("/")
    public SearchUserResponse list(SearchUserRequest request) {
        return userService.search(request);
    }

    @PutMapping("/register/user")
    public UserVO register(UserVO userVO) {
        userMessagePublisher.produceMsg(userVO);
        return userVO;
    }

    @PutMapping("/register/order")
    public OrderDTO register(OrderDTO orderDTO) {
        orderMessagePublisher.produceMsg(orderDTO);
        return orderDTO;
    }

}
