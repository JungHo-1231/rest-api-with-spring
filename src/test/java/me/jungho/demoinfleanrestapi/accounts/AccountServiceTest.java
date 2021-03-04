package me.jungho.demoinfleanrestapi.accounts;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    void findByUsername(){
        //given
        String username = "jung@naver.com";
        String password = "test";

        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        accountRepository.save(account);

        UserDetailsService userDetailsService = (UserDetailsService)accountService;

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Assertions.assertThat(userDetails.getPassword()).isEqualTo(password);
    }

    @Test()
    void findByUsernameFail(){
        assertThrows(UsernameNotFoundException.class, () -> accountService.loadUserByUsername("ramdom@gmail.com"));
    }




}