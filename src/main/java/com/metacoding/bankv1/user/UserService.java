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

    public User 로그인(UserRequest.LoginDTO loginDTO) {
        // 1. 유저네임으로 조회
        User user = userRepository.findByUsername(loginDTO.getUsername());

        // 2. 유저네임 필터링(유저네임 null이면 터뜨림)
        if (user == null) throw new RuntimeException("해당 username이 없음");

        // 3. 패스워드가 맞나?(패스워드가 다르면 터뜨림)
        if (!(user.getPassword().equals(loginDTO.getPassword()))) throw new RuntimeException("password 틀림");

        // 4. 인증
        return user;
    }
}
