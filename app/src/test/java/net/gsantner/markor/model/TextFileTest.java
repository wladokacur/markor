package net.gsantner.markor.model;
import net.gsantner.opoc.util.FileUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class TextFileTest {
    FileUtils fileUtils = new FileUtils();
    private File file;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static File getFileFromPath(Object obj, String fileName) {
        ClassLoader classLoader = obj.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return new File(resource.getPath());
    }

    @Before
    public void fileObjectShouldNotBeNull() {
        file = getFileFromPath(this, "changelog.md");
    }

    @Test
    public void createAndWriteFile(){
        File createdFile= null;
        try {
            createdFile = File.createTempFile("tempTestFile",".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileUtils.writeFile(createdFile,"some new line");
        assertThat(createdFile.length()).isGreaterThan(0);
    }

    @Test
    public void checkLengthOfFile(){
        assertThat(file).isNotNull();
        String text = fileUtils.readTextFile(file);
        assertThat(text.length()).isGreaterThan(0);

        File newFile= null;
        try {
            newFile = File.createTempFile("text",".md");
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean isCopiedText = fileUtils.copyFile(file,newFile);
        if(isCopiedText){
            assertThat(file.length()).isEqualTo(newFile.length());
        }
        else{
            thrown.expect(IndexOutOfBoundsException.class);
            thrown.expectMessage("expected messages");
        }
    }

    @Test
    public void timediff(){
        long past = 1569224700000L;   //09/23/2019 9:45:00
        long now  = 1569225300000L;                 //09/23/2019 9:55:00
        int[] diff = fileUtils.getTimeDiffHMS(past,now);
        assertThat(diff[1]).isEqualTo(10);

    }






}
