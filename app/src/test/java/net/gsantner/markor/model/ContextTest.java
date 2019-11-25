package net.gsantner.markor.model;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import net.gsantner.markor.util.ShareUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class) public class ContextTest {

	private Context context = ApplicationProvider.getApplicationContext();
	private ShareUtil shareUtil;

	@Before
	public void prepareTest() {
		shareUtil = new ShareUtil(context);
	}

	@Test
	public void testClipboardCopy() {
		String clipboardText = generateTextRandom();
		shareUtil.setClipboard(clipboardText);
		assertThat(generateTextRandom().length()).isGreaterThan(0);
		assertThat(shareUtil.getClipboard()).isEqualTo(new ArrayList<String>(Collections.singletonList(clipboardText)));

	}


	public String generateTextRandom() {
		byte[] array = new byte[7]; // length is bounded by 7
		new Random().nextBytes(array);
		return 	new String(array, Charset.forName("UTF-8"));
	}

}
