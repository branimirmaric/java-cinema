/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utilities;

import hr.algebra.factory.UrlConnectionFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author dnlbe
 */
public class FileUtils {

    private static final String UPLOAD = "Upload";
    private static final String TEXT_FILES_TXT = "Text files (*.txt)";
    private static final String TXT = "txt";
    private static final String SAVE = "Save";

    private FileUtils() {
    }

    public static Optional<File> uploadFile(String description, String... extensions) {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
        chooser.setDialogTitle(UPLOAD);
        chooser.setApproveButtonText(UPLOAD);
        chooser.setApproveButtonToolTipText(UPLOAD);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
            return selectedFile.exists()
                    && Arrays.asList(extensions).contains(extension.toLowerCase())
                    ? Optional.of(selectedFile)
                    : Optional.empty();
        }
        return Optional.empty();
    }

    public static Optional<File> saveTextInFile(String text, Optional<File> optFile) throws IOException {
        if (!optFile.isPresent()) {
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            chooser.setFileFilter(new FileNameExtensionFilter(TEXT_FILES_TXT, TXT));
            chooser.setDialogTitle(SAVE);
            chooser.setApproveButtonText(SAVE);
            chooser.setApproveButtonToolTipText(SAVE);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                if (!selectedFile.toString().endsWith(TXT)) {
                    selectedFile = new File(selectedFile.toString().concat(".").concat(TXT));
                }
                optFile = Optional.of(selectedFile);
                Files.write(optFile.get().toPath(), text.getBytes());
            }
        } else {
            Files.write(optFile.get().toPath(), text.getBytes());
        }

        return optFile;
    }

    public static Optional<String> loadTextFromFile() throws IOException {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.toString().endsWith(TXT);
            }

            @Override
            public String getDescription() {
                return TEXT_FILES_TXT;
            }

        });

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return Optional.of(new String(
                    Files.readAllBytes(
                            chooser
                                    .getSelectedFile()
                                    .toPath())));

        }
        return Optional.empty();
    }

    public static void copy(String src, String dst) throws IOException {
        createDirHierarchy(dst);
        Files.copy(Paths.get(src), Paths.get(dst));
    }

    public static void copyFrom(String src, String dst) throws IOException {
        createDirHierarchy(dst);
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(src);

        try (InputStream is = con.getInputStream()) {
            Files.copy(is, Paths.get(dst));
        }
    }

    private static void createDirHierarchy(String dst) throws IOException {
        String dir = dst.substring(
                0,
                dst.lastIndexOf(File.separator));
        if (!Files.exists(Paths.get(dir))) {
            Files.createDirectories(Paths.get(dir));
        }

    }

    public static boolean fileNameHasExtension(String filename, int length) {
        return filename.contains(".")
                && filename.substring(filename.lastIndexOf(".") + 1)
                        .length() == length;
    }
}
