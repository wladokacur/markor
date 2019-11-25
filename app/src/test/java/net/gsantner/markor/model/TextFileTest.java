package net.gsantner.markor.model;

import net.gsantner.opoc.util.FileUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class TextFileTest {
    FileUtils fileUtils = new FileUtils();
    private File file1;
    private File file2;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static File getFileFromPath(Object obj, String fileName) {
        ClassLoader classLoader = obj.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return new File(resource.getPath());
    }

    @Before
    public void fileObjectShouldNotBeNull() {
        file1 = getFileFromPath(this, "testFile1.md");
        file2 = getFileFromPath(this, "testFile2.md");
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
        assertThat(file1).isNotNull();
        String text = fileUtils.readTextFile(file1);
        assertThat(text.length()).isGreaterThan(0);

        File newFile= null;
        try {
            newFile = File.createTempFile("text",".md");
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean isCopiedText = fileUtils.copyFile(file1,newFile);
        if(isCopiedText){
            assertThat(file1.length()).isEqualTo(newFile.length());
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

    @Test
    public void readeTextFileFast(){
        String textFast = FileUtils.readTextFileFast(file1);
        String text = FileUtils.readTextFile(file1);
        assertThat(textFast).isNotEqualTo(text);
    }

    @Test
    public void copyFile(){
        assertThat(file1.length()).isGreaterThan(0);
        assertThat(file2.length()).isGreaterThan(0);
        assertThat(FileUtils.copyFile(file1,file2)).isEqualTo(true);

    }

    @Test
    public void checkExtenssion(){
        String[] extension={"jpg","md","str","png", "jpeg"};
        String text = "mySuperText.jpg";
        assertThat(FileUtils.hasExtension(text,extension)).isEqualTo(true);
        assertThat(FileUtils.hasExtension("TextAllOfUs.png",extension)).isEqualTo(true);
        assertThat(FileUtils.hasExtension("myText.str",extension)).isEqualTo(true);
        assertThat(FileUtils.hasExtension("myText.md",extension)).isEqualTo(true);
        assertThat(FileUtils.hasExtension("myText.jpeg",extension)).isEqualTo(true);
        assertThat(FileUtils.hasExtension("myText.csv",extension)).isEqualTo(false);
    }

    @Test
    public void getTypeOfFile(){
        assertThat(file1).isNotEqualTo(null);
        assertThat(file1.length()).isGreaterThan(0);
        assertThat(FileUtils.getMimeType(file1)).isEqualTo("text/markdown");
    }

    @Test
    public void testReadingCloseSteam(){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertThat(FileUtils.readCloseTextStream(fileInputStream)).isNotNull();
        assertThat(FileUtils.readCloseStreamWithSize(fileInputStream,600)).isNotNull();
        assertThat(FileUtils.readBinaryFile(file1)).isNotNull();
        assertThat(FileUtils.readCloseBinaryStream(fileInputStream)).isNotNull();
    }


}
