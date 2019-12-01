/*#######################################################
 *
 *   Maintained by Gregor Santner, 2017-
 *   https://gsantner.net/
 *
 *   License of this file: Apache 2.0 (Commercial upon request)
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
#########################################################*/
package net.gsantner.markor.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.gsantner.markor.format.keyvalue.KeyValueConverter;
import net.gsantner.markor.util.DocumentIO;

@RunWith(MockitoJUnitRunner.class)
public class DocumentTest{

    @Mock
    Document document;

    @Test
    public void documentOlderVersion() {
        Document document = new Document();
        document.setTitle("Hello");
        document.forceAddNextChangeToHistory();
        document.setTitle("Hello World");
        document.forceAddNextChangeToHistory();
        document.goToEarlierVersion();
        assertThat(document.getTitle()).isEqualTo("Hello");
    }

    @Test
    public void documentNewerVersion() {
        Document document = new Document();
        document.setTitle("Hello");
        document.forceAddNextChangeToHistory();
        document.setTitle("Hello World");
        document.forceAddNextChangeToHistory();
        document.setTitle("Hello World Again");
        document.goToEarlierVersion();
        document.goToEarlierVersion();
        assertThat(document.getTitle()).isEqualTo("Hello");
        document.goToNewerVersion();
        assertThat(document.getTitle()).isEqualTo("Hello World");
        assertThat(document.canGoToNewerVersion()).isEqualTo(true);
        document.goToNewerVersion();
        assertThat(document.getTitle()).isEqualTo("Hello World Again");
        assertThat(document.canGoToNewerVersion()).isEqualTo(false);
    }

    public String normalizeTitleForFilename(Document document) {
        return DocumentIO.normalizeTitleForFilename(document, document.getContent().toString());
    }

    @Test
    public void filenameNormalization() {
        assertThat(normalizeTitleForFilename(nd(null, "HelloWorld"))).isEqualTo("HelloWorld");
        assertThat(normalizeTitleForFilename(nd("HelloWorld", "text"))).isEqualTo("HelloWorld");
        assertThat(normalizeTitleForFilename(nd(null, "text\nnewline"))).isEqualTo("text");
        assertThat(normalizeTitleForFilename(nd(null, "sumtext/folder"))).isEqualTo("sumtextfolder");
        assertThat(normalizeTitleForFilename(nd(null, "## hello world"))).isEqualTo("hello world");
    }

    private Document nd(String title, String content) {
        Document document = new Document();
        if (title != null) {
            document.setTitle(title);
        }
        if (content != null) {
            document.setContent(content);
        }
        return document;
    }

    @Test
    public void testMockFile(){
        File file = null;
        KeyValueConverter keyValueConverter = new KeyValueConverter();
        try {
            file = File.createTempFile("mockFile",".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //when
        when(document.getFile()).thenReturn(file);
		when(document.getContent()).thenReturn("http://google.sk");
		when(document.getTitle()).thenReturn("MyTitle");
        //then
        assertThat(DocumentIO.getMaskedContent(document)).isNotEqualTo(null);
        assertThat(DocumentIO.getMaskedContent(document)).isEqualTo("https://aaaaaa.aa");
        //then
        assertThat(keyValueConverter.isFileOutOfThisFormat(document.getFile().getPath())).isNotEqualTo(null);
        assertThat(keyValueConverter.isFileOutOfThisFormat(document.getFile().getPath())).isEqualTo(false);
        //then
        assertThat(document.getFile()).isEqualTo(file);
        assertThat(document.getTitle()).isEqualTo("MyTitle");

    }

    @Test
    public void maskedContent(){

        assertThat(maskForFile(nd("HelloWorld","text"))).isEqualTo("aaaa");
        assertThat(maskForFile(nd("http://ssss","text"))).isEqualTo("aaaa");
        assertThat(maskForFile(nd("http://sssss-sssss","text"))).isEqualTo("aaaa");
        assertThat(maskForFile(nd("HelloWosssssrld","text"))).isEqualTo("aaaa");
        assertThat(maskForFile(nd("adadada","text"))).isEqualTo("aaaa");
        assertThat(maskForFile(nd("HelloWoasdadrld","text"))).isEqualTo("aaaa");
    }


    public String maskForFile(Document document) {
        return DocumentIO.getMaskedContent(document);
    }


}
