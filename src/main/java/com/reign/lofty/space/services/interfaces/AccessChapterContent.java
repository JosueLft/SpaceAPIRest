package com.reign.lofty.space.services.interfaces;

import com.reign.lofty.space.entities.Chapter;
import com.reign.lofty.space.entities.Work;

public interface AccessChapterContent {

    Chapter accessContent(Work work);
}