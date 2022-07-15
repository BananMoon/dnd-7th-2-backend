package com.dnd.niceteam.security;

import com.dnd.niceteam.member.domain.Member;
import com.dnd.niceteam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class       : UserDetailsImpl
 * Author      : 문 윤지
 * Description : UserDetailsService 구현체
 * History     : [2022-07-15] 문윤지 - Class Create
 */

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsImpl user = findSecurityUserByUsername(username);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    // security user 조회
    public UserDetailsImpl findSecurityUserByUsername(String username) {
        // TODO: 2022-07-15 error throw
        Member member = memberRepository.findOneByUsername(username)
                .orElseThrow();
        return convertToUserDetails(member);
    }

    private UserDetailsImpl convertToUserDetails(Member member) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(member.getRole().getFullName()));

        return new UserDetailsImpl(
                member.getUsername(),
                member.getPassword(),
                grantedAuthorities
        );
    }
}
