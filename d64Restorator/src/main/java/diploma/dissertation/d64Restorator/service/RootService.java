package diploma.dissertation.d64Restorator.service;

import diploma.dissertation.d64Restorator.converter.FileCoverterDTO;
import diploma.dissertation.d64Restorator.exception.DifferentDiscIdException;
import diploma.dissertation.d64Restorator.exception.NotEnoughSpaceException;
import diploma.dissertation.d64Restorator.model.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class RootService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RootService.class);

    private FileCoverterDTO converter;

    public RootService(FileCoverterDTO converter) {
        this.converter = converter;
    }

    public List<FileModel> findAll() {
        return Singleton.getInstance().getFiles();
    }

    public FileModel addOne(MultipartFile file) throws IOException {

        if (Singleton.getInstance().getFiles().size() <= 5) {
            FileModel ret = converter.toFileDTO(file);
            LOGGER.info("Trying to add file with name: " + ret.getName() + " and id: " + ret.getDiskId()[0] + " " + ret.getDiskId()[1]);
            if (Singleton.getInstance().getFiles().isEmpty()) {
                Singleton.getInstance().getFiles().add(ret);
            } else {
                FileModel compare = Singleton.getInstance().getFiles().get(0);
                if (new Byte(compare.getDiskId()[0]).compareTo(new Byte(ret.getDiskId()[0])) == 0 && new Byte(compare.getDiskId()[1]).compareTo(new Byte(ret.getDiskId()[1])) == 0) {
                    Singleton.getInstance().getFiles().add(ret);
                } else {
                    throw new DifferentDiscIdException("Upload of Disk with ID: " + ret.getDiskId()[0] + "" + ret.getDiskId()[1]
                            + " not allowed doe to Disk with ID: " + compare.getDiskId()[0] + "" + compare.getDiskId()[1] + " already being imported.");
                }
            }
            return ret;
        } else {
            throw new NotEnoughSpaceException("Only 5 files can be uploaded at one time.");
        }
    }

    public FileModel fix() throws IOException{
        FileModel best = Singleton.getInstance().getFiles()
                .stream()
                .min(Comparator.comparing(FileModel::getErrorCount))
                .orElseThrow(NullPointerException::new);

        byte[] fixedBytes = null, bestBytes;

        bestBytes = best.getBytes();
        fixedBytes = Arrays.copyOf(bestBytes, bestBytes.length);

        if (best.getErrorList().size() == 0) {
            //retturn new shit
        }

        for (List error :
                best.getErrorList()) {
            for (FileModel potentialFix :
                    Singleton.getInstance().getFiles()) {
                if (potentialFix.getErrorList().stream().noneMatch(x -> error.get(0) == x.get(0) && error.get(1) == x.get(1))) {
                    int index, flagInd;
                    byte[] potFixBytes = potentialFix.getBytes();

                    if ((int) error.get(0) < 17) {

                        index = (int) error.get(0) * 21 * 256 + (int) error.get(1) * 256;
                        flagInd = (int) error.get(0) * 21 + (int) error.get(1);

                    } else if ((int) error.get(0) >= 17 && (int) error.get(0) < 24) {

                        index = 17 * 21 * 256 + ((int) error.get(0) - 17) * 19 * 256 + (int) error.get(1) * 256;
                        flagInd = 17 * 21 + ((int) error.get(0) - 17) * 19 + (int) error.get(1);

                    } else if ((int) error.get(0) >= 24 && (int) error.get(0) < 30) {

                        index = 17 * 21 * 256 + 7 * 19 * 256 + ((int) error.get(0) - 17 - 7) * 18 * 256 + (int) error.get(1) * 256;
                        flagInd = 17 * 21 + 7 * 19 + ((int) error.get(0) - 17 - 7) * 18 + (int) error.get(1);

                    } else {

                        index = 17 * 21 * 256 + 7 * 19 * 256 + 6 * 18 * 256 + ((int) error.get(0) - 17 - 7 - 6) * 17 * 256 + (int) error.get(1) * 256;
                        flagInd = 17 * 21 + 7 * 19 + 6 * 18 + ((int) error.get(0) - 17 - 7 - 6) * 17 + (int) error.get(1);

                    }
                    for (int i = index; i < index + 256; index++) {

                        fixedBytes[i] = potFixBytes[i];
                        fixedBytes[fixedBytes.length - 683 + flagInd] = 1;

                    }
                    break;
                }
            }
        }


        MultipartFile fixedFile = new MockMultipartFile("fixed.d64",
                best.getFile().getName(), best.getFile().getContentType(), fixedBytes);

        FileModel ret = converter.toFileDTO(fixedFile);
        if (ret.getErrorCount() == 0) {
            if (ret.getSectorView().size() == 35) {
                ret.setBytes(Arrays.copyOf(ret.getBytes(), 174848));
            } else {
                ret.setBytes(Arrays.copyOf(ret.getBytes(), 196608));
            }
        }
        Singleton.getInstance().setFixedFile(ret);
        LOGGER.info("Fixing files completed");
        return ret;
    }

    public byte[] download() {
        if (Singleton.getInstance().getFixedFile() != null) {
            return Singleton.getInstance().getFixedFile().getBytes();
        }
        return null;
    }

    public void removeFixed() {
        Singleton.getInstance().setFixedFile(null);
    }

    public void removeOne(int index) {
        Singleton.getInstance().getFiles().remove(index);
    }

    public void removeAll() {
        Singleton.getInstance().getFiles().clear();
        Singleton.getInstance().setFixedFile(null);
    }

}
