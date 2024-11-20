package com.example.demo.model.service;

import org.springframework.stereotype.Service;
import com.example.demo.model.domain.TestDB;
import com.example.demo.model.repository.TestRepository;
import lombok.RequiredArgsConstructor;

@Service // 서비스 계층 명시, 스프링 내부에서 자동 등록됨
@RequiredArgsConstructor // final 필드를 가진 생성자를 자동으로 생성
public class TestService {

    private final TestRepository testRepository; // final로 선언하여 생성자 주입

    public TestDB findByName(String name) {
        return testRepository.findByName(name); // 형변환 제거, 리턴값이 TestDB라고 가정
    }
}
