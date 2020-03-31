package com.nexttech.easybusinesscard.Activity;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;import android.widget.ListView;

import com.nexttech.easybusinesscard.R;

public class PrivacyPolicy extends Activity
{
    // Array of strings...
    ListView simpleList;
    String policy[] = {"Easy Business Card uses an offline database to store your information in your phone. We do not use the internet. So our users can stay tension free about their information.",
            "Easy Business Card also collects personally identifiable information that users provide to design the card, such as name, designation, department name, company name, phone number, fax, address, web address and email address.",
            "In upcoming updates, Easy Business Card may also access other personal information on the user's device, such as phone book, calendar in order to provide better services. In that case, user permission will be asked first.",
            "Easy Business Card stores all personal data under the Digital Security Act 2018, Bangladesh.",
            "Ads are served to you only from Google Admob.",
            "Team Easy Business Card will post any privacy policy changes on this page and, if the changes are significant, we will provide a more prominent notice.",
            "If you have any questions about this Privacy Policy, please contact us."
    };

    @Override   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      setContentView(R.layout.privacy_policy);
        simpleList = (ListView)findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, policy);
        simpleList.setAdapter(arrayAdapter);
    }
}