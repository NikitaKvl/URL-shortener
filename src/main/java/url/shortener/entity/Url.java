package url.shortener.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "url")
@AllArgsConstructor
public class Url {
    @Id
    private String id;
    private String originalUrl;
}
