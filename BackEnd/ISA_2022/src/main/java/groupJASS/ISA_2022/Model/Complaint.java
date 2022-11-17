package groupJASS.ISA_2022.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Complaint {

   @Id
   private UUID id;

   @Column(nullable = false)
   private String text;

   @ManyToOne
   @JoinColumn(name = "blood_center_id")
   private BloodCenter bloodCenter;

   @Column(nullable = false)
   private ComplaintTarget complaintTarget;
}
