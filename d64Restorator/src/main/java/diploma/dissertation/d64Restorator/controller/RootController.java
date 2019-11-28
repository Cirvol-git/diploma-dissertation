package diploma.dissertation.d64Restorator.controller;

import diploma.dissertation.d64Restorator.model.FileModel;
import diploma.dissertation.d64Restorator.service.RootService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "api")
@CrossOrigin(origins = "*")
public class RootController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RootController.class);

    private RootService service;

    public RootController(RootService service) {
        this.service = service;
    }

    @GetMapping(value = "",
            produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<FileModel>> getAll() {
        LOGGER.info("Get all files");
        return new ResponseEntity<List<FileModel>>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/fix",
            produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<FileModel> fix() throws IOException{
        LOGGER.info("Fixing uploaded files");
        return new ResponseEntity<FileModel>(service.fix(), HttpStatus.OK);
    }

    @GetMapping(value = "/download",
            produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<byte[]> download() {
        LOGGER.info("Download fixed file");
        return new ResponseEntity<byte[]>(service.download(), HttpStatus.OK);
    }

    @PostMapping(value = "",
            consumes = MediaType.MULTIPART_FORM_DATA,
            produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<FileModel> upload(@RequestParam MultipartFile file) throws IOException {
        LOGGER.info("File " + file.getOriginalFilename() + " uploaded");
        LOGGER.info("length: " + file.getBytes().length);
        return new ResponseEntity<FileModel>(service.addOne(file), HttpStatus.OK);
    }

    @DeleteMapping(value = "/fix")
    public ResponseEntity<Void> removeFix() {
        LOGGER.info("Deleted fixed file");
        service.removeFixed();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{index}")
    public ResponseEntity<Void> removeOne(@PathVariable int index) {
        LOGGER.info("Delete file with index of: " + index);
        service.removeOne(index);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "")
    public ResponseEntity<Void> removeAll() {
        LOGGER.info("Deleted all files");
        service.removeAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
