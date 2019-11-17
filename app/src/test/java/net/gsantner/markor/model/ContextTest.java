package net.gsantner.markor.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import net.gsantner.markor.util.ShareUtil;

@RunWith(RobolectricTestRunner.class) public class ContextTest {

	private Context context = ApplicationProvider.getApplicationContext();
	private ShareUtil shareUtil;

	@Before
	public void prepareTest() {
		shareUtil = new ShareUtil(context);
	}

	@Test
	public void testClipboardCopy() {
		String clipboardText = "clipboardText";
		shareUtil.setClipboard(clipboardText);
		assertThat(shareUtil.getClipboard()).isEqualTo(new ArrayList<String>(Collections.singletonList(clipboardText)));

	}


}
