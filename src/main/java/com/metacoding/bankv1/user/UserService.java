package com.metacoding.bankv1.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        // 1. 동일 유저네임 있는지 검사
        User user = userRepository.findByUsername(joinDTO.getUsername());

        // 2. 있으면 ,exception 터뜨리기
        if (user != null) throw new RuntimeException("이미 있는 아이디입니다.");

        // 3. 없으면 회원가입
        userRepository.save(joinDTO.getUsername(), joinDTO.getPassword(), joinDTO.getFullname());
    }
}
