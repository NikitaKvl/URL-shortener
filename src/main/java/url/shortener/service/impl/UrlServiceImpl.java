package url.shortener.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import url.shortener.dto.RequestUrlDto;
import url.shortener.entity.Url;
import url.shortener.exception.InvalidRequestUrlException;
import url.shortener.exception.UrlNotFoundException;
import url.shortener.repository.UrlRepository;
import url.shortener.service.UrlService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlServiceImpl implements UrlService {
    private final static String APP_URL = "http://localhost:8080/";
    private final static int FIRST_INDEX = 0;
    private final static int LAST_INDEX = 8;
    private final UrlRepository urlRepository;

    @Cacheable(value = "longUrl", key = "#requestUrl.url",
            condition = "#requestUrl.url != null", unless = "#result == null")
    @Override
    public String shortenUrl(RequestUrlDto requestUrl) {
        if (null == requestUrl || StringUtils.isBlank(requestUrl.getUrl())) {
            log.error("Invalid requestUrl: {}", requestUrl);
            throw new InvalidRequestUrlException("Invalid request");
        }
        log.info("Start processing to short url for requestUrl: {}", requestUrl.getUrl());
        Url newUrl = new Url(generateId(), requestUrl.getUrl());
        log.info("Saving entity in mongo db: {}", newUrl);
        Url savedUrl = urlRepository.save(newUrl);
        return APP_URL + savedUrl.getId();
    }

    @Cacheable(value = "shortUrl", key = "#urlId", unless = "#result == null")
    @Override
    public String getOriginalUrl(String urlId) {
        if (StringUtils.isBlank(urlId)) {
            log.error("Invalid request urlId: {}", urlId);
            throw new InvalidRequestUrlException("Invalid request url");
        }
        log.info("Start processing to find entity by id: {}", urlId);
        Url url = urlRepository.findById(urlId).orElseThrow(() ->
                new UrlNotFoundException("Can't find url by id: " + urlId));
        log.info("Return original url by urlId: {}", urlId);
        return url.getOriginalUrl();
    }

    private String generateId() {
        return UUID.randomUUID().toString().substring(FIRST_INDEX, LAST_INDEX);
    }
}
