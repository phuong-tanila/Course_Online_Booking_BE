package fa.training.backend.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TokenAuthModel {
    private String accessToken;
    private String refreshToken;

}
