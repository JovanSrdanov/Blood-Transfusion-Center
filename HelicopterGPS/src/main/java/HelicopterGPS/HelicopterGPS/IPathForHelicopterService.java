package HelicopterGPS.HelicopterGPS;

import org.springframework.stereotype.Repository;

@Repository
public interface IPathForHelicopterService {

    PathForHelicopter findNotDelivered();

    PathForHelicopter save(PathForHelicopter entity);


}
