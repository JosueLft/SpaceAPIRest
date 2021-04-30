package com.reign.lofty.space.services.interfaces;

import com.reign.lofty.space.entities.Page;

import java.util.List;

public interface AccessPageContent {

    List<Page> accessContent(String workName);
}