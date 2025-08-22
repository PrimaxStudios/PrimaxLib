package net.primaxstudios.primaxcore.managers;

import com.alessiodp.libby.Library;
import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.dependency.DependencyManager;

import java.util.List;

public class CoreDependencyManager extends DependencyManager {

    public CoreDependencyManager() {
        super(PrimaxCore.inst());
    }

    @Override
    protected List<Library> declareLibraries() {
        return List.of(
                //HikariCP
                Library.builder()
                        .groupId("com{}zaxxer")
                        .artifactId("HikariCP")
                        .version("6.3.0")
                        .relocate("com{}zaxxer{}hikari", "net{}primaxstudios{}primaxcore{}hikari")
                        .build(),
                //Postgresql Driver
                Library.builder()
                        .groupId("org{}postgresql")
                        .artifactId("postgresql")
                        .version("42.7.7")
                        .relocate("org{}postgresql", "net{}primaxstudios{}primaxcore{}libs{}postgresql")
                        .build()
        );
    }
}
