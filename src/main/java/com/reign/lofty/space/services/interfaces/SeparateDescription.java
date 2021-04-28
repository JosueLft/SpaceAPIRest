package com.reign.lofty.space.services.interfaces;

import org.jsoup.nodes.Document;

public interface SeparateDescription {
    String description(Document doc, String tag, String regen);
}