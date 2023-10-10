package url.shortener.service.impl;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import url.shortener.dto.RequestUrlDto;
import url.shortener.entity.Url;
import url.shortener.exception.InvalidRequestUrlException;
import url.shortener.exception.UrlNotFoundException;
import url.shortener.repository.UrlRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UrlServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UrlServiceImplTest {
    @MockBean
    private UrlRepository urlRepository;

    @Autowired
    private UrlServiceImpl urlServiceImpl;

    @Test
    void testShortenUrlWithValidData_isOk() {
        when(urlRepository.save(any())).thenReturn(new Url("1abc", "https://userway.org/"));
        RequestUrlDto requestUrlDto = new RequestUrlDto();
        requestUrlDto.setUrl("https://userway.org/");
        assertEquals("http://localhost:8080/1abc", urlServiceImpl.shortenUrl(requestUrlDto));
        verify(urlRepository).save(any());
    }

    @Test
    void testShortenUrlWithEmptyUrlInRequest_isNotOk() {
        RequestUrlDto requestUrlDto = mock(RequestUrlDto.class);
        when(requestUrlDto.getUrl()).thenReturn("");
        assertThrows(InvalidRequestUrlException.class, () -> urlServiceImpl.shortenUrl(requestUrlDto));
        verify(requestUrlDto).getUrl();
    }

    @Test
    void testShortenUrlWithNullRequest_isNotOk() {
        assertThrows(InvalidRequestUrlException.class, () -> urlServiceImpl.shortenUrl(null));
    }

    @Test
    void testGetOriginalUrl_isOk() {
        when(urlRepository.findById(any())).thenReturn(Optional.of(new Url("1abc", "https://userway.org/")));
        assertEquals("https://userway.org/", urlServiceImpl.getOriginalUrl("1abc"));
        verify(urlRepository).findById(any());
    }

    @Test
    void testGetOriginalUrlWhenUrlNotFound_isNotOk() {
        when(urlRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(UrlNotFoundException.class, () -> urlServiceImpl.getOriginalUrl("1abc"));
        verify(urlRepository).findById(any());
    }
}

