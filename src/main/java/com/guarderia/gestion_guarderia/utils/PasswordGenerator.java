package com.guarderia.gestion_guarderia.utils;


    public class PasswordGenerator {

        public static String generatePassword(String nombre, String rut) {
            // Obtener las tres primeras letras del nombre en minúsculas
            String nombrePart = nombre.length() >= 3 ? nombre.substring(0, 3).toLowerCase() : nombre.toLowerCase();

            // Obtener los tres primeros dígitos del RUT
            String rutPart = rut.replaceAll("\\D", ""); // Quitar caracteres no numéricos
            rutPart = rutPart.length() >= 3 ? rutPart.substring(0, 3) : rutPart;

            return nombrePart + rutPart + "@123"; // Agregar algún carácter especial y número para fortaleza
        }
    }

