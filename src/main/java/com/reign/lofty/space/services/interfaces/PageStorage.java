package com.reign.lofty.space.services.interfaces;

import java.io.File;
import java.net.URL;

public interface PageStorage {

    byte[] page(URL pageUrl, String workName, File path);
}