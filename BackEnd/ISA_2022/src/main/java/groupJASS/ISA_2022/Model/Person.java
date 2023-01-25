package groupJASS.ISA_2022.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class Person {

    @Id
    protected UUID id;
    @Column(nullable = false)
    protected String name;
    @Column(nullable = false)
    protected String surname;
    @Column(nullable = false)
    protected String phoneNumber;
}
