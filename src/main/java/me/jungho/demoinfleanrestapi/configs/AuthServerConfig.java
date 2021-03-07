package me.jungho.demoinfleanrestapi.configs;

import lombok.RequiredArgsConstructor;
import me.jungho.demoinfleanrestapi.accounts.AccountService;
import me.jungho.demoinfleanrestapi.common.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;
    private final TokenStore tokenStore;
    private final DataSource dataSource;
    private final AppProperties appProperties;

    // 클라이언트 시크릿을 확인할 때 passwordEncoder 를 사용한다.
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {


        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);

        if(!jdbcClientDetailsService.listClientDetails().isEmpty() ) {
            jdbcClientDetailsService.removeClientDetails("myApp");
        }

        if(jdbcClientDetailsService.listClientDetails().isEmpty() ) {
            clients.jdbc(dataSource).withClient("myApp").secret(passwordEncoder.encode("pass"))
                    .authorizedGrantTypes("password", "refresh_token")
                    .scopes("read", "write").accessTokenValiditySeconds(10 * 60)
                    .refreshTokenValiditySeconds(6 * 10 * 60).and().build();
        }
        clients.jdbc(dataSource).build().loadClientByClientId("myApp");
    }



//        clients.jdbc(dataSource)
//                .withClient("myApp")
//                .authorizedGrantTypes("password", "refresh_token")
//                .scopes("read","write")
//                .secret(passwordEncoder.encode("pass"))
//                .accessTokenValiditySeconds(10 * 60) // 10분
//                .refreshTokenValiditySeconds(6 * 10 * 60); // 리프레쉬 토큰 1시간

//        clients.inMemory()
//                .withClient(appProperties.getClientId())
//                .authorizedGrantTypes("password", "refresh_token")
//                .scopes("read","write")
//                .secret(passwordEncoder.encode(appProperties.getClientSecret()))
//                .accessTokenValiditySeconds(10 * 60)
//                .refreshTokenValiditySeconds(6 * 10 * 60);
//    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(accountService)
                .tokenStore(tokenStore)

                ;
    }
}
