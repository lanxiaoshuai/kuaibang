package com.witkey.witkeyhelp.Contacts;

import android.os.Environment;

import java.io.File;

public interface Contacts {
    String path = Environment.getExternalStorageDirectory().getPath()
            + "/witkeyHelp/";
    String imgPath = path + "pics/";

    String Use_TAG = "llx";


}
