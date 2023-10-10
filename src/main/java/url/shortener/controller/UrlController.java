package url.shortener.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import url.shortener.dto.RequestUrlDto;
import url.shortener.dto.ResponseUrlDto;
import url.shortener.service.UrlService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UrlController {
    private final UrlService urlService;

    @PostMapping
    public ResponseUrlDto shortenUrl(@RequestBody RequestUrlDto requestUrl) {
        log.info("Incoming request to get shorten url: {}", requestUrl);
        return new ResponseUrlDto(urlService.shortenUrl(requestUrl));
    }

    @GetMapping("/{urlId}")
    public ResponseEntity<Void> getOriginalUrl(@PathVariable String urlId) {
        log.info("Incoming request to get original url by id: {}", urlId);
        String originalUrl = urlService.getOriginalUrl(urlId);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
    }
}
