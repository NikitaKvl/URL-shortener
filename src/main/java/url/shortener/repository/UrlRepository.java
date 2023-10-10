package url.shortener.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import url.shortener.entity.Url;

@Repository
public interface UrlRepository extends MongoRepository<Url, String> {
    Optional<Url> findById(String url);
}
