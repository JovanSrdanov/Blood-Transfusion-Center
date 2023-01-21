package groupJASS.ISA_2022.Service.Interfaces;

import groupJASS.ISA_2022.Exceptions.QrCodeReadingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public interface IQrCodeService {
    String readQRCode(File qrCodeFile);
    UUID readAppointmentCode(MultipartFile qrCode) throws QrCodeReadingException;
}
