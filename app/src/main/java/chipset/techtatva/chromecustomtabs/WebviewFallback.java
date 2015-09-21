package chipset.techtatva.chromecustomtabs;

/**
 * Created by saketh on 20/9/15.
 */
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import chipset.techtatva.activities.WebviewActivity;

/**
 * A Fallback that opens a Webview when Custom Tabs is not available
 */
public class WebviewFallback implements CustomTabActivityHelper.CustomTabFallback {
    @Override
    public void openUri(Activity activity, Uri uri) {
        Intent intent = new Intent(activity, WebviewActivity.class);
        intent.putExtra(WebviewActivity.EXTRA_URL, uri.toString());
        activity.startActivity(intent);
    }
}