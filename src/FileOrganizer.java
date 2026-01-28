import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class FileOrganizer {

    public static void organizeFiles(String directoryPath) {
        Path dir = Paths.get(directoryPath);

        if (!Files.isDirectory(dir)) {
            System.out.println("Provided path is not a directory.");
            return;
        }

        try (Stream<Path> files = Files.list(dir)) {
            files
                    .filter(Files::isRegularFile)
                    .forEach(file -> organizeFile(file, dir));
        } catch (IOException e) {
            System.out.println("Error reading directory: " + e.getMessage());
        }
    }

    private static void organizeFile(Path file, Path baseDir) {
        String fileName = file.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex == -1) {
            return;
        }

        String extension = fileName.substring(dotIndex + 1);
        Path targetDir = baseDir.resolve(extension + "s");

        try {
            Files.createDirectories(targetDir);
            Files.move(file, targetDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Failed to move file: " + fileName);
        }
    }

    public static void main(String[] args) {
        organizeFiles("teste");
    }
}
