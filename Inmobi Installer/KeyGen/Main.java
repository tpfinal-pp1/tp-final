import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

public class Main {



    public static void main(String[] args){
        try {
            Files.write(Paths.get("key.txt"),
                    Cipher.encrypt(
                            String.valueOf(Instant.now().toEpochMilli())).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
