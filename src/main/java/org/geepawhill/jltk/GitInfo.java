package org.geepawhill.jltk;

import org.eclipse.jgit.lib.*;

import java.nio.file.*;

class NoGitWorkingFolderException extends RuntimeException {
    NoGitWorkingFolderException(Path path, Throwable cause) {
        super("Not a valid git working folder: " + path.toAbsolutePath().toString(), cause);
    }
}

public class GitInfo {
    public final Path home;
    public final Path root;
    public final String branch;
    public final String committer;
    public final String email;

    GitInfo(Path home, Path root, String branch, String committer, String email) {
        this.home = home;
        this.root = root;
        this.branch = branch;
        this.committer = committer;
        this.email = email;
    }

    GitInfo(Path root) {
        try (Repository localRepo = new RepositoryBuilder()
                .findGitDir(root.toFile()).build()) {
            this.home = home();
            this.root = localRepo.getWorkTree().toPath();
            Config configuration = localRepo.getConfig();
            email = configuration.getString("user", null, "email");
            committer = configuration.getString("user", null, "name");
            branch = localRepo.getBranch();
        } catch (Exception cause) {
            throw new NoGitWorkingFolderException(root, cause);
        }
    }

    GitInfo(String root) {
        this(Path.of(root));
    }

    GitInfo() {
        this(System.getProperty("user.dir"));
    }

    static Path home() {
        return Path.of(System.getProperty("user.home"));
    }
}
