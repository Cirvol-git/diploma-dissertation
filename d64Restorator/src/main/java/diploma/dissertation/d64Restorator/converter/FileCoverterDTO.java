package diploma.dissertation.d64Restorator.converter;

import diploma.dissertation.d64Restorator.exception.UnsupportedFileTypeException;
import diploma.dissertation.d64Restorator.model.FileModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class FileCoverterDTO {
    public FileModel toFileDTO(MultipartFile file) throws IOException {
        FileModel ret = new FileModel();
        ArrayList<Boolean> track = null;
        Integer count = 0;
        byte[] bytes = new byte[0];

        ret.setName(file.getOriginalFilename());
        ret.setFile(file);


        bytes = file.getBytes();
        ret.setBytes(bytes);


        if (bytes.length == 174848 || bytes.length == 196608) {
            for (int i = 0; i < 17; i++) {
                track = new ArrayList<>(Collections.nCopies(21, Boolean.TRUE));
                ret.getSectorView().add(track);
            }
            for (int i = 0; i < 7; i++) {
                track = new ArrayList<>(Collections.nCopies(19, Boolean.TRUE));
                ret.getSectorView().add(track);
            }
            for (int i = 0; i < 6; i++) {
                track = new ArrayList<>(Collections.nCopies(18, Boolean.TRUE));
                ret.getSectorView().add(track);
            }
            if (bytes.length == 174848) {
                for (int i = 0; i < 5; i++) {
                    track = new ArrayList<>(Collections.nCopies(17, Boolean.TRUE));
                    ret.getSectorView().add(track);
                }
            } else {
                for (int i = 0; i < 10; i++) {
                    track = new ArrayList<>(Collections.nCopies(17, Boolean.TRUE));
                    ret.getSectorView().add(track);
                }
            }
            ret.setDiskId(new byte[]{bytes[91392 + 162], bytes[91392 + 163]});
            return ret;
        } else if (bytes.length == 175531) {
            for (int i = bytes.length - 683; i < bytes.length; i++) {
                if ((i - 174848) % 21 == 0
                        && i < bytes.length - 683 + 21 * 17
                    || ((i - 174848 - 21 * 17) % 19 == 0
                        && (bytes.length - 683 + 21 * 17) <= i
                        && i < (bytes.length - 683 + 21 * 17 + 19 * 7))
                    || ((i - 174848 - 21 * 17 - 19 * 7) % 18 == 0
                        && (bytes.length - 683 + 21 * 17 + 19 * 7) <= i
                        && i < (bytes.length - 683 + 21 * 17 + 19 * 7 + 18 * 6))
                    || ((i - 174848 - 21 * 17 - 19 * 7 - 18 * 6) % 17 == 0
                        && (bytes.length - 683 + 21 * 17 + 19 * 7 + 18 * 6) <= i)
                ) {
                    if (track != null) {
                        ret.getSectorView().add(track);
                    }
                    track = new ArrayList<>();
                }
                track.add(bytes[i] == 1);
                if (bytes[i] != 1) {
                    count++;
                    ret.getErrorList().add(Arrays.asList(ret.getSectorView().size(), track.size() - 1));
                }
            }
            if (bytes[bytes.length - 683 + 17 * 21] == 1) {
                ret.setDiskId(new byte[]{bytes[91392 + 162], bytes[91392 + 163]});
            }
            ret.getSectorView().add(track);
            return ret;
        } else if (bytes.length == 197376) {
            for (int i = bytes.length - 768; i < bytes.length; i++) {
                if ((i - 196608) % 21 == 0
                        && i < bytes.length - 768 + 21 * 17
                    || ((i - 196608 - 21 * 17) % 19 == 0
                        && (bytes.length - 768 + 21 * 17) <= i
                        && i < (bytes.length - 768 + 21 * 17 + 19 * 7))
                    || ((i - 196608 - 21 * 17 - 19 * 7) % 18 == 0
                        && (bytes.length - 768 + 21 * 17 + 19 * 7) <= i
                        && i < (bytes.length - 768 + 21 * 17 + 19 * 7 + 18 * 6)
                    || ((i - 196608 - 21 * 17 - 19 * 7 - 18 * 6) % 17 == 0)
                        && (bytes.length - 768 + 21 * 17 + 19 * 7 + 18 * 6) <= i)
                ) {
                    if (track != null) {
                        ret.getSectorView().add(track);
                    }
                    track = new ArrayList<>();
                }
                track.add(bytes[i] == 1);
                if (bytes[i] == 0) {
                    count++;
                    ret.getErrorList().add(Arrays.asList(ret.getSectorView().size(), track.size() - 1));
                }
            }
            if (bytes[bytes.length - 768 + 17 * 21] == 1) {
                ret.setDiskId(new byte[]{bytes[91392 + 162], bytes[91392 + 163]});
            }
            ret.getSectorView().add(track);
            return ret;
        } else {
            throw new UnsupportedFileTypeException("Type of uploaded file is unsupported");
        }
    }
}
