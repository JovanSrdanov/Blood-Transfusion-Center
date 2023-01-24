package groupJASS.ISA_2022.Controller;

import groupJASS.ISA_2022.DTO.PageEntityDto;
import groupJASS.ISA_2022.DTO.PendingShipmentsDTO;
import groupJASS.ISA_2022.Model.BloodCenter;
import groupJASS.ISA_2022.Model.GPSDemandBloodShipment;
import groupJASS.ISA_2022.Service.Interfaces.IAccountService;
import groupJASS.ISA_2022.Service.Interfaces.IGPSDemandBloodShipmentService;
import groupJASS.ISA_2022.Service.Interfaces.IStaffService;
import groupJASS.ISA_2022.Utilities.ObjectMapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("gps-demand-blood-shipment")
public class GPSDemandBloodShipmentController {

    private final IAccountService _accountService;
    private final IStaffService _staffService;

    private final IGPSDemandBloodShipmentService _gpsDemandBloodShipmentService;

    public GPSDemandBloodShipmentController(IAccountService accountService, IStaffService staffService, IGPSDemandBloodShipmentService gpsDemandBloodShipmentService) {
        _accountService = accountService;
        _staffService = staffService;
        _gpsDemandBloodShipmentService = gpsDemandBloodShipmentService;
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @GetMapping("get-all-pending-shipments")
    public ResponseEntity<?> getAllPendingShipments(Principal account,
                                                    @RequestParam(name = "page") int page,
                                                    @RequestParam(name = "pageSize") int pageSize) {

        try {
            BloodCenter bloodCenter = _staffService.findById(_accountService.findAccountByEmail(account.getName()).getPersonId()).getBloodCenter();
            if (bloodCenter == null) {
                throw new Exception("This staff member is not a member of any bloodcenter");
            }
            Page<GPSDemandBloodShipment> entities = _gpsDemandBloodShipmentService.getAllPendingShipments(bloodCenter.getId(), page, pageSize);
            List<PendingShipmentsDTO> content = ObjectMapperUtils.mapAll(entities.getContent(), PendingShipmentsDTO.class);
            PageEntityDto<List<PendingShipmentsDTO>> pageDto = new PageEntityDto<>(content, (int) entities.getTotalElements());
            return new ResponseEntity<>(pageDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @GetMapping("send-shipment/{shipmentId}/{seconds}")
    public ResponseEntity<?> sendShipment(Principal account, @PathVariable("shipmentId") UUID shipmentId, @PathVariable("seconds") int seconds) {

        try {
            _gpsDemandBloodShipmentService.handleShipment(shipmentId, seconds);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @GetMapping("can-view-delivery")
    public ResponseEntity<?> canViewDelivery(Principal account) {

        try {
            BloodCenter bloodCenter = _staffService.findById(_accountService.findAccountByEmail(account.getName()).getPersonId()).getBloodCenter();
            if (bloodCenter == null) {
                throw new Exception("This staff member is not a member of any bloodcenter");
            }
            if (!bloodCenter.isDeliveryInProgres()) {
                throw new Exception("No delivery is in progress");
            }

            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
