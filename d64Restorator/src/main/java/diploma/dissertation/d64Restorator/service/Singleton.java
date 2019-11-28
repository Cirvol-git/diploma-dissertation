package diploma.dissertation.d64Restorator.service;

import diploma.dissertation.d64Restorator.model.FileModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Singleton {

    private static Singleton instance = null;
    @Getter
    private ArrayList<FileModel> files = new ArrayList<>();

    @Getter
    @Setter
    private FileModel fixedFile;

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
