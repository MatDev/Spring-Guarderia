package com.guarderia.gestion_guarderia.utils.constant;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FotoFileConstant {
    public static final String SUBFOLDER = "fotos";
    public static final String SUBFOLDER_ACTIVIDAD = "actividades";
    public static final Path ROOT_LOCATION = Paths.get(SUBFOLDER, SUBFOLDER_ACTIVIDAD);
}
