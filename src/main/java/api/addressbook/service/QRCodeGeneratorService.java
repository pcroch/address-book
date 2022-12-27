package api.addressbook.service;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Service("QRCodeGeneratorService")
public class QRCodeGeneratorService {

    public static void generateQRCodeImage(String text, int width, int height, String filePath) {
    }


    public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {


        return null;
    }

}