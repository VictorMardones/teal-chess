/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package io;

import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
final class ResourceImporter {

    static InputStream get(String path) {
        return ResourceImporter.class.getClassLoader().getResourceAsStream(path);
    }
}
