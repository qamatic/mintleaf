package org.qamatic.mintleaf;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by senips on 4/8/17.
 */
public class FileFinder {
    private static final MintleafLogger logger = MintleafLogger.getLogger(FileFinder.class);

    private final PathMatcher matcher;
    private final String path;
    private final boolean regExMatch;

    public FileFinder(String path) {
        final String[] pathSplits = path.split("/");
        regExMatch = path.contains("regex:");
        final String wildCardName = regExMatch ? pathSplits[pathSplits.length - 1] : "glob:" + pathSplits[pathSplits.length - 1];
        this.path = path.replaceAll("\\Q" + pathSplits[pathSplits.length - 1] + "\\E", "");
        matcher = FileSystems.getDefault()
                .getPathMatcher(wildCardName);
    }

    public List<String> list() {
        List<String> files = new ArrayList<>();
        try {
            Files.walkFileTree(Paths.get(this.path), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    Path name = regExMatch ? file.toAbsolutePath() : file.getFileName();
                    if (name != null && matcher.matches(name)) {
                        files.add(file.toAbsolutePath().toString());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            logger.error(e);
        }
        return files;
    }

}
