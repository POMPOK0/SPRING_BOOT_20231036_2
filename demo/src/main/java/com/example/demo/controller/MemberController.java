package com.example.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.service.AddMemberRequest;
import com.example.demo.model.service.Member_Service;

import jakarta.servlet.http.HttpSession;

import com.example.demo.model.domain.Member;
import org.springframework.ui.Model;


@Controller // 컨트롤러 어노테이션 명시

public class MemberController {

    @Autowired
    Member_Service memberService;
    @GetMapping("/join_new") // 회원 가입 페이지 연결
    public String join_new() {
        return "join_new"; // .HTML 연결
    }

    @PostMapping("/api/members") // 회원 가입 저장
    public String addmembers(@ModelAttribute AddMemberRequest request) {
        memberService.saveMember(request);
        return "join_end"; // .HTML 연결
    }
    @GetMapping("/member_login") // 로그인 페이지 연결
    public String member_login() {
        return "login"; // .HTML 연결
    }
    // @PostMapping("/api/login_check") // 로그인(아이디, 패스워드) 체크
    // public String checkMembers(@ModelAttribute AddMemberRequest request, Model model) {
    //     try {
    //         Member member = memberService.loginCheck(request.getEmail(), request.getPassword()); // 패스워드 반환
    //         model.addAttribute("member", member); // 로그인 성공 시 회원 정보 전달
    //         return "redirect:/board_list"; // 로그인 성공 후 이동할 페이지
    //     } catch (IllegalArgumentException e) {
    //         model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
    //         return "login"; // 로그인 실패 시 로그인 페이지로 리다이렉트
    //     }
    // }

    @PostMapping("/api/login_check") // 아이디, 패스워드 로그인 체크
    public String checkMembers(
        @ModelAttribute AddMemberRequest request,
        Model model,
        HttpSession session) { // 세션 객체 추가
            
        try {
            // 로그인 체크를 통해 멤버 객체 반환
            Member member = memberService.loginCheck(request.getEmail(), request.getPassword());
            
            // 고유 ID를 생성하여 세션에 저장
            String sessionId = UUID.randomUUID().toString(); // 임의의 고유 ID로 세션 생성
            session.setAttribute("userId", sessionId); // 세션에 저장할 키 이름: userId
            session.setAttribute("memberName", member.getName()); // 추가로 회원 이름 저장 가능
        
            System.out.println("로그인 성공 - 세션 생성: " + sessionId); // 디버깅용 출력
        
            // 로그인 성공 시 이동할 페이지
            return "redirect:/board_list"; 
    } catch (IllegalArgumentException e) {
        // 로그인 실패 시 에러 메시지 전달
        model.addAttribute("error", e.getMessage()); 
        return "login"; // 로그인 페이지로 다시 이동
    }
}


}
