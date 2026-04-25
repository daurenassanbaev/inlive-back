package pm.inlive;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pm.inlivefilemanager.client.api.FileManagerApi;

@SpringBootTest
class InliveApplicationTests {
    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private FileManagerApi fileManagerApi;

    @Test
    void contextLoads() {
    }

}
