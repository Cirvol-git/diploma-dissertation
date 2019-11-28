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

    private Integer errorCount;

    @JsonIgnore
    private MultipartFile file;

    private byte[] bytes;

    private ArrayList<List<Integer>> errorList = new ArrayList<>();

    private ArrayList<List<Boolean>> sectorView = new ArrayList<>();

}
