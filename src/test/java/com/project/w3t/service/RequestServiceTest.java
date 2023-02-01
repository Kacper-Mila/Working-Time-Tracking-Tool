//package com.project.w3t.service;
//
//import com.project.w3t.repository.RequestRepository;
//import com.project.w3t.repository.UserRepository;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.test.context.NestedTestConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
//public class RequestServiceTest {
//    @TestConfiguration
//    static class RequestServiceImplTestContextConfiguration{
//        @Autowired
//        private RequestRepository requestRepository;
//        @Autowired
//        private UserRepository userRepository;
//
//        @Bean
//        public RequestService requestService(){
//            return new RequestService(requestRepository, userRepository);
//        }
//    }
//
//    private RequestService requestService;
//    @MockBean
//    private RequestRepository requestRepository;
//}