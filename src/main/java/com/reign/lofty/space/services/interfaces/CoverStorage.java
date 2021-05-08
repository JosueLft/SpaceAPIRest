package com.reign.lofty.space.services.interfaces;

import java.net.URL;

public interface CoverStorage {
    byte[] recoverCover(URL coverUrl, String workName);
}