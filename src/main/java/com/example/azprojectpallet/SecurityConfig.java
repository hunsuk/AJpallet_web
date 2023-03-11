package com.example.azprojectpallet;

import com.example.azprojectpallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

    private final UserService userService; // 3


    @Override
    public void configure(WebSecurity web) { // 4
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**","/error","/images/**,");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // 5
        http
                .authorizeRequests() // 6
                .antMatchers("/start","/startInit","/login", "/signup", "/user","/error/403","/test1","/test2","/test3","/test4","/test5","/test6","/test7","/getIndexPallet").permitAll() // 누구나 접근 허용
                .antMatchers("/","/publish_req","/setBuket","/myPage","/check_my_request","/request_del","/main","/prediction_user","/requestDetail","/prdicRequest","/prdicRequest_send","/request_popup","/request_send","/getSendingPallet","/request_check_popup","/requestReservationSend").hasRole("USER") // USER, ADMIN만 접근 가능
                .antMatchers("/admin","/publish_resp","prediction_admin","/request_process","/control_request","/dashboard_admin_atration","/orderDetail","/deliverStatus","/returnList","/orderStatus","/getPalletInfo","/getPalletAdd").hasRole("ADMIN") // ADMIN만 접근 가능
                .anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
                .and()
                .formLogin() // 7
                .loginPage("/login") // 로그인 페이지 링크
                .defaultSuccessUrl("/")// 로그인 성공 후 리다이렉트 주소
                .and()
                .logout() // 8
                .logoutSuccessUrl("/start") // 로그아웃 성공시 리다이렉트 주소
                .invalidateHttpSession(true) // 세션 날리기
                .and()
                .exceptionHandling().accessDeniedPage("/error/403");

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception { // 9
        auth.userDetailsService(userService)
                // 해당 서비스(userService)에서는 UserDetailsService를 implements해서
                // loadUserByUsername() 구현해야함 (서비스 참고)
                .passwordEncoder(new BCryptPasswordEncoder());
    }



}

