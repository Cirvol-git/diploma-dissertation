package diploma.dissertation.d64Restorator.service;

import diploma.dissertation.d64Restorator.model.FileModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@NoArgsConstructor
public class Singleton {

    @Getter
    private ArrayList<FileModel> files = new ArrayList<>();

    @Getter
    @Setter
    private FileModel fixedFile;
}
