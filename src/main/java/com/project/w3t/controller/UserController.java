//package com.project.w3t.controller;
//
//import com.project.w3t.model.Request;
//import com.project.w3t.model.RequestDto;
//import com.project.w3t.repository.UserStorage;
//import com.project.w3t.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/users")
//public class UserController {
//
//    private final UserService userService;
//    @Autowired
//    public UserController(UserService userService, UserStorage userStorage) {
//        this.userService = userService;
//    }
//
//    @GetMapping
//    public List<Request> getAll() {
//        return userService.getUserRequestList();
//    }
//
//    @PostMapping
//    public void addRequest(@RequestBody Request request) {
//        requestService.addRequest(request);
//    }
//
//    @PatchMapping("{requestId}")
//    public void update(@PathVariable Long requestId, @RequestBody RequestDto requestDto) {
//        requestService.updateRequest(requestId, requestDto);
//    }
//
//    @DeleteMapping("{requestId}")
//    public void delete(@PathVariable Long requestId) {
//        requestService.deleteRequest(requestId);
//    }
//}
