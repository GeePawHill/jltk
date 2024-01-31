package za.co.wethinkcode.flow;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.*;

import java.nio.file.*;
import java.util.*;

import static za.co.wethinkcode.flow.FileHelpers.*;

public class GitInfo implements MapAppender {
    /**
     * The root folder of the current repo.
     */
    public final Path root;

    /**
     * The branch that is current in that repo.
     */
    public final String branch;

    /**
     * The git username.
     */
    public final String username;

    /**
     * The git email
     */
    public final String email;

    /**
     * The repo's last commit hashtag
     */
    public final String last;

    /**
     * The primitive "all fields" constructor, used only for testing.
     */
    GitInfo(Path root, String branch, String username, String email, String last) {
        this.root = root;
        this.branch = branch;
        this.username = username;
        this.email = email;
        this.last = last;
    }

    /**
     * Constructor that deduces everything but the repo location.
     *
     * @param root Folder containing .git or any subfolder of a folder containing .git.
     */
    GitInfo(Path root) {
        try (Repository localRepo = new RepositoryBuilder()
                .findGitDir(root.toFile()).build()) {
            this.last = computeLastCommitHash(localRepo);
            this.root = localRepo.getWorkTree().toPath();
            Config configuration = localRepo.getConfig();
            email = configuration.getString("user", null, "email");
            username = configuration.getString("user", null, "name");
            branch = localRepo.getBranch();
        } catch (Exception cause) {
            throw new NoGitWorkingFolder(root, cause);
        }
    }

    /**
     * Convenience constructor starting from a string representation of the root rather than a Path object.
     *
     * @param root -- The string representation of the repo's root.
     */
    GitInfo(String root) {
        this(Path.of(root));
    }

    /**
     * The normal constructor, which deduces everything based on the user's current folder.
     */
    GitInfo() {
        this(System.getProperty("user.dir"));
    }

    public Path computeTemporaryPath() {
        String shortEmail = email.split("@")[0];
        String leafName = branch
                + "_" + shortEmail
                + JLTK_TMP_SUFFIX;
        return root.resolve(Path.of(FileHelpers.JLTK_FOLDER, leafName));
    }

    @Override
    public void putTo(YamlMap map) {
        map.put("branch", branch);
        map.put("committer", username);
        map.put("email", email);
    }

    private String computeLastCommitHash(Repository localRepo) {
        try {
            Git git = new Git(localRepo);
            ObjectId branchId = localRepo.resolve("HEAD");
            Iterator<RevCommit> commits = git.log().add(branchId).call().iterator();
            if (commits.hasNext()) return commits.next().getName();
        } catch (Exception ignored) {
        }
        return "Unknown";
    }
}
