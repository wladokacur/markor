package net.gsantner.markor.model;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import net.gsantner.markor.util.ShareUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class ContextTest {

    private Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void test(){
        ShareUtil shareUtil = new ShareUtil(context);
        shareUtil.setClipboard("ssss");
        assertThat(shareUtil.getClipboard()).isEqualTo(new ArrayList<String>(Collections.singletonList("ssss")));
    }
}
