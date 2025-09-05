package command;

import org.jline.builtins.Completers;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * The ImprovedFileNameCompleter handles file name completion in the terminal.
 * Only if the user has entered a '/' character does the completer actually allow file name completion
 * Spaces are handled, by adding three '\' characters in front of the space to allow for proper tokenization
 * Setting the root file means, that the file name completer only completes to files in the root directory
 * @author Matthias Amend
 */
public class ImprovedFileNameCompleter extends Completers.FileNameCompleter implements Completer {

    private File root = Paths.get(System.getProperty("user.dir")).toFile();

    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
        if(line.word().startsWith("/")) {
            assert candidates != null;
            String buffer = line.word().substring(0, line.wordCursor());
            String sep = this.getUserDir().getFileSystem().getSeparator();
            int lastSep = buffer.lastIndexOf(sep);
            String curBuf = buffer.substring(0, lastSep + 1);
            Path current = root.toPath();
            try {
                File directory = new File(current.toFile(), curBuf);
                Files.newDirectoryStream(directory.toPath()).forEach((p) -> {
                    String value = curBuf + p.getFileName().toString();
                    if (Files.isDirectory(p)) {
                        candidates.add(new Candidate(value + (reader.isSet(LineReader.Option.AUTO_PARAM_SLASH) ? sep : ""), this.getDisplay(reader.getTerminal(), p), (String) null, (String) null, reader.isSet(LineReader.Option.AUTO_REMOVE_SLASH) ? sep : null, (String) null, false));
                    } else {
                        candidates.add(new Candidate(value, this.getDisplay(reader.getTerminal(), p), (String) null, (String) null, (String) null, (String) null, true));
                    }

                });
            } catch (IOException ignored) {
            }
            for(int candidateIndex = 0; candidateIndex < candidates.size(); candidateIndex++) {
                Candidate candidate = candidates.get(candidateIndex);
                String value = candidate.value();
                if(value.contains(" ")) {
                    value = formatFileName(value);
                    Candidate updatedCandidate = new Candidate(value , value, candidate.group(), candidate.descr(),
                            candidate.suffix(), candidate.key(), candidate.complete());
                    candidates.set(candidateIndex, updatedCandidate);
                }
            }
        }
    }

    /**
     * Format file names with spaces.
     * If a space is detected within a file name, add three '\' characters in front
     * @param value The file name as a string
     * @return The updated file name as a string
     */
    private String formatFileName(String value) {
        StringBuilder fileBuilder = new StringBuilder();
        for(int charIndex = 0; charIndex < value.length(); charIndex++) {
            if(Character.isWhitespace(value.charAt(charIndex))) {
                fileBuilder.append("\\ ");
            } else {
                fileBuilder.append(value.charAt(charIndex));
            }
        }
        return fileBuilder.toString();
    }

    /**
     * Set the root file of the file name completer.
     * @param root The root file
     */
    public void setRoot(File root) {
        this.root = root;
    }

    /**
     * Get the root directory path.
     * @return The root directory as a path
     */
    @Override
    protected Path getUserDir() {
        return root.toPath();
    }

}
