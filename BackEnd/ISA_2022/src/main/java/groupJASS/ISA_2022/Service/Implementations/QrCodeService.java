package groupJASS.ISA_2022.Service.Implementations;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import groupJASS.ISA_2022.Exceptions.QrCodeReadingException;
import groupJASS.ISA_2022.Service.Interfaces.IQrCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

@Service
@Primary
public class QrCodeService implements IQrCodeService {

    private Logger logger = LoggerFactory.getLogger(QrCodeService.class);

    @Override
    public String readQRCode(File qrCodeFile) {
        try {
            BufferedImage bufferedImage = ImageIO.read(qrCodeFile);
            BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(bufferedImage);
            HybridBinarizer hybridBinarizer = new HybridBinarizer(bufferedImageLuminanceSource);
            BinaryBitmap binaryBitmap = new BinaryBitmap(hybridBinarizer);
            MultiFormatReader multiFormatReader = new MultiFormatReader();

            Result result = multiFormatReader.decode(binaryBitmap);
            String qrCodeText = result.getText();
            return qrCodeText;
        } catch (IOException | NotFoundException ex) {
            logger.error("Error during reading QR code image", ex);
        }
        return null;
    }

    @Override
    public UUID readAppointmentCode(MultipartFile qrCode) throws QrCodeReadingException {
        //For concurrency reasons
        String randomNumber = Integer.toString((int) Math.round(Math.random()));
        File qrCodeFile = new File("src/main/resources/temp/qrFile" + randomNumber + ".tmp");

        try {
            //Converting multipart file to file
            qrCodeFile.createNewFile();
            OutputStream os = new FileOutputStream(qrCodeFile);
            os.write(qrCode.getBytes());

            //Decoding QR code
            String text = readQRCode(qrCodeFile);

            String delimiter = "=====";
            String appCodeLine = text.split(delimiter)[1];
            String appCode = appCodeLine.split(":")[1].strip();

            return  UUID.fromString(appCode);
        } catch (Exception e) {
            throw new QrCodeReadingException();
        }
    }
}
