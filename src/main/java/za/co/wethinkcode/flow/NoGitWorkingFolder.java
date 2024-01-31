package za.co.wethinkcode.flow;

import java.nio.file.*;

public class NoGitWorkingFolder extends RuntimeException {
    NoGitWorkingFolder(Path path, Throwable cause) {
        super("Not a valid git working folder: " + path.toAbsolutePath().toString(), cause);
    }
}
