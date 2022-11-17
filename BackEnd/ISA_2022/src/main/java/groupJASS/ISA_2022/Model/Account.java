package groupJASS.ISA_2022.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9]{8,}", message = "Length of the password must be 8 characters and it can only contain upppercase letters, lowercase letters and digits (0-9)")
    private String password;

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private UUID personId;

    @Column(nullable = false)
    private boolean isActivated;

}
