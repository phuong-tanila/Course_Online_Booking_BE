package fa.training.backend.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginResponseModel {
    private String accessToken;
    private String refreshToken;

}
