package HelicopterGPS.HelicopterGPS;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PathForHelicopterRepository extends JpaRepository<PathForHelicopter, UUID> {

    @Query("Select pfh from PathForHelicopter pfh " +
            "where pfh.isDelivered=false")
    PathForHelicopter findUndelivered();
}
