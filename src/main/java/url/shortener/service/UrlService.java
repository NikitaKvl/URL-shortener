package url.shortener.service;

import url.shortener.dto.RequestUrlDto;

public interface UrlService {
    String shortenUrl(RequestUrlDto requestUrl);

    String getOriginalUrl(String urlId);
}
