package manage.gxuicpc.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserExcelDTO {
    private String username;
    private String account;
    private String email;
    private String role;
}
