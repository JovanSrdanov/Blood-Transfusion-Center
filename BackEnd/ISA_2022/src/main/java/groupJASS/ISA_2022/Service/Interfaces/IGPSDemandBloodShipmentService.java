package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Model.GPSDemandBloodShipment;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IGPSDemandBloodShipmentService extends ICrudService<GPSDemandBloodShipment> {
    Page<GPSDemandBloodShipment> getAllPendingShipments(UUID bloodCenterId, int page, int pageSize);
}
