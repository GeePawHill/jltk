package org.geepawhill.jltk.flow;

import org.eclipse.jgit.lib.*;

import java.nio.file.*;

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
     * The primitive "all fields" constructor, used only for testing.
     */
    GitInfo(Path root, String branch, String username, String email) {
        this.root = root;
        this.branch = branch;
        this.username = username;
        this.email = email;
    }

    /**
     * Constructor that deduces everything but the repo location.
     *
     * @param root Folder containing .git or any subfolder of a folder containing .git.
     */
    GitInfo(Path root) {
        try (Repository localRepo = new RepositoryBuilder()
                .findGitDir(root.toFile()).build()) {
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

    public Path computeLogPathFor(Path home) {
        WtcKeyManager manager = new WtcKeyManager(root, home);
        String key = manager.findOrMakeKey();
        String shortEmail = email.split("@")[0];
        String leafName = branch
                + "_" + shortEmail
                + ".wtc";
        return home.resolve(Path.of(ActionInfo.JLTK_FOLDER, key, leafName));
    }

    @Override
    public void putTo(YamlMap map) {
        map.put("branch", branch);
        map.put("committer", username);
        map.put("email", email);
    }
}
