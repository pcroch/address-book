package api.addressbook.controller;

import api.addressbook.entity.PersonEntity;
import api.addressbook.entity.QRCodeEntity;
import api.addressbook.repository.AddressRepository;
import api.addressbook.repository.QRCodeRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.commons.io.IOUtils;
import org.hibernate.annotations.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Optional;

import static api.addressbook.service.QRCodeGeneratorService.generateQRCodeImage;

@Controller
@RequestMapping("/qr-code")
public class QRCodeController {

    public static final Logger logger = LoggerFactory.getLogger(AddressRepository.class);

    @Autowired
    private QRCodeRepository qrcodeRepository;

    @RequestMapping("/ping")
    @GetMapping(value = "/url", produces = "application/json")
    public ResponseEntity<String> getPingPersonAddress() {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>("You have reached on QRCode Endpoints", headers, HttpStatus.ACCEPTED);
    }

    /**
     * It will get a  QR code based on qr_code id
     */
    @RequestMapping("/")
    @GetMapping(value = "/url", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQRCodeById() throws IOException {
        File f = new File("src/main/resources/images/code.png");
        logger.info("here is get: {}", f.getAbsolutePath());
        FileInputStream fl = new FileInputStream(f);
        var byteArray = fl.readAllBytes();
        logger.info("byteArray {}",byteArray );
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE)).
                body(byteArray);
    }

    @RequestMapping("/create")
    @GetMapping(value = "/url", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> createQRCode() throws IOException, WriterException {
        String url1 = "https://www.google.com/maps/place/Av.+de+Mersch+103,+6700+Arlon/@49.6872803,5.8277855,17z/data=!3m1!4b1!4m5!3m4!1s0x47eab048197e1e49:0x51a4775d53f430f8!8m2!3d49.6872803!4d5.8299742";
        String url = "https://www.google.com/maps/place/Rue+des+D%C3%A9port%C3%A9s+78,+6700+Arlon/@49.6807691,5.8195074,20z/data=!4m5!3m4!1s0x47eab0439f7d694d:0xcf5fa2ab8ea056cd!8m2!3d49.6807691!4d5.8197009";
 var tmp = generateQRCodeImage(url1, 125,125);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE)).
                body(tmp);
    }
}


// next steps:
// part 1
// creating a qr-code on the tmp cache system and print it postman
    // creating a qr code with the url of the address: https://medium.com/nerd-for-tech/how-to-generate-qr-code-in-java-spring-boot-134adb81f10d
// save  the qr code on the db
//export the qr code when consuming the api

// part 2
// gnerate a nice concat of the addres to writte it by hand

// part 3
// improving the readme to explain a bit more the api
// see for versionning othe the api like v1 , v2 etc...