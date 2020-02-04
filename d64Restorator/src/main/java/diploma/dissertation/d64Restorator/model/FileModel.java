package diploma.dissertation.d64Restorator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class FileModel {

    private String name;

    private byte[] diskId;

    @JsonIgnore
    private MultipartFile file;

    private byte[] bytes;

    private List<List<Integer>> errorList = new ArrayList<>();

    private List<List<Boolean>> sectorView = new ArrayList<>();

}
