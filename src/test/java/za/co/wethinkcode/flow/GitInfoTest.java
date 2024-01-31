package za.co.wethinkcode.flow;

import org.junit.jupiter.api.*;

import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

public class GitInfoTest {


    @Test
    void literalConstructor() {
        GitInfo info = new GitInfo(Path.of("."), "branch", "committer", "email", "last");
        // assumption: the working folder is the root of the git repo.
        assertEquals(Path.of("."), info.root);
        assertEquals("branch", info.branch);
        assertEquals("committer", info.username);
        assertEquals("email", info.email);
        assertEquals("last", info.last);
    }

    @Test
    void throwsOnNonRepo() {
        // assumption: the user's home directory is not a valid repo
        try {
            new GitInfo(System.getProperty("user.home"));
            Assertions.fail("non-git working folder did not throw.");
        } catch (NoGitWorkingFolder expected) {
        }
    }

    @Test
    void worksOnThisRepo() {
        GitInfo info = new GitInfo();
        String expected = Path.of(".").toAbsolutePath().normalize().toString();
        assertEquals(expected, info.root.toAbsolutePath().toString());
    }

    @Test
    void knowsRepoFolder() {
        GitInfo info = new GitInfo();
        String expected = Path.of(".").toAbsolutePath().normalize().toString();
        assertEquals(expected, info.root.toAbsolutePath().normalize().toString());
    }
}
