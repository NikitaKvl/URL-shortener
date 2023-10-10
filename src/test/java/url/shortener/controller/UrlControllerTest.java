package url.shortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import url.shortener.dto.RequestUrlDto;
import url.shortener.service.UrlService;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UrlController.class})
@ExtendWith(SpringExtension.class)
class UrlControllerTest {
    @Autowired
    private UrlController urlController;

    @MockBean
    private UrlService urlService;

    @Test
    void testGetOriginalUrlWithValidData_isOk() throws Exception {
        when(urlService.getOriginalUrl(any())).thenReturn("https://userway.org/");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(new URI("http://localhost:8080/1abc"));
        MockMvcBuilders.standaloneSetup(urlController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("https://userway.org/"));
    }

    @Test
    void testShortenUrl() throws Exception {
        when(urlService.shortenUrl(any())).thenReturn("http://localhost:8080/1abc");
        RequestUrlDto requestUrlDto = new RequestUrlDto();
        requestUrlDto.setUrl("https://userway.org/");
        String content = (new ObjectMapper()).writeValueAsString(requestUrlDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(urlController)
                .build()
                .perform(requestBuilder);
        String expectedContent = "{\"shortenUrl\":\"http://localhost:8080/1abc\"}";
        actualPerformResult
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }
}

